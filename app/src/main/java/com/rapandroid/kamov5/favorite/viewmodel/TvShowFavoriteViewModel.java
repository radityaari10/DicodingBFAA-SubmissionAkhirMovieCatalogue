package com.rapandroid.kamov5.favorite.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rapandroid.kamov5.model.TvShow;

import java.util.ArrayList;

public class TvShowFavoriteViewModel extends ViewModel {
    private MutableLiveData<ArrayList<TvShow>> listTvShow = new MutableLiveData<>();

    public void setTvShow(ArrayList<TvShow> tvShows) {
        listTvShow.postValue(tvShows);
    }

    public LiveData<ArrayList<TvShow>> getTvShow() {
        return listTvShow;
    }
}
