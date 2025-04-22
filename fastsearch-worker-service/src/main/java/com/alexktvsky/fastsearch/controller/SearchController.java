package com.alexktvsky.fastsearch.controller;

import com.alexktvsky.fastsearch.dto.DocumentDto;
import com.alexktvsky.fastsearch.dto.SearchResponse;
import com.alexktvsky.fastsearch.model.DocumentEntity;
import com.alexktvsky.fastsearch.repo.DocumentRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/api/v1")
public class SearchController {

    private final DocumentRepository repo;

    @Value("${fastsearch.worker.id}")
    private String workerId;

    public SearchController(DocumentRepository repo) {
        this.repo = repo;
    }

    @GetMapping("/search")
    public Mono<ResponseEntity<SearchResponse>> search(@RequestParam("q") String query,
                                                       @RequestHeader(value = "X-Trace-Id", required = false) String traceId) {
        return repo.findByPhrase(query)
                .map(this::toDto)
                .collectList()
                .map(docs -> ResponseEntity.ok(new SearchResponse(workerId, docs)));
    }

    private DocumentDto toDto(DocumentEntity entity) {
        return new DocumentDto(
                entity.getId().toHexString(),
                entity.getTitle(),
                entity.getText()
        );
    }
}
