package com.example.themoviedb.main.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.themoviedb.SharedPreferencesHelper;
import com.example.themoviedb.main.data.FilmWrap;
import com.example.themoviedb.main.model.FavoritesLoadModelImpl;
import com.example.themoviedb.main.model.FavoritesLoadModelUseCase;
import com.example.themoviedb.main.model.FilmSearchModelImpl;
import com.example.themoviedb.main.model.DataStorage;

import java.util.List;

import javax.inject.Inject;

public class DescriptionFilmViewModel extends ViewModel {

    private FavoritesLoadModelUseCase favoritesLoadModel;
    private DataStorage storage;
    private MutableLiveData<Boolean> isFavorite = new MutableLiveData<>();
    private SharedPreferencesHelper sharedPreferencesHelper;

    private FilmWrap film;

    @Inject
    public DescriptionFilmViewModel(FavoritesLoadModelImpl favoritesLoadModel,
                                    SharedPreferencesHelper sharedPreferencesHelper, DataStorage storage) {
        this.favoritesLoadModel = favoritesLoadModel;
        this.sharedPreferencesHelper = sharedPreferencesHelper;
        this.storage = storage;
        isFavorite.setValue(false);
        observeLiveData();
    }

    private void observeLiveData() {
        favoritesLoadModel.getMarkFilmAsFavoriteSuccessful().observeForever(markFilmAsFavoriteSuccessful -> {
            if(markFilmAsFavoriteSuccessful != null) {
                if (markFilmAsFavoriteSuccessful) {
                    if (isFavorite.getValue()) {
                        isFavorite.setValue(false);
                        removeFavoriteFilm();
                    } else {
                        isFavorite.setValue(true);
                        addFavoriteFilm();
                    }
                }
            }
        });
    }

    private void addFavoriteFilm() {
        storage.addFavoriteFilm(film);
    }

    private void removeFavoriteFilm() {
        storage.removeFavoriteFilm(film);
    }

    public FilmWrap getFilm(String sourceName, int filmId) {
        List<FilmWrap> favorites = storage.getFavoriteFilms();
        switch (sourceName) {
            case FavoritesLoadModelImpl
                    .FAVORITES_SOURCE:
                for(FilmWrap film : favorites) {
                    if(film.getId() == filmId) {
                        this.film = film;
                        isFavorite.setValue(true);
                        return film;
                    }
                }
                return null;
            case FilmSearchModelImpl
                    .FILM_SEARCH_SOURCE:
            default:
                for(FilmWrap film : storage.getFilmsSearch()) {
                    if(film.getId() == filmId) {
                        this.film = film;
                        isFavorite.setValue(checkFilmIsFavorite(favorites, film));
                        return film;
                    }
                }
                return null;
        }
    }

    public LiveData<Boolean> getIsFavorite() {
        return isFavorite;
    }

    public void setFilmFavorite(boolean favorite, int mediaId) {
         String sessionId = sharedPreferencesHelper.getString(SharedPreferencesHelper.KEY_SESSION_ID, null);
         if(sessionId != null) {
             favoritesLoadModel.markFilmAsFavorite(storage.getUserData().getId(), sessionId, mediaId, favorite);
         } else {
             //ошибка хранения sessionId
         }
    }

    private boolean checkFilmIsFavorite(List<FilmWrap> favorites, FilmWrap film) {
        for(FilmWrap filmFavorite : favorites) {
            if(filmFavorite.getId() == film.getId())
                return true;
        }
        return false;
    }
}
