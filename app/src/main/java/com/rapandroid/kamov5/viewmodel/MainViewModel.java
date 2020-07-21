package com.rapandroid.kamov5.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.rapandroid.kamov5.BuildConfig;
import com.rapandroid.kamov5.database.MoviesHelper;
import com.rapandroid.kamov5.database.TvShowHelper;
import com.rapandroid.kamov5.model.Movie;
import com.rapandroid.kamov5.model.TvShow;
import com.rapandroid.kamov5.rest.ApiClient;
import com.rapandroid.kamov5.rest.ApiEndPoint;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainViewModel extends AndroidViewModel {
    private ApiEndPoint mApiInterface;
    private final MoviesHelper mMovieHelper;
    private TvShowHelper mTvShowHelper;
    private MutableLiveData<ArrayList<Movie>> listMovies = new MutableLiveData<>();
    private final MutableLiveData<ArrayList<Movie>> listNowPlayingMovies = new MutableLiveData<>();
    private final MutableLiveData<ArrayList<TvShow>> listTvShow = new MutableLiveData<>();
    private final MutableLiveData<ArrayList<TvShow>> listNowPlayingTv = new MutableLiveData<>();
    private final MutableLiveData<ArrayList<Movie>> listFavoriteMovie = new MutableLiveData<>();
    private final MutableLiveData<ArrayList<TvShow>> listFavoriteTvShow = new MutableLiveData<>();

    public void setMovie() {
        mApiInterface = ApiClient.getClient().create(ApiEndPoint.class);
        try {
            Call<String> authorized = mApiInterface.getDiscoverMovie(BuildConfig.TMDB_API_KEY, "en-US");
            final ArrayList<Movie> listItems = new ArrayList<>();
            authorized.enqueue(new Callback<String>() {
                @Override
                public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                    if (response.isSuccessful()) {

                        try {
                            JSONObject responseObject = new JSONObject(response.body());
                            JSONArray list = responseObject.getJSONArray("results");

                            for (int i = 0; i < list.length(); i++) {
                                JSONObject movie = list.getJSONObject(i);
                                Movie movies = new Movie(movie);
                                listItems.add(movies);
                            }
                            listMovies.postValue(listItems);
                        } catch (Exception e) {
                            Log.d("Exception", e.getMessage());

                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<String> call, @NonNull Throwable throwable) {

                }
            });
        } catch (Exception e) {
            System.out.println("error" + e);
        }

    }

    public void setSearchMovies(String query) {
        mApiInterface = ApiClient.getClient().create(ApiEndPoint.class);
        try {
            Call<String> authorized = mApiInterface.getSearchMovie(BuildConfig.TMDB_API_KEY, "en-US", query);
            final ArrayList<Movie> listItems = new ArrayList<>();
            authorized.enqueue(new Callback<String>() {
                @Override
                public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                    if (response.isSuccessful()) {

                        try {
                            JSONObject responseObject = new JSONObject(response.body());
                            JSONArray list = responseObject.getJSONArray("results");

                            for (int i = 0; i < list.length(); i++) {
                                JSONObject movie = list.getJSONObject(i);
                                Movie movies = new Movie(movie);
                                listItems.add(movies);
                            }
                            listMovies.postValue(listItems);
                        } catch (Exception e) {
                            Log.d("Exception", e.getMessage());

                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<String> call, @NonNull Throwable throwable) {
                    Log.d("onFailure", throwable.getMessage());
                }
            });
        } catch (Exception e) {
            System.out.println("error" + e);
        }

    }

    public void setTvShow() {
        mApiInterface = ApiClient.getClient().create(ApiEndPoint.class);
        try {
            Call<String> authorized = mApiInterface.getDiscoverTvShow(BuildConfig.TMDB_API_KEY, "en-US");
            final ArrayList<TvShow> listItems = new ArrayList<>();
            authorized.enqueue(new Callback<String>() {
                @Override
                public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                    if (response.isSuccessful()) {
                        try {
                            JSONObject responseObject = new JSONObject(response.body());
                            JSONArray list = responseObject.getJSONArray("results");

                            for (int i = 0; i < list.length(); i++) {
                                JSONObject tv = list.getJSONObject(i);
                                TvShow tvShow = new TvShow(tv);
                                listItems.add(tvShow);
                            }
                            listTvShow.postValue(listItems);
                        } catch (Exception e) {
                            Log.d("Exception", e.getMessage());

                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<String> call, @NonNull Throwable throwable) {

                }
            });
        } catch (Exception e) {
            System.out.println("error" + e);
        }

    }

    public void setSearchTvShow(String query) {
        mApiInterface = ApiClient.getClient().create(ApiEndPoint.class);
        try {
            Call<String> authorized = mApiInterface.getSearchTvShow(BuildConfig.TMDB_API_KEY, "en-US", query);
            final ArrayList<TvShow> listItems = new ArrayList<>();
            authorized.enqueue(new Callback<String>() {
                @Override
                public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                    if (response.isSuccessful()) {
                        try {
                            JSONObject responseObject = new JSONObject(response.body());
                            JSONArray list = responseObject.getJSONArray("results");

                            for (int i = 0; i < list.length(); i++) {
                                JSONObject tv = list.getJSONObject(i);
                                TvShow tvShow = new TvShow(tv);
                                listItems.add(tvShow);
                            }
                            listTvShow.postValue(listItems);
                        } catch (Exception e) {
                            Log.d("Exception", e.getMessage());

                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<String> call, @NonNull Throwable throwable) {

                }
            });
        } catch (Exception e) {
            System.out.println("error" + e);
        }

    }

    public MainViewModel(@NonNull Application application) {
        super(application);
        this.mMovieHelper = MoviesHelper.getInstance(application);
        this.mTvShowHelper = TvShowHelper.getInstance(application);
    }

    public void setMovieDatabase(){
        ArrayList<Movie> movie = mMovieHelper.getAllMovies();
        listFavoriteMovie.postValue(movie);
    }

    public void setTvShowDatabase(){
        ArrayList<TvShow> television = mTvShowHelper.getAllTvShow();
        listFavoriteTvShow.postValue(television);
    }

    public LiveData<ArrayList<Movie>> getMovieFavorite(String type){
        setMovieDatabase();
        return listFavoriteMovie;
    }

    public LiveData<ArrayList<TvShow>> getTvShowFavorite(String type){
        setTvShowDatabase();
        return listFavoriteTvShow;
    }

    public LiveData<ArrayList<Movie>> getMovies() {
        return listMovies;
    }

    public LiveData<ArrayList<Movie>> getNowPlayingMovie() {
        return listNowPlayingMovies;
    }

    public LiveData<ArrayList<TvShow>> getTvShow() {
        return listTvShow;
    }

    public LiveData<ArrayList<TvShow>> getNowPlayingTvShow() {
        return listNowPlayingTv;
    }

    public LiveData<ArrayList<TvShow>> getSearchTvShow() {
        return listTvShow;
    }
}
