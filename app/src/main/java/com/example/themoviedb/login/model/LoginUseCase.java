package com.example.themoviedb.login.model;

import androidx.lifecycle.LiveData;

import com.example.themoviedb.login.data.RequestTokenResponseWrap;
import com.example.themoviedb.login.data.SessionIdWrap;

import javax.inject.Singleton;

public interface LoginUseCase {
    LiveData<SessionIdWrap> getCreatedSessionId();
    LiveData<Integer> getErrorCode();
    void clearDisposable();
    void loginUser(String username, String password);
}
