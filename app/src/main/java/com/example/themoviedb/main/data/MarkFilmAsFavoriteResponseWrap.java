package com.example.themoviedb.main.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MarkFilmAsFavoriteResponseWrap {
    @SerializedName("status_message")
    @Expose
    private String statusMessage;
    @SerializedName("status_code")
    @Expose
    private int statusCode;
    @SerializedName("success")
    @Expose
    private boolean success;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
