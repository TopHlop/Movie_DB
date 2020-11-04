package com.example.themoviedb.main.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.themoviedb.main.data.FilmWrap;

import java.util.List;

public interface FilmsSearchModelUseCase {
    void searchFilms(String query);
    void clearDisposable();
    LiveData<List<FilmWrap>> getResultSearch();
    LiveData<String> getQueryString();
}
