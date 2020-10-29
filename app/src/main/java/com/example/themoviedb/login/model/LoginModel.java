package com.example.themoviedb.login.model;

import androidx.lifecycle.LiveData;

import com.example.themoviedb.login.data.RequestTokenAnswerWrap;
import com.example.themoviedb.login.data.SessionIdWrap;

public interface LoginModel {
    LiveData<RequestTokenAnswerWrap> getCreatedRequestToken();
    LiveData<RequestTokenAnswerWrap> getValidatedRequestToken();
    LiveData<SessionIdWrap> getCreatedSessionId();
    void createRequestToken();
    void clearDisposable();
    void validateRequestToken(String username, String password, String requestToken);
    void createSessionId(String requestToken);
}
