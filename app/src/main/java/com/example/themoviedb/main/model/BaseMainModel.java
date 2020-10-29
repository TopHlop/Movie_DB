package com.example.themoviedb.main.model;

import android.content.Context;

import com.example.themoviedb.App;
import com.example.themoviedb.R;
import com.example.themoviedb.main.network.MainService;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public abstract class BaseMainModel {
    @Inject
    protected MainService apiMainService;
    @Inject
    Context context;

    protected CompositeDisposable disposable;
    protected String apiKey;

    public BaseMainModel() {
        App.getAppComponent().inject(this);
        disposable = new CompositeDisposable();
        apiKey = context.getResources().getString(R.string.api_key);
    }

    public void clearDisposable() {
        if (disposable != null) {
            disposable.clear();
            disposable = null;
        }
    }
}
