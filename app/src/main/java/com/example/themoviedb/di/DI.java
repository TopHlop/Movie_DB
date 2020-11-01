package com.example.themoviedb.di;

import android.content.Context;

import com.example.themoviedb.R;
import com.example.themoviedb.di.component.AppComponent;
import com.example.themoviedb.di.component.DaggerAppComponent;
import com.example.themoviedb.di.module.ApiModule;
import com.example.themoviedb.di.module.AppModule;

public class DI {
    private static AppComponent appComponent;

    public static void setAppComponent(Context context) {
        appComponent = DaggerAppComponent.builder()
                .apiModule(new ApiModule(context.getString(R.string.base_url)))
                .appModule(new AppModule(context))
                .build();
    }

    public static AppComponent getAppComponent() {
       return appComponent;
    }
}
