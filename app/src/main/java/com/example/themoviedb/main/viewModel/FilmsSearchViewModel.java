package com.example.themoviedb.main.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.themoviedb.SharedPreferencesHelper;
import com.example.themoviedb.main.data.FilmWrap;
import com.example.themoviedb.main.model.FilmSearchModelImpl;
import com.example.themoviedb.main.model.FilmsSearchModelUseCase;
import com.example.themoviedb.main.ui.RecyclerViewForm;

import java.util.List;

import javax.inject.Inject;

public class FilmsSearchViewModel extends ViewModel {

    private SharedPreferencesHelper sharedPreferencesHelper;
    private FilmsSearchModelUseCase filmsSearchModel;

    @Inject
    public FilmsSearchViewModel(FilmSearchModelImpl filmSearchModel, SharedPreferencesHelper sharedPreferencesHelper) {
        this.sharedPreferencesHelper = sharedPreferencesHelper;
        filmsSearchModel = filmSearchModel;
    }

    public void searchFilms(String query) {
        filmsSearchModel.searchFilms(query);
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
