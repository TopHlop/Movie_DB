package com.example.themoviedb.main.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.themoviedb.SharedPreferencesHelper;
import com.example.themoviedb.main.data.UserWrap;
import com.example.themoviedb.main.model.UserModelUseCase;
import com.example.themoviedb.main.model.UserModelImpl;

import javax.inject.Inject;

public class UserViewModel extends ViewModel {

    private SharedPreferencesHelper sharedPreferencesHelper;

    private UserModelUseCase userModel;
    private MutableLiveData<Boolean> isSuccessDeleteSession = new MutableLiveData<>();

    @Inject
    public UserViewModel(SharedPreferencesHelper sharedPreferencesHelper, UserModelImpl userModel) {
        this.userModel = userModel;
        this.sharedPreferencesHelper = sharedPreferencesHelper;
        observeLiveData();
    }

    private void observeLiveData() {
        userModel.getDeleteSessionResponse().observeForever(deleteSessionResponse -> {
            //false doesn't be (only fail in response, if something wrong)
            if(deleteSessionResponse != null) {
                if (deleteSessionResponse.isSuccess()) {
                    isSuccessDeleteSession.postValue(true);
                    sharedPreferencesHelper.deleteDataByKey(SharedPreferencesHelper.KEY_SESSION_ID);
                }
            }
        });
    }

    public void getUserData() {
        String sessionId = sharedPreferencesHelper.getString(SharedPreferencesHelper.KEY_SESSION_ID, null);
        if (sessionId != null && userModel.getUser().getValue() == null) {
            userModel.getUserData(sessionId);
        } else {
            //ошибка хранения session_id
        }
    }

    public void deleteSession() {
        String sessionId = sharedPreferencesHelper.getString(SharedPreferencesHelper.KEY_SESSION_ID, null);
        if (sessionId != null) {
            userModel.deleteSession(sessionId);
        } else {
            //ошибка хранения session_id
        }
    }

    public LiveData<Boolean> getIsSuccessDeleteSession() {
        return isSuccessDeleteSession;
    }

    public LiveData<UserWrap> getUser() {
        return userModel.getUser();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        userModel.clearDisposable();
    }
}
