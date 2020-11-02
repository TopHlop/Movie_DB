package com.example.themoviedb.main.model;

import android.content.Context;

import com.example.themoviedb.R;
import com.example.themoviedb.di.DI;
import com.example.themoviedb.main.network.MainService;

import io.reactivex.disposables.CompositeDisposable;

public abstract class BaseMainModel {
    protected MainService apiMainService;

    protected CompositeDisposable disposable;
    protected String apiKey;

    public BaseMainModel(MainService apiMainService, Context context) {
        this.apiMainService = apiMainService;
        DI.getAppComponent().inject(this);
        disposable = new CompositeDisposable();
        apiKey = context.getResources().getString(R.string.api_key);
    }

}
