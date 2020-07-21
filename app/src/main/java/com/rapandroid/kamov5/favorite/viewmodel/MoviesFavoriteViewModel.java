package com.rapandroid.kamov5.favorite.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rapandroid.kamov5.model.Movie;

import java.util.ArrayList;

public class MoviesFavoriteViewModel extends ViewModel {
    private MutableLiveData<ArrayList<Movie>> listMovie = new MutableLiveData<>();

    public void setMovie(ArrayList<Movie> movies) {
        listMovie.postValue(movies);
    }

    public LiveData<ArrayList<Movie>> getMovie() {
        return listMovie;
    }
}
