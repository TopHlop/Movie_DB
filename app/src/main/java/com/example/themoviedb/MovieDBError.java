package com.example.themoviedb;

import android.content.Context;

public class MovieDBError {
    //login
    public static final int INVALID_API_KEY = 7;
    public static final int RESOURCE_NOT_FOUND = 34;
    public static final int INVALID_REQUEST_TOKEN = 33;
    public static final int EMAIL_NOT_VERIFIED = 32;
    public static final int NOT_USERNAME_OR_PASSWORD = 26;
    public static final int INVALID_USERNAME_OR_PASSWORD = 30;
    public static final int SESSION_DENIED = 17;
    //delete session
    public static final int INVALID_SESSION_ID = 6;

    public static String getErrorMessage(int errorCode, Context context) {
        switch (errorCode) {
            case NOT_USERNAME_OR_PASSWORD:
            case INVALID_USERNAME_OR_PASSWORD:
            case EMAIL_NOT_VERIFIED:
                return context.getString(R.string.error_login_or_password);
            default:
                return context.getString(R.string.error_login);
        }
    }


}
