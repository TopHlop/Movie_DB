package com.example.themoviedb.model;

import com.example.themoviedb.App;
import com.example.themoviedb.network.Service;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import retrofit2.Retrofit;

public class LoginModel {

    @Inject
    Retrofit retrofit;

    private static LoginModel loginModel;
    private Service apiService;

    public static LoginModel getInstance() {
        if(loginModel == null) {
            loginModel = new LoginModel();
        }
        return loginModel;
    }

    public LoginModel() {
        App.getAppComponent().inject(this);
        apiService = retrofit.create(Service.class);
    }

    public void createRequestToken() {


    }

}
