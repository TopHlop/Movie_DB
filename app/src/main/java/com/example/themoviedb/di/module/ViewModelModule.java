package com.example.themoviedb.di.module;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.themoviedb.login.viewModel.LoginViewModel;
import com.example.themoviedb.main.viewModel.FavoritesLoadViewModel;
import com.example.themoviedb.main.viewModel.FilmsSearchViewModel;
import com.example.themoviedb.main.viewModel.MainViewModel;
import com.example.themoviedb.main.viewModel.UserViewModel;
import com.example.themoviedb.main.viewModel.ViewModelFactory;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel.class)
    abstract ViewModel bindMainViewModel(MainViewModel mainViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel.class)
    abstract ViewModel bindLoginViewModel(LoginViewModel loginViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(UserViewModel.class)
    abstract ViewModel bindUserViewModel(UserViewModel userViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(FilmsSearchViewModel.class)
    abstract ViewModel bindFilmsSearchViewModel(FilmsSearchViewModel filmsSearchViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(FavoritesLoadViewModel.class)
    abstract ViewModel bindFavoritesLoadViewModel(FavoritesLoadViewModel favoritesLoadViewModel);


    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory viewModelFactory);
}
