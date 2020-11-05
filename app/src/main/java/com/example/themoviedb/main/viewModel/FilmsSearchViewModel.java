package com.example.themoviedb.main.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.themoviedb.SharedPreferencesHelper;
import com.example.themoviedb.main.data.FilmWrap;
import com.example.themoviedb.main.model.FilmSearchModelImpl;
import com.example.themoviedb.main.model.FilmsSearchModelUseCase;
import com.example.themoviedb.main.model.UserModelImpl;
import com.example.themoviedb.main.model.UserModelUseCase;
import com.example.themoviedb.main.ui.RecyclerViewForm;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

public class FilmsSearchViewModel extends ViewModel {

    private SharedPreferencesHelper sharedPreferencesHelper;
    private FilmsSearchModelUseCase filmsSearchModel;
    private UserModelUseCase userModel;

    @Inject
    public FilmsSearchViewModel(FilmSearchModelImpl filmSearchModel, UserModelImpl userModel,
                                SharedPreferencesHelper sharedPreferencesHelper) {
        this.sharedPreferencesHelper = sharedPreferencesHelper;
        this.userModel = userModel;
        this.filmsSearchModel = filmSearchModel;
        loadUserData();
    }

    //get accountId for add, delete, load favorites
    private void loadUserData() {
        if(userModel.getUser().getValue() == null) {
            String sessionId = sharedPreferencesHelper.getString(SharedPreferencesHelper.KEY_SESSION_ID, null);
            if (sessionId != null) {
                userModel.getUserData(sessionId);
            } else {
                //ошибка хранения sessionId
            }
        }
    }

    public void searchFilms(String query) {
        filmsSearchModel.searchFilms(query, Objects.requireNonNull(userModel.getUser().getValue()).isIncludeAdult());
    }

    public LiveData<List<FilmWrap>> getResultSearch() {
        return filmsSearchModel.getResultSearch();
    }

    public LiveData<String> getQueryString() {
        return filmsSearchModel.getQueryString();
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
        filmsSearchModel.clearDisposable();
    }
}
