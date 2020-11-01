package com.example.themoviedb.di.component;

import com.example.themoviedb.login.ui.LoginFragment;
import com.example.themoviedb.main.model.BaseMainModel;
import com.example.themoviedb.di.module.ApiModule;
import com.example.themoviedb.di.module.AppModule;
import com.example.themoviedb.di.module.ViewModelModule;
import com.example.themoviedb.login.model.LoginModelImpl;
import com.example.themoviedb.login.viewModel.LoginViewModel;
import com.example.themoviedb.main.ui.fragment.UserFragment;
import com.example.themoviedb.main.viewModel.UserViewModel;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApiModule.class, ViewModelModule.class, AppModule.class})
public interface AppComponent {
    void inject(LoginModelImpl loginModel);
    void inject(LoginViewModel loginViewModel);
    void inject(UserViewModel userViewModel);
    void inject(UserFragment userFragment);
    void inject(BaseMainModel baseMainModel);
    void inject(LoginFragment loginFragment);
}
