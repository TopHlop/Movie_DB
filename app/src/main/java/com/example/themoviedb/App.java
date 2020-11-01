package com.example.themoviedb;

import android.app.Application;

import com.example.themoviedb.di.DI;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        DI.setAppComponent(this);
    }
}
