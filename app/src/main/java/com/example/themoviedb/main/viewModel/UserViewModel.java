package com.example.themoviedb.main.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.themoviedb.App;
import com.example.themoviedb.SharedPreferencesHelper;
import com.example.themoviedb.main.model.UserModel;
import com.example.themoviedb.main.model.UserModelImpl;

import javax.inject.Inject;

public class UserViewModel extends ViewModel {

    @Inject
    SharedPreferencesHelper sharedPreferencesHelper;

    private UserModel userModel;
    private MutableLiveData<Boolean> isSuccessDeleteSession = new MutableLiveData<>();

    @Inject
    public UserViewModel() {
        userModel = UserModelImpl.getInstance();
        App.getAppComponent().inject(this);
        observeLiveData();
    }

    private void observeLiveData() {
        userModel.getDeleteSessionResponse().observeForever(deleteSessionResponse -> {
            if (deleteSessionResponse.isSuccess()) {
                isSuccessDeleteSession.postValue(true);
                sharedPreferencesHelper.deleteDataByKey(SharedPreferencesHelper.KEY_SESSION_ID);
            } else {

            }
        });
    }

    public void getUserData() {

    }

    public void deleteSession() {
        String sessionId = sharedPreferencesHelper.getString(SharedPreferencesHelper.KEY_SESSION_ID, null);
        if(sessionId != null) {
            userModel.deleteSession(sessionId);
        } else {
            //ошибка хранения session_id
        }
    }

    public LiveData<Boolean> getIsSuccessDeleteSession() {
        return isSuccessDeleteSession;
    }
}
