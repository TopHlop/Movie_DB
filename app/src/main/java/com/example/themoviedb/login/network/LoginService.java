package com.example.themoviedb.login.network;

import com.example.themoviedb.login.data.RequestTokenAnswerWrap;
import com.example.themoviedb.login.data.RequestTokenWrap;
import com.example.themoviedb.login.data.SessionIdWrap;
import com.example.themoviedb.login.data.UserDataWrap;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface LoginService {

    @GET("authentication/token/new")
    Single<RequestTokenAnswerWrap> createRequestToken(@Query("api_key") String apiKey);

    @POST("authentication/token/validate_with_login")
    Single<RequestTokenAnswerWrap> validateRequestToken(@Query("api_key") String apiKey,
                                                        @Body UserDataWrap userData);

    @POST("authentication/session/new")
    Single<SessionIdWrap> createSession(@Query("api_key") String apiKey,
                                        @Body RequestTokenWrap requestToken);
}
