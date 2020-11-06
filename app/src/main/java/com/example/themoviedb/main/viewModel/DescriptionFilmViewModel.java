package com.example.themoviedb.main.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.themoviedb.main.data.FilmWrap;
import com.example.themoviedb.main.model.FavoritesLoadModelImpl;
import com.example.themoviedb.main.model.FavoritesLoadModelUseCase;
import com.example.themoviedb.main.model.FilmSearchModelImpl;
import com.example.themoviedb.main.model.FilmsSearchModelUseCase;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

public class DescriptionFilmViewModel extends ViewModel {

    private FavoritesLoadModelUseCase favoritesLoadModel;
    private FilmsSearchModelUseCase filmsSearchModel;
    private MutableLiveData<Boolean> isFavorite = new MutableLiveData<>();

    @Inject
    public DescriptionFilmViewModel(FilmSearchModelImpl filmSearchModel,
                                    FavoritesLoadModelImpl favoritesLoadModel) {
        this.favoritesLoadModel = favoritesLoadModel;
        this.filmsSearchModel = filmSearchModel;
    }

    public FilmWrap getFilm(String sourceName, int filmId) {
        List<FilmWrap> favorites = Objects.requireNonNull(favoritesLoadModel.getFavoritesFilmsResult().getValue());
        switch (sourceName) {
            case FavoritesLoadModelImpl
                    .FAVORITES_SOURCE:
                for(FilmWrap film : favorites) {
                    if(film.getId() == filmId) {
                        isFavorite.setValue(true);
                        return film;
                    }
                }
                return null;
            case FilmSearchModelImpl
                    .FILM_SEARCH_SOURCE:
            default:
                for(FilmWrap film : Objects.requireNonNull(filmsSearchModel.getResultSearch().getValue())) {
                    if(film.getId() == filmId) {
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

    private boolean checkFilmIsFavorite(List<FilmWrap> favorites, FilmWrap film) {
        for(FilmWrap filmFavorite : favorites) {
            if(filmFavorite.getId() == film.getId())
                return true;
        }
        return false;
    }
}
