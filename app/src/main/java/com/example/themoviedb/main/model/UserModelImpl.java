package com.example.themoviedb.main.model;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.themoviedb.main.data.DeleteSessionResponseWrap;
import com.example.themoviedb.main.data.SessionIdBodyWrap;
import com.example.themoviedb.main.data.UserWrap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class UserModelImpl extends BaseMainModel implements UserModel {

    private static UserModelImpl userModel;

    public static UserModelImpl getInstance() {
        if (userModel == null) {
            userModel = new UserModelImpl();
        }
        return userModel;
    }

    public UserModelImpl() {
        super();
    }

    private MutableLiveData<DeleteSessionResponseWrap> deleteSessionResponse = new MutableLiveData<>();
    private MutableLiveData<UserWrap> user = new MutableLiveData<>();

    @Override
    public LiveData<DeleteSessionResponseWrap> getDeleteSessionResponse() {
        return deleteSessionResponse;
    }

    @Override
    public LiveData<UserWrap> getUser() {
        return user;
    }

    @Override
    public void deleteSession(String sessionId) {
        disposable.add(apiMainService.deleteSession(apiKey, new SessionIdBodyWrap(sessionId))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<DeleteSessionResponseWrap>() {
                    @Override
                    public void onSuccess(DeleteSessionResponseWrap deleteSessionResponseWrap) {
                        deleteSessionResponse.postValue(deleteSessionResponseWrap);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(this.getClass().getName(), "error delete session: " + e.getMessage());
                    }
                }));
    }

    @Override
    public void getUserData(String sessionId) {
        disposable.add(apiMainService.getAccountDetails(apiKey, sessionId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<UserWrap>() {
                    @Override
                    public void onSuccess(UserWrap userWrap) {
                        user.postValue(userWrap);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(this.getClass().getName(), "error get user data: " + e.getMessage());
                    }
                }));
    }

    @Override
    public void clearDisposable() {
        super.clearDisposable();
    }
}
