package com.example.themoviedb.login.data;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestTokenResponseWrap {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("expires_at")
    @Expose
    private String expiresAt;
    @SerializedName("request_token")
    @Expose
    private String requestToken;

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public void setExpiresAt(String expiresAt) {
        this.expiresAt = expiresAt;
    }

    public void setRequestToken(String requestToken) {
        this.requestToken = requestToken;
    }

    public Boolean isSuccess() {
        return success;
    }

    public String getExpiresAt() {
        return expiresAt;
    }

    public String getRequestToken() {
        return requestToken;
    }
}
