package com.example.themoviedb.main.model;

import androidx.lifecycle.LiveData;

import com.example.themoviedb.main.data.FilmWrap;
import com.example.themoviedb.main.data.GenresListWrap;

import java.util.List;

public interface FilmsSearchModelUseCase {
    void searchFilms(String query, boolean includeAdult);
    void clearDisposable();
    void loadGenreList();
    LiveData<List<GenresListWrap.Genre>> getGenreList();
    LiveData<List<FilmWrap>> getResultSearch();
}
