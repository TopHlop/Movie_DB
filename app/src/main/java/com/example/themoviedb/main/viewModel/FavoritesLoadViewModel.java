package com.example.themoviedb.main.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.themoviedb.SharedPreferencesHelper;
import com.example.themoviedb.main.data.FilmWrap;
import com.example.themoviedb.main.model.DataStorage;
import com.example.themoviedb.main.model.FavoritesLoadModelImpl;
import com.example.themoviedb.main.model.FavoritesLoadModelUseCase;
import com.example.themoviedb.main.ui.RecyclerViewForm;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class FavoritesLoadViewModel extends ViewModel {

    private FavoritesLoadModelUseCase favoritesSearchModel;
    private SharedPreferencesHelper sharedPreferencesHelper;
    private DataStorage storage;


    @Inject
    public FavoritesLoadViewModel(FavoritesLoadModelImpl favoritesSearchModel,
                                  SharedPreferencesHelper sharedPreferencesHelper, DataStorage storage) {
        this.favoritesSearchModel = favoritesSearchModel;
        this.storage = storage;
        this.sharedPreferencesHelper = sharedPreferencesHelper;
    }

    public void loadFavoriteFilms() {
        if(storage.getFavoriteFilms() == null) {
            String sessionId = sharedPreferencesHelper.getString(SharedPreferencesHelper.KEY_SESSION_ID, null);
            int accountId = storage.getUserData().getId();
            if(sessionId != null) {
                favoritesSearchModel.loadFavorites(accountId, sessionId);
            } else {
                //ошибка хранения sessionId
            }
        }
    }

    public List<FilmWrap> searchFilmsAmongFavorites(String query) {
        List<FilmWrap> favorites = storage.getFavoriteFilms();
        List<FilmWrap> searchFilms = new ArrayList<>();
        for(FilmWrap film : favorites) {
            if(film.getTitle().toLowerCase().contains(query) || film.getOriginalTitle().toLowerCase().contains(query)) {
                searchFilms.add(film);
            }
        }
        return searchFilms;
    }

    public List<FilmWrap> getFavoriteFilms() {
        return storage.getFavoriteFilms();
    }

    public LiveData<List<FilmWrap>> getFavoriteFilmsResult() {
        LiveData<List<FilmWrap>> favoritesFilmsResult = favoritesSearchModel.getFavoritesFilmsResult();
        favoritesFilmsResult.observeForever(favorites -> {
            storage.setFavoriteFilms(favorites);
        });
        return favoritesFilmsResult;
    }

    public void setRecyclerViewForm(RecyclerViewForm recyclerViewForm) {
        sharedPreferencesHelper.putRecyclerViewForm(recyclerViewForm);
    }

    public RecyclerViewForm getRecyclerViewForm() {
        return sharedPreferencesHelper.getRecyclerViewForm();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        favoritesSearchModel.clearDisposable();
    }
}
