package com.example.themoviedb.login.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestTokenWrap {

    @SerializedName("request_token")
    @Expose
    private String requestToken;

    public RequestTokenWrap(String requestToken) {
        this.requestToken = requestToken;
    }

    public String getRequestToken() {
        return requestToken;
    }

    public void setRequestToken(String requestToken) {
        this.requestToken = requestToken;
    }
}
