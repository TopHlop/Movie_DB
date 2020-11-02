package com.example.themoviedb.login.model;

import androidx.lifecycle.LiveData;

import com.example.themoviedb.login.data.RequestTokenResponseWrap;
import com.example.themoviedb.login.data.SessionIdWrap;

import javax.inject.Singleton;

public interface LoginUseCase {
    LiveData<RequestTokenResponseWrap> getCreatedRequestToken();
    LiveData<RequestTokenResponseWrap> getValidatedRequestToken();
    LiveData<SessionIdWrap> getCreatedSessionId();
    void createRequestToken();
    void clearDisposable();
    void validateRequestToken(String username, String password, String requestToken);
    void createSessionId(String requestToken);
}
