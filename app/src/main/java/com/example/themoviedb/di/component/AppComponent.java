package com.example.themoviedb.di.component;

import com.example.themoviedb.SharedPreferencesHelper;
import com.example.themoviedb.di.module.ApiModule;
import com.example.themoviedb.di.module.AppModule;
import com.example.themoviedb.di.module.ViewModelModule;
import com.example.themoviedb.login.ui.LoginActivity;
import com.example.themoviedb.login.model.LoginModelImpl;
import com.example.themoviedb.login.viewModel.LoginViewModel;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApiModule.class, ViewModelModule.class, AppModule.class})
public interface AppComponent {
    void inject(LoginActivity loginActivity);
    void inject(LoginModelImpl loginModel);
    void inject(LoginViewModel loginViewModel);

    SharedPreferencesHelper getSharedPreferencesHelper();
}
