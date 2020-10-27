package com.example.themoviedb;

import android.app.Application;

import com.example.themoviedb.di.component.AppComponent;
import com.example.themoviedb.di.component.DaggerAppComponent;
import com.example.themoviedb.di.module.ApiModule;

public class App extends Application {

    private static AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.builder()
                .apiModule(new ApiModule(getString(R.string.base_url)))
                .build();
    }

    public static AppComponent getAppComponent() {
        return appComponent;
    }
}
