package com.example.themoviedb.main.model;

import androidx.lifecycle.LiveData;

import com.example.themoviedb.main.data.FilmWrap;

import java.util.List;

public interface FavoritesLoadModelUseCase {
    void loadFavorites(int accountId, String sessionId);
    LiveData<List<FilmWrap>> getFavoritesFilmsResult();
    void clearDisposable();
}
