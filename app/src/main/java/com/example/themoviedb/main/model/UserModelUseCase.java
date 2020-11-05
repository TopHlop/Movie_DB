package com.example.themoviedb.main.model;

import androidx.lifecycle.LiveData;

import com.example.themoviedb.main.data.DeleteSessionResponseWrap;
import com.example.themoviedb.main.data.UserWrap;

public interface UserModelUseCase {
    LiveData<DeleteSessionResponseWrap> getDeleteSessionResponse();
    void deleteSession(String sessionId);
    void getUserData(String sessionId);
    LiveData<UserWrap> getUser();
    void clearDisposable();
    void clearData();
}
