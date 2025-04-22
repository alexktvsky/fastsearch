package com.alexktvsky.fastsearch.dto;

import java.util.List;

public class WorkerResponse {

    private String workerId;
    private List<DocumentDto> docs;

    public WorkerResponse() {}

    public WorkerResponse(String workerId, List<DocumentDto> docs) {
        this.workerId = workerId;
        this.docs = docs;
    }

    public String getWorkerId() {
        return workerId;
    }

    public void setWorkerId(String workerId) {
        this.workerId = workerId;
    }

    public List<DocumentDto> getDocs() {
        return docs;
    }

    public void setDocs(List<DocumentDto> docs) {
        this.docs = docs;
    }
}
