package com.example.themoviedb.main.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.themoviedb.SharedPreferencesHelper;
import com.example.themoviedb.main.data.FilmWrap;
import com.example.themoviedb.main.model.FavoritesLoadModelImpl;
import com.example.themoviedb.main.model.FavoritesLoadModelUseCase;
import com.example.themoviedb.main.model.UserModelImpl;
import com.example.themoviedb.main.model.UserModelUseCase;
import com.example.themoviedb.main.ui.RecyclerViewForm;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

public class FavoritesLoadViewModel extends ViewModel {

    private FavoritesLoadModelUseCase favoritesSearchModel;
    private SharedPreferencesHelper sharedPreferencesHelper;
    private UserModelUseCase userModel;


    @Inject
    public FavoritesLoadViewModel(FavoritesLoadModelImpl favoritesSearchModel,
                                  UserModelImpl userModel,
                                  SharedPreferencesHelper sharedPreferencesHelper) {
        this.favoritesSearchModel = favoritesSearchModel;
        this.userModel = userModel;
        this.sharedPreferencesHelper = sharedPreferencesHelper;
    }

    public void loadFavoriteFilms() {
        if(favoritesSearchModel.getFavoritesFilmsResult().getValue() == null) {
            String sessionId = sharedPreferencesHelper.getString(SharedPreferencesHelper.KEY_SESSION_ID, null);
            int accountId = Objects.requireNonNull(userModel.getUser().getValue()).getId();
            if(sessionId != null) {
                favoritesSearchModel.loadFavorites(accountId, sessionId);
            } else {
                //ошибка хранения sessionId
            }
        }
    }

    public LiveData<List<FilmWrap>> getFavoriteFilmsResult() {
        return favoritesSearchModel.getFavoritesFilmsResult();
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
