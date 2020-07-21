package com.rapandroid.kamov5.viewmodel;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.rapandroid.kamov5.BuildConfig;
import com.rapandroid.kamov5.database.TvShowHelper;
import com.rapandroid.kamov5.model.TvShow;
import com.rapandroid.kamov5.rest.ApiClient;
import com.rapandroid.kamov5.rest.ApiEndPoint;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TvShowViewModel extends AndroidViewModel {
    String API_KEY = BuildConfig.TMDB_API_KEY;
    private MutableLiveData<ArrayList<TvShow>> listTvshow = new MutableLiveData<>();
    private ApiEndPoint mApi;
    private TvShowHelper tvShowHelper;

    public TvShowViewModel(@NonNull Application application) {
        super(application);
        this.tvShowHelper = TvShowHelper.getInstance(application);

    }

    public void setTvShow() {
        mApi = ApiClient.getClient().create(ApiEndPoint.class);
        try {
            Call<String> authorized = mApi.getDiscoverTvShow(BuildConfig.TMDB_API_KEY, "en-US");
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
                            listTvshow.postValue(listItems);
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
        mApi = ApiClient.getClient().create(ApiEndPoint.class);
        try {
            Call<String> authorized = mApi.getSearchTvShow(API_KEY, "en-US", query);
            final ArrayList<TvShow> listItems = new ArrayList<>();
            authorized.enqueue(new Callback<String>() {
                @Override
                public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                    if (response.isSuccessful()) {

                        try {
                            JSONObject responseObject = new JSONObject(response.body());
                            JSONArray list = responseObject.getJSONArray("results");

                            for (int i = 0; i < list.length(); i++) {
                                JSONObject tvShow = list.getJSONObject(i);
                                TvShow tvShow1 = new TvShow(tvShow);
                                listItems.add(tvShow1);
                            }
                            listTvshow.postValue(listItems);
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

    public LiveData<ArrayList<TvShow>> getSearchTvShow() {
        return listTvshow;
    }

    public LiveData<ArrayList<TvShow>> getTvShow() {
        return listTvshow;
    }
}
