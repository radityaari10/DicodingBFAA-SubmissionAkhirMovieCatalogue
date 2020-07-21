package com.rapandroid.kamov5.rest;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiEndPoint {
    @GET("discover/movie")
    Call<String> getReleaseMovie(@Query("api_key") String API_KEY,
                                 @Query("primary_release_date.gte") String ReleaseDate,
                                 @Query("primary_release_date.lte") String TodayDate);

    @GET("discover/movie")
    Call<String> getDiscoverMovie(@Query("api_key") String API_KEY,
                                  @Query("language") String language);

//    @GET("movie/now_playing")
//    Call<String> getNowPlayingMovie(@Query("api_key") String API_KEY,
//                                    @Query("language") String language);

    @GET("search/movie")
    Call<String> getSearchMovie(@Query("api_key") String API_KEY,
                                @Query("language") String language,
                                @Query("query") String keyword);

    @GET("discover/tv")
    Call<String> getDiscoverTvShow(@Query("api_key") String API_KEY,
                               @Query("language") String language);

//    @GET("tv/on_the_air")
//    Call<String> getNowPlayingTvShow(@Query("api_key") String API_KEY,
//                                 @Query("language") String language);

    @GET("search/tv")
    Call<String> getSearchTvShow(@Query("api_key") String API_KEY,
                                     @Query("language") String language,
                                     @Query("query") String keyword);

}
