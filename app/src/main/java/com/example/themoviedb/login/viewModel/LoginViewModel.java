package com.example.themoviedb.login.viewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.themoviedb.SharedPreferencesHelper;
import com.example.themoviedb.di.DI;
import com.example.themoviedb.login.model.LoginUseCase;
import com.example.themoviedb.login.model.LoginModelImpl;

import javax.inject.Inject;

public class LoginViewModel extends ViewModel {

    private static final String TAG = "LoginViewModel";

    private SharedPreferencesHelper sharedPreferencesHelper;

    private MutableLiveData<Boolean> isSuccessLogin = new MutableLiveData<>();
    private LoginUseCase loginModel;

    @Inject
    public LoginViewModel(SharedPreferencesHelper sharedPreferencesHelper, LoginModelImpl loginModel) {
        this.loginModel = loginModel;
        this.sharedPreferencesHelper = sharedPreferencesHelper;
        DI.getAppComponent().inject(this);
        observeLiveData();
    }

    private void observeLiveData() {
        loginModel.getCreatedSessionId().observeForever(createdSessionId -> {
            isSuccessLogin.postValue(createdSessionId.isSuccess());
            sharedPreferencesHelper.putString(SharedPreferencesHelper.KEY_SESSION_ID,
                    createdSessionId.getSessionId());
            Log.d(TAG, "sessionId: " + createdSessionId.getSessionId());
        });
    }

    public boolean isUserLogin() {
        return (sharedPreferencesHelper.getString(SharedPreferencesHelper.KEY_SESSION_ID,
                null) != null);
    }

    public void loginUser(String username, String password) {
        loginModel.loginUser(username, password);
    }

    public LiveData<Boolean> getIsSuccessLogin() {
        return isSuccessLogin;
    }

    public LiveData<Integer> getErrorCode() {
        return loginModel.getErrorCode();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        loginModel.clearDisposable();
    }
}
