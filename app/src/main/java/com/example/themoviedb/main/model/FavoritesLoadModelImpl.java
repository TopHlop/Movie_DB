package com.example.themoviedb.main.model;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.themoviedb.main.data.FilmWrap;
import com.example.themoviedb.main.data.MarkFilmAsFavoriteBodyRequestWrap;
import com.example.themoviedb.main.data.MarkFilmAsFavoriteResponseWrap;
import com.example.themoviedb.main.data.PagingEnvelope;
import com.example.themoviedb.main.network.MainService;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class FavoritesLoadModelImpl extends BaseMainModel implements FavoritesLoadModelUseCase {

    public static final String FAVORITES_SOURCE = "favorites";

    private MutableLiveData<List<FilmWrap>> favoritesFilmsResult = new MutableLiveData<>();
    private MutableLiveData<Boolean> markFilmAsFavoriteSuccessful = new MutableLiveData<>();

    @Inject
    public FavoritesLoadModelImpl(MainService apiMainService, Context context) {
        super(apiMainService, context);
    }

    public LiveData<List<FilmWrap>> getFavoritesFilmsResult() {
        return favoritesFilmsResult;
    }

    public LiveData<Boolean> getMarkFilmAsFavoriteSuccessful() {
        return markFilmAsFavoriteSuccessful;
    }

    public void loadFavorites(int accountId, String sessionId) {
        disposable.add(apiMainService.getFavouriteMovies(accountId, apiKey, sessionId, language, 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<PagingEnvelope<FilmWrap>>() {
                    @Override
                    public void onSuccess(PagingEnvelope<FilmWrap> response) {
                        favoritesFilmsResult.postValue(response.getResults());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(this.getClass().getName(), "error load favorites: " + e.getMessage());
                    }
                }));
    }

    public void markFilmAsFavorite(int accountId, String sessionId, int mediaId, boolean favorite) {
        disposable.add(apiMainService.markFilmAsFavorite(accountId, apiKey, sessionId,
                new MarkFilmAsFavoriteBodyRequestWrap("movie", mediaId, favorite))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<MarkFilmAsFavoriteResponseWrap>() {
                    @Override
                    public void onSuccess(MarkFilmAsFavoriteResponseWrap response) {
                        if(response.isSuccess()) {
                            markFilmAsFavoriteSuccessful.postValue(true);
                        } else {
                            markFilmAsFavoriteSuccessful.postValue(false);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(this.getClass().getName(), "error mark film as favorite: " + e.getMessage());
                    }
                }));
    }

    public void clearDisposable() {
        if (disposable != null) {
            disposable.clear();
        }
    }
}
