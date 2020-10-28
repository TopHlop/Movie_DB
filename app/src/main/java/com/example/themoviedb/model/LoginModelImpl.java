package com.example.themoviedb.model;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.themoviedb.App;
import com.example.themoviedb.R;
import com.example.themoviedb.data.RequestTokenAnswerWrap;
import com.example.themoviedb.data.RequestTokenWrap;
import com.example.themoviedb.data.SessionIdWrap;
import com.example.themoviedb.data.UserDataWrap;
import com.example.themoviedb.network.Service;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class LoginModelImpl implements LoginModel {

    private static final String TAG = "LoginModelImpl";

    @Inject
    Service apiService;
    @Inject
    Context context;

    private String apiKey;
    private final MutableLiveData<RequestTokenAnswerWrap> createdRequestToken = new MutableLiveData<>();
    private final MutableLiveData<RequestTokenAnswerWrap> validatedRequestToken = new MutableLiveData<>();
    private final MutableLiveData<SessionIdWrap> createdSessionId = new MutableLiveData<>();
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();

    private CompositeDisposable disposable;

    private static LoginModelImpl loginModel;

    public static LoginModelImpl getInstance() {
        if (loginModel == null) {
            loginModel = new LoginModelImpl();
        }
        return loginModel;
    }

    public LoginModelImpl() {
        App.getAppComponent().inject(this);
        disposable = new CompositeDisposable();
        apiKey = context.getResources().getString(R.string.api_key);
    }

    @Override
    public void clearDisposable() {
        if (disposable != null) {
            disposable.clear();
            disposable = null;
        }
    }

    @Override
    public LiveData<RequestTokenAnswerWrap> getCreatedRequestToken() {
        return createdRequestToken;
    }

    @Override
    public LiveData<RequestTokenAnswerWrap> getValidatedRequestToken() {
        return validatedRequestToken;
    }

    @Override
    public LiveData<SessionIdWrap> getCreatedSessionId() {
        return createdSessionId;
    }

    @Override
    public void createRequestToken() {
        disposable.add(apiService.createRequestToken(apiKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<RequestTokenAnswerWrap>() {
                    @Override
                    public void onSuccess(RequestTokenAnswerWrap requestTokenAnswerWrap) {
                        createdRequestToken.postValue(requestTokenAnswerWrap);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "error create request token: " + e.getMessage());
                    }
                }));
    }

    @Override
    public void validateRequestToken(String username, String password, String requestToken) {
        disposable.add(apiService.validateRequestToken(apiKey,
                new UserDataWrap(username, password, requestToken))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<RequestTokenAnswerWrap>() {
                    @Override
                    public void onSuccess(RequestTokenAnswerWrap requestTokenAnswerWrap) {
                        validatedRequestToken.postValue(requestTokenAnswerWrap);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "error validate request token: " + e.getMessage());
                    }
                }));
    }

    @Override
    public void createSessionId(String requestToken) {
        disposable.add(apiService.createSession(apiKey,
                new RequestTokenWrap(requestToken))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<SessionIdWrap>() {
                    @Override
                    public void onSuccess(SessionIdWrap sessionIdWrap) {
                        createdSessionId.postValue(sessionIdWrap);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "error create session id: " + e.getMessage());
                    }
                }));
    }
}
