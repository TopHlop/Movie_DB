package com.example.themoviedb.main.viewModel;

import androidx.lifecycle.ViewModel;

import com.example.themoviedb.main.model.FavoritesLoadModelImpl;
import com.example.themoviedb.main.model.FavoritesLoadModelUseCase;
import com.example.themoviedb.main.model.FilmSearchModelImpl;
import com.example.themoviedb.main.model.FilmsSearchModelUseCase;
import com.example.themoviedb.main.model.UserModelImpl;
import com.example.themoviedb.main.model.UserModelUseCase;

import javax.inject.Inject;

public class MainViewModel extends ViewModel {

    private UserModelUseCase userModel;
    private FavoritesLoadModelUseCase favoritesLoadModel;
    private FilmsSearchModelUseCase filmsSearchModel;

    @Inject
    public MainViewModel(UserModelImpl userModel, FavoritesLoadModelImpl favoritesLoadModel,
                         FilmSearchModelImpl filmSearchModel) {
        this.userModel = userModel;
        this.favoritesLoadModel = favoritesLoadModel;
        this.filmsSearchModel = filmSearchModel;
    }

    public void clearData() {
        filmsSearchModel.clearData();
        favoritesLoadModel.clearData();
        userModel.clearData();
    }
}
