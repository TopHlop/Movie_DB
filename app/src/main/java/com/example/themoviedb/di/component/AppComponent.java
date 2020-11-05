package com.example.themoviedb.di.component;

import com.example.themoviedb.login.ui.LoginFragment;
import com.example.themoviedb.di.module.ApiModule;
import com.example.themoviedb.di.module.AppModule;
import com.example.themoviedb.di.module.ViewModelModule;
import com.example.themoviedb.main.ui.fragment.FavoritesFragment;
import com.example.themoviedb.main.ui.fragment.FilmsFragment;
import com.example.themoviedb.main.ui.fragment.UserFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApiModule.class, ViewModelModule.class, AppModule.class})
public interface AppComponent {
    void inject(UserFragment userFragment);
    void inject(LoginFragment loginFragment);
    void inject(FilmsFragment filmsFragment);
    void inject(FavoritesFragment favoritesFragment);
}
