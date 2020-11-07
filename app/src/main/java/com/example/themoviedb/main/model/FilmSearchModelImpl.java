package com.example.themoviedb.main.model;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.themoviedb.main.data.FilmWrap;
import com.example.themoviedb.main.data.PagingEnvelope;
import com.example.themoviedb.main.network.MainService;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class FilmSearchModelImpl extends BaseMainModel implements FilmsSearchModelUseCase {

    public static final String FILM_SEARCH_SOURCE = "filmSearch";

    MutableLiveData<List<FilmWrap>> resultSearch = new MutableLiveData<>();

    @Inject
    public FilmSearchModelImpl(MainService apiMainService, Context context) {
        super(apiMainService, context);
    }

    public LiveData<List<FilmWrap>> getResultSearch() {
        return resultSearch;
    }

    @Override
    public void searchFilms(String query, boolean includeAdult) {
        disposable.add(apiMainService.getMovies(apiKey, language, query, includeAdult, 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<PagingEnvelope<FilmWrap>>() {
                    @Override
                    public void onSuccess(PagingEnvelope<FilmWrap> response) {
                        resultSearch.postValue(response.getResults());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(this.getClass().getName(), "error search film: " + e.getMessage());
                    }
                }));
    }

    @Override
    public void clearDisposable() {
        if (disposable != null) {
            disposable.clear();
        }
    }
}
