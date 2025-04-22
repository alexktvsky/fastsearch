package com.alexktvsky.fastsearch.service;

import com.alexktvsky.fastsearch.dto.SearchResponse;
import com.alexktvsky.fastsearch.dto.WorkerResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WorkerSearchService {
    private final WebClient webClient = WebClient.create();

    @Value("${fastsearch.workers}")
    private List<String> workers;

    public Mono<SearchResponse> searchAllWorkers(String query, String traceId) {
        List<Mono<WorkerResponse>> calls = workers.stream().map(url ->
                webClient.get()
                        .uri(url + "/api/v1/search?q=" + query)
                        .header("X-Trace-Id", traceId == null ? "" : traceId)
                        .retrieve()
                        .bodyToMono(WorkerResponse.class)
        ).collect(Collectors.toList());

        return Flux.merge(calls)
                .flatMap(worker -> Flux.fromIterable(worker.getDocs()))
                .collectList()
                .map(SearchResponse::new);
    }
}
