package com.example.themoviedb.main.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.themoviedb.SharedPreferencesHelper;
import com.example.themoviedb.main.data.UserWrap;
import com.example.themoviedb.main.model.DataStorage;
import com.example.themoviedb.main.model.UserModelUseCase;
import com.example.themoviedb.main.model.UserModelImpl;

import javax.inject.Inject;

public class UserViewModel extends ViewModel {

    private SharedPreferencesHelper sharedPreferencesHelper;
    private UserModelUseCase userModel;
    private DataStorage storage;

    private MutableLiveData<Boolean> isSuccessDeleteSession = new MutableLiveData<>();

    @Inject
    public UserViewModel(SharedPreferencesHelper sharedPreferencesHelper, UserModelImpl userModel,
                         DataStorage storage) {
        this.storage = storage;
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
                    sharedPreferencesHelper.deleteDataByKey(SharedPreferencesHelper.RECYCLER_VIEW_FORM);
                }
            }
        });
    }

    public void loadUserData() {
        String sessionId = sharedPreferencesHelper.getString(SharedPreferencesHelper.KEY_SESSION_ID, null);
        if (sessionId != null && userModel.getUser().getValue() == null) {
            userModel.getUserData(sessionId);
        } else {
            //ошибка хранения session_id
        }
    }

    public UserWrap getUserData() {
        return storage.getUserData();
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
        storage.setUserData(userModel.getUser().getValue());
        return userModel.getUser();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        userModel.clearDisposable();
    }
}
