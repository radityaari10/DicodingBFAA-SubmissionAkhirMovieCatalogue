package com.rapandroid.kamov5.viewmodel;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.rapandroid.kamov5.BuildConfig;
import com.rapandroid.kamov5.database.MoviesHelper;
import com.rapandroid.kamov5.model.Movie;
import com.rapandroid.kamov5.rest.ApiClient;
import com.rapandroid.kamov5.rest.ApiEndPoint;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviesViewModel extends AndroidViewModel {
    String API_KEY = BuildConfig.TMDB_API_KEY;
    private MoviesHelper mMovieHelper;
    private final MutableLiveData<ArrayList<Movie>> listMovie = new MutableLiveData<>();
    private ApiEndPoint mApi;

    public MoviesViewModel(@NonNull Application application) {
        super(application);
        this.mMovieHelper = MoviesHelper.getInstance(application);
    }

//    public LiveData<ArrayList<Movie>> getMovie() {
//        return listMovie;
//    }

//    public void setMovies(final String movies) {
//        AsyncHttpClient client = new AsyncHttpClient();
//        final ArrayList<Movie> listItems = new ArrayList<>();
//        String url = "https://api.themoviedb.org/3/discover/movie?api_key=" + API_KEY + "&language=en-US";
//
//        client.get(url, new AsyncHttpResponseHandler() {
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
//                try {
//                    String result = new String(responseBody);
//                    JSONObject responseObject = new JSONObject(result);
//                    JSONArray list = responseObject.getJSONArray("results");
//                    for (int i = 0; i < list.length(); i++) {
//                        JSONObject moviejs = list.getJSONObject(i);
//                        Movie movieItems = new Movie(moviejs);
//                        listItems.add(movieItems);
//                    }
//                    listMovie.postValue(listItems);
//                } catch (Exception e) {
//                    Log.d("Exception", e.getMessage());
//                }
//            }
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
//                Log.d("onFailure", error.getMessage());
//            }
//        });
//    }

    public void setMovies() {
        mApi = ApiClient.getClient().create(ApiEndPoint.class);
        try {
            Call<String> authorized = mApi.getDiscoverMovie(API_KEY, "en-US");
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
                            listMovie.postValue(listItems);
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


    public void setSearchMovies(String query) {
        mApi = ApiClient.getClient().create(ApiEndPoint.class);
        try {
            Call<String> authorized = mApi.getSearchMovie(API_KEY, "en-US", query);
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
                            listMovie.postValue(listItems);
                        } catch (Exception e) {
                            Log.d("Exception", e.getMessage());
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<String> call, @NonNull Throwable throwable) {
                    Toast.makeText(getApplication(), "Something went wrong" + throwable, Toast.LENGTH_SHORT).show();
                    Log.d("onFailure", throwable.getMessage());
                }
            });
        } catch (Exception e) {
            System.out.println("error" + e);
        }

    }


    public LiveData<ArrayList<Movie>> getSearchMovies() {
        return listMovie;
    }

    public LiveData<ArrayList<Movie>> getMovies() {
        return listMovie;
    }

}
