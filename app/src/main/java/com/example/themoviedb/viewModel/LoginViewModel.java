package com.example.themoviedb.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<Boolean> isSuccessAuth = new MutableLiveData<>();
    private MutableLiveData<String> errorMessage = new MutableLiveData<>();

    @Inject
    public LoginViewModel() {
    }

    public void loginUser(String username, String password) {

    }

    public LiveData<Boolean> getIsSuccessAuth() {
        return isSuccessAuth;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }
}
