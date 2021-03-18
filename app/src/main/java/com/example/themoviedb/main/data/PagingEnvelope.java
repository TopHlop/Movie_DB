package com.example.themoviedb.main.data;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PagingEnvelope<T> {
    @SerializedName("total_page")
    @Expose
    private int totalPages;
    @SerializedName("total_results")
    @Expose
    private int totalResults;
    @SerializedName("page")
    @Expose
    private int page;
    @SerializedName("results")
    @Expose
    private List<T> results;

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

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public void setResults(List<T> results) {
        this.results = results;
    }
}