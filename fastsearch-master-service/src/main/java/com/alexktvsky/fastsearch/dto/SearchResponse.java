package com.alexktvsky.fastsearch.dto;

import java.util.List;

public class SearchResponse {

    private List<DocumentDto> docs;

    public SearchResponse(List<DocumentDto> docs) {
        this.docs = docs;
    }

    public List<DocumentDto> getDocs() {
        return docs;
    }

    public void setDocs(List<DocumentDto> docs) {
        this.docs = docs;
    }
}
