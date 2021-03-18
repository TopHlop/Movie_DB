package com.example.themoviedb.main.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SessionIdBodyWrap {
    @SerializedName("session_id")
    @Expose
    private String sessionId;

    public SessionIdBodyWrap(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
