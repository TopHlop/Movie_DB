package com.example.themoviedb.login.model;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.themoviedb.R;
import com.example.themoviedb.di.DI;
import com.example.themoviedb.login.data.ErrorLoginWrap;
import com.example.themoviedb.login.data.RequestTokenResponseWrap;
import com.example.themoviedb.login.data.RequestTokenWrap;
import com.example.themoviedb.login.data.SessionIdWrap;
import com.example.themoviedb.login.data.UserDataWrap;
import com.example.themoviedb.login.network.LoginService;
import com.google.gson.Gson;

import java.util.Objects;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;

public class LoginModelImpl implements LoginUseCase {

    private static final String TAG = "LoginModelImpl";

    private LoginService apiLoginService;
    private Gson gson;

    private String apiKey;
    private final MutableLiveData<RequestTokenResponseWrap> createdRequestToken = new MutableLiveData<>();
    private final MutableLiveData<RequestTokenResponseWrap> validatedRequestToken = new MutableLiveData<>();
    private final MutableLiveData<SessionIdWrap> createdSessionId = new MutableLiveData<>();
    private final MutableLiveData<Integer> errorCode = new MutableLiveData<>();

    private CompositeDisposable disposable;

    @Inject
    public LoginModelImpl(LoginService apiLoginService, Context context, Gson gson) {
        DI.getAppComponent().inject(this);
        this.apiLoginService = apiLoginService;
        this.gson = gson;
        apiKey = context.getResources().getString(R.string.api_key);
        disposable = new CompositeDisposable();
    }

    @Override
    public void clearDisposable() {
        if (disposable != null) {
            disposable.clear();
        }
    }

    @Override
    public LiveData<RequestTokenResponseWrap> getCreatedRequestToken() {
        return createdRequestToken;
    }

    @Override
    public LiveData<RequestTokenResponseWrap> getValidatedRequestToken() {
        return validatedRequestToken;
    }

    @Override
    public LiveData<SessionIdWrap> getCreatedSessionId() {
        return createdSessionId;
    }

    public LiveData<Integer> getErrorCode() {
        return errorCode;
    }

    @Override
    public void createRequestToken() {
        disposable.add(apiLoginService.createRequestToken(apiKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<RequestTokenResponseWrap>() {
                    @Override
                    public void onSuccess(RequestTokenResponseWrap requestTokenResponseWrap) {
                        createdRequestToken.postValue(requestTokenResponseWrap);
                    }

                    @Override
                    public void onError(Throwable e) {
                        setErrorCode(e);
                    }
                }));
    }

    @Override
    public void validateRequestToken(String username, String password, String requestToken) {
        disposable.add(apiLoginService.validateRequestToken(apiKey,
                new UserDataWrap(username, password, requestToken))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<RequestTokenResponseWrap>() {
                    @Override
                    public void onSuccess(RequestTokenResponseWrap requestTokenResponseWrap) {
                        validatedRequestToken.postValue(requestTokenResponseWrap);
                    }

                    @Override
                    public void onError(Throwable e) {
                        setErrorCode(e);
                    }
                }));
    }

    @Override
    public void createSessionId(String requestToken) {
        disposable.add(apiLoginService.createSession(apiKey,
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
                        setErrorCode(e);
                    }
                }));
    }

    private void setErrorCode(Throwable e) {
        HttpException error = (HttpException) e;
        ErrorLoginWrap errorLogin = gson.fromJson(Objects.requireNonNull(Objects.requireNonNull(error.response()).errorBody()).charStream(),
                ErrorLoginWrap.class);
        errorCode.postValue(errorLogin.getStatusCode());
        Log.e(TAG, errorLogin.getStatusMessage());
    }
}
