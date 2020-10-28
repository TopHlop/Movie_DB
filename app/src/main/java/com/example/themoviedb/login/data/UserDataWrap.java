package com.example.themoviedb.login.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.reactivex.Single;

public class UserDataWrap {
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("request_token")
    @Expose
    private String requestToken;

    public UserDataWrap(String username, String password, String requestToken) {
        this.username = username;
        this.password = password;
        this.requestToken = requestToken;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRequestToken(String requestToken) {
        this.requestToken = requestToken;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRequestToken() {
        return requestToken;
    }
}
