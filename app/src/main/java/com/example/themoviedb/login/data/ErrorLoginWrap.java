package com.example.themoviedb.login.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ErrorLoginWrap {
    @SerializedName("status_message")
    @Expose
    private String statusMessage;
    @SerializedName("status_code")
    @Expose
    private int statusCode;

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
