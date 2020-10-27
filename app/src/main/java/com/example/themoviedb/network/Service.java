package com.example.themoviedb.network;

import com.example.themoviedb.data.Movie;
import com.example.themoviedb.data.PagingEnvelope;
import com.example.themoviedb.data.RequestTokenAnswerWrap;
import com.example.themoviedb.data.RequestTokenWrap;
import com.example.themoviedb.data.SessionIdWrap;
import com.example.themoviedb.data.UserDataWrap;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Service {

    @GET("/authentication/token/new")
    Single<RequestTokenAnswerWrap> createRequestToken(@Query("api_key") String apiKey);

    @POST("/authentication/token/validate_with_login")
    Single<RequestTokenAnswerWrap> validateRequestToken(@Query("api_key") String apiKey,
                                                        @Body UserDataWrap userData);

    @POST("/authentication/session/new")
    Single<SessionIdWrap> createSession(@Query("api_key") String apiKey,
                                        @Body RequestTokenWrap requestToken);

    @GET("/search/movie")
    Single<PagingEnvelope<Movie>> getMovies(@Query("api_key") String apiKey,
                                            @Query("language") String language,
                                            @Query("query") String query,
                                            @Query("page") int page);

    @GET("/account/{account_id}/favorite/movies")
    Single<PagingEnvelope<Movie>> getFavouriteMovies(@Path("account_id") int accountId,
                                                     @Query("api_key") String apiKey,
                                                     @Query("session_id") String sessionID,
                                                     @Query("language") String language,
                                                     @Query("page") int page);

}
