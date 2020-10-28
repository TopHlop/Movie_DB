package com.example.themoviedb.model;

import androidx.lifecycle.LiveData;

import com.example.themoviedb.data.RequestTokenAnswerWrap;
import com.example.themoviedb.data.SessionIdWrap;

public interface LoginModel {
    LiveData<RequestTokenAnswerWrap> getCreatedRequestToken();
    LiveData<RequestTokenAnswerWrap> getValidatedRequestToken();
    LiveData<SessionIdWrap> getCreatedSessionId();
    void createRequestToken();
    void clearDisposable();
    void validateRequestToken(String username, String password, String requestToken);
    void createSessionId(String requestToken);
}
