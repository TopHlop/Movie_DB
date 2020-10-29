package com.example.themoviedb.main.model;

import androidx.lifecycle.LiveData;

import com.example.themoviedb.main.data.DeleteSessionResponseWrap;

public interface UserModel {
    LiveData<DeleteSessionResponseWrap> getDeleteSessionResponse();
    void deleteSession(String sessionId);
    void getUserData(String sessionId);
}
