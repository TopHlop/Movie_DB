package com.example.themoviedb.di.component;

import com.example.themoviedb.di.module.ApiModule;
import com.example.themoviedb.di.module.AppModule;
import com.example.themoviedb.di.module.ViewModelModule;
import com.example.themoviedb.login.LoginActivity;
import com.example.themoviedb.model.LoginModelImpl;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApiModule.class, ViewModelModule.class, AppModule.class})
public interface AppComponent {
    void inject(LoginActivity loginActivity);
    void inject(LoginModelImpl loginModel);
}
