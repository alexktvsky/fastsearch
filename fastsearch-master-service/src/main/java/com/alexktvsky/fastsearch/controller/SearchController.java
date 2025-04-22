package com.alexktvsky.fastsearch.controller;

import com.alexktvsky.fastsearch.dto.SearchResponse;
import com.alexktvsky.fastsearch.service.WorkerSearchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1")
public class SearchController {

    private final WorkerSearchService searchService;

    public SearchController(WorkerSearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping("/search")
    public Mono<ResponseEntity<SearchResponse>> search(@RequestParam("q") String query,
                                                       @RequestHeader(value = "X-Trace-Id", required = false) String traceId) {
        return searchService.searchAllWorkers(query, traceId)
                .map(ResponseEntity::ok);
    }
}
