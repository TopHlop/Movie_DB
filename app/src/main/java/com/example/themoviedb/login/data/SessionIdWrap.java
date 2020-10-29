package com.example.themoviedb.login.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SessionIdWrap {
    @SerializedName("success")
    @Expose
    private boolean success;
    @SerializedName("session_id")
    @Expose
    private String sessionId;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
