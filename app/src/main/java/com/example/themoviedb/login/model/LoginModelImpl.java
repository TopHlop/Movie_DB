package com.example.themoviedb.login.model;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.themoviedb.R;
import com.example.themoviedb.login.data.ErrorLoginWrap;
import com.example.themoviedb.login.data.RequestTokenResponseWrap;
import com.example.themoviedb.login.data.RequestTokenWrap;
import com.example.themoviedb.login.data.SessionIdWrap;
import com.example.themoviedb.login.data.UserDataWrap;
import com.example.themoviedb.login.network.LoginService;
import com.google.gson.Gson;

import java.util.Objects;

import javax.inject.Inject;

import io.reactivex.SingleSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;

public class LoginModelImpl implements LoginUseCase {

    private static final String TAG = "LoginModelImpl";

    private LoginService apiLoginService;
    private Gson gson;

    private String apiKey;
    private final MutableLiveData<SessionIdWrap> createdSessionId = new MutableLiveData<>();
    private final MutableLiveData<Integer> errorCode = new MutableLiveData<>();

    private CompositeDisposable disposable;

    @Inject
    public LoginModelImpl(LoginService apiLoginService, Context context, Gson gson) {
        this.apiLoginService = apiLoginService;
        this.gson = gson;
        apiKey = context.getResources().getString(R.string.api_key);
        disposable = new CompositeDisposable();
    }

    public void clearDisposable() {
        if (disposable != null) {
            disposable.clear();
        }
    }

    public LiveData<SessionIdWrap> getCreatedSessionId() {
        return createdSessionId;
    }

    public LiveData<Integer> getErrorCode() {
        return errorCode;
    }

    @SuppressLint("CheckResult")
    public void loginUser(String username, String password) {
        disposable.add(apiLoginService.createRequestToken(apiKey)
                .flatMap((Function<RequestTokenResponseWrap, SingleSource<RequestTokenResponseWrap>>) requestTokenResponseWrap ->
                        apiLoginService.validateRequestToken(apiKey,
                                new UserDataWrap(username, password, requestTokenResponseWrap.getRequestToken())))
                .flatMap((Function<RequestTokenResponseWrap, SingleSource<SessionIdWrap>>) requestTokenResponseWrap ->
                        apiLoginService.createSession(apiKey,
                                new RequestTokenWrap(requestTokenResponseWrap.getRequestToken())))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(createdSessionId::postValue, this::setErrorCode));
    }

    private void setErrorCode(Throwable e) {
        HttpException error = (HttpException) e;
        ErrorLoginWrap errorLogin = gson.fromJson(Objects.requireNonNull(Objects.requireNonNull(error.response()).errorBody()).charStream(),
                ErrorLoginWrap.class);
        errorCode.postValue(errorLogin.getStatusCode());
        Log.e(TAG, errorLogin.getStatusMessage());
    }
}
