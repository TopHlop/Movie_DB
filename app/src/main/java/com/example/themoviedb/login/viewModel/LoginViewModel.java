package com.example.themoviedb.login.viewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.themoviedb.App;
import com.example.themoviedb.SharedPreferencesHelper;
import com.example.themoviedb.login.model.LoginUseCase;
import com.example.themoviedb.login.model.LoginModelImpl;

import javax.inject.Inject;

public class LoginViewModel extends ViewModel {

    private static final String TAG = "LoginViewModel";

    @Inject
    SharedPreferencesHelper sharedPreferencesHelper;

    private MutableLiveData<Boolean> isSuccessLogin = new MutableLiveData<>();
    private MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private LoginUseCase loginModel;
    private String username;
    private String password;

    @Inject
    public LoginViewModel() {
        loginModel = LoginModelImpl.getInstance();
        App.getAppComponent().inject(this);
        observeLiveData();
    }

    private void observeLiveData() {
        loginModel.getCreatedRequestToken().observeForever(createdRequestToken -> {
            if(createdRequestToken.isSuccess()) {
                loginModel.validateRequestToken(username, password, createdRequestToken.getRequestToken());
                Log.d(TAG, "created token: " + createdRequestToken.getRequestToken());
            }
        });
        loginModel.getValidatedRequestToken().observeForever(validatedRequestToken -> {
            if(validatedRequestToken.isSuccess()) {
                loginModel.createSessionId(validatedRequestToken.getRequestToken());
                Log.d(TAG, "validated token: " + validatedRequestToken.getRequestToken());
            }
        });
        loginModel.getCreatedSessionId().observeForever(createdSessionId -> {
            if(createdSessionId.isSuccess()) {
                isSuccessLogin.postValue(true);
                sharedPreferencesHelper.putString(SharedPreferencesHelper.KEY_SESSION_ID,
                        createdSessionId.getSessionId());
                Log.d(TAG, "sessionId: " + createdSessionId.getSessionId());
            }
        });
    }

    public boolean isUserLogin() {
        return (sharedPreferencesHelper.getString(SharedPreferencesHelper.KEY_SESSION_ID,
                null) != null);
    }

    public void loginUser(String username, String password) {
        this.username = username;
        this.password = password;
        loginModel.createRequestToken();
    }

    public LiveData<Boolean> getIsSuccessLogin() {
        return isSuccessLogin;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        loginModel.clearDisposable();
    }
}
