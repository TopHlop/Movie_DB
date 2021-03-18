package com.example.themoviedb.main.model;

import com.example.themoviedb.main.data.FilmWrap;
import com.example.themoviedb.main.data.GenresListWrap;
import com.example.themoviedb.main.data.UserWrap;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DataStorage {

    private List<FilmWrap> filmsSearch;
    private List<FilmWrap> favoriteFilms;
    private String querySearch;
    private UserWrap userData;
    private List<GenresListWrap.Genre> genres;

    @Inject
    public DataStorage() {

    }

    public List<GenresListWrap.Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<GenresListWrap.Genre> genres) {
        this.genres = genres;
    }

    public UserWrap getUserData() {
        return userData;
    }

    public void setUserData(UserWrap userData) {
        this.userData = userData;
    }

    public List<FilmWrap> getFilmsSearch() {
        return filmsSearch;
    }

    public void setFilmsSearch(List<FilmWrap> filmsSearch) {
        this.filmsSearch = filmsSearch;
    }

    public List<FilmWrap> getFavoriteFilms() {
        return favoriteFilms;
    }

    public void setFavoriteFilms(List<FilmWrap> favoriteFilms) {
        this.favoriteFilms = favoriteFilms;
    }

    public void addFavoriteFilm(FilmWrap film) {
        favoriteFilms.add(film);
    }

    public void removeFavoriteFilm(FilmWrap film) {
        favoriteFilms.remove(film);
    }

    public String getQuerySearch() {
        return querySearch;
    }

    public void setQuerySearch(String querySearch) {
        this.querySearch = querySearch;
    }

    public void clearData() {
        filmsSearch = null;
        favoriteFilms = null;
        querySearch = null;
        userData = null;
        genres = null;
    }
}
