package com.example.themoviedb.main.network;

import com.example.themoviedb.main.data.DeleteSessionResponseWrap;
import com.example.themoviedb.main.data.FilmWrap;
import com.example.themoviedb.main.data.GenresListWrap;
import com.example.themoviedb.main.data.MarkFilmAsFavoriteBodyRequestWrap;
import com.example.themoviedb.main.data.MarkFilmAsFavoriteResponseWrap;
import com.example.themoviedb.main.data.PagingEnvelope;
import com.example.themoviedb.main.data.SessionIdBodyWrap;
import com.example.themoviedb.main.data.UserWrap;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MainService {

    @GET("search/movie")
    Single<PagingEnvelope<FilmWrap>> getMovies(@Query("api_key") String apiKey,
                                               @Query("language") String language,
                                               @Query("query") String query,
                                               @Query("include_adult") boolean includeAdult,
                                               @Query("page") int page);

    @GET("account/{account_id}/favorite/movies")
    Single<PagingEnvelope<FilmWrap>> getFavouriteMovies(@Path("account_id") int accountId,
                                                        @Query("api_key") String apiKey,
                                                        @Query("session_id") String sessionID,
                                                        @Query("language") String language,
                                                        @Query("page") int page);

    @POST("account/{account_id}/favorite")
    Single<MarkFilmAsFavoriteResponseWrap> markFilmAsFavorite(@Path("account_id") int accountId,
                                                              @Query("api_key") String apiKey,
                                                              @Query("session_id") String sessionId,
                                                              @Body MarkFilmAsFavoriteBodyRequestWrap body);


    @HTTP(method = "DELETE", path = "authentication/session", hasBody = true)
    Single<DeleteSessionResponseWrap> deleteSession(@Query("api_key") String apiKey,
                                                    @Body SessionIdBodyWrap sessionIdBody);

    @GET("account")
    Single<UserWrap> getAccountDetails(@Query("api_key") String apiKey,
                                       @Query("session_id") String sessionID);

    @GET("genre/movie/list")
    Single<GenresListWrap> getMovieGenreList(@Query("api_key") String api_key,
                                             @Query("language") String language);

}
