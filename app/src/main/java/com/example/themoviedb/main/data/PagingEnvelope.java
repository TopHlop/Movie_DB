package com.example.themoviedb.main.data;


import java.util.List;

public class PagingEnvelope<T> {
    private final int totalPages;
    private final int totalResults;
    private final int page;
    private final List<T> results;

    public int getTotalPages() {
        return totalPages;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public int getPage() {
        return page;
    }

    public List<T> getResults() {
        return results;
    }

    public PagingEnvelope(final int totalPages, final int totalResults, final int page, final List<T> results) {
        this.totalPages = totalPages;
        this.totalResults = totalResults;
        this.page = page;
        this.results = results;
    }
}