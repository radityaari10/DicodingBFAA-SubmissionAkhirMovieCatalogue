package com.rapandroid.kamov5.favorite;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.rapandroid.kamov5.R;
import com.rapandroid.kamov5.database.MoviesHelper;
import com.rapandroid.kamov5.favorite.adapter.MoviesFavoriteAdapter;
import com.rapandroid.kamov5.favorite.viewmodel.MoviesFavoriteViewModel;
import com.rapandroid.kamov5.model.Movie;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MoviesFavoriteFragment extends Fragment {
    private ArrayList<Movie> movies = new ArrayList<>();
    private RecyclerView rvFavorite;
    private MoviesHelper moviesHelper;
    private MoviesFavoriteAdapter adapter;
    private ProgressBar progressBar;

    public MoviesFavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movies_favorite, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle sacedInstanceState){
        progressBar = view.findViewById(R.id.progressbarFavorite);
        rvFavorite = view.findViewById(R.id.rv_movie_favorite);
        moviesHelper = MoviesHelper.getInstance(view.getContext());
        moviesHelper.open();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvFavorite.setLayoutManager(linearLayoutManager);
        adapter = new MoviesFavoriteAdapter(view.getContext());
        rvFavorite.setHasFixedSize(true);
        rvFavorite.setAdapter(adapter);

        ArrayList<Movie> data = (ArrayList<Movie>) loadFavMovies();

        MoviesFavoriteViewModel movieFavoriteViewModel = ViewModelProviders.of(this).get(MoviesFavoriteViewModel.class);
        movieFavoriteViewModel.setMovie(data);
        movieFavoriteViewModel.getMovie().observe(getViewLifecycleOwner(), getMovie);
        showLoading(true);
    }

    private List<Movie> loadFavMovies() {
        movies = moviesHelper.getAllMovies();
        return movies;
    }

    @Override
    public void onResume() {
        super.onResume();
        movies = moviesHelper.getAllMovies();
        adapter.setData(movies);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        moviesHelper.close();
    }

    private Observer<ArrayList<Movie>> getMovie = new Observer<ArrayList<Movie>>() {

        @Override
        public void onChanged(ArrayList<Movie> movies) {
            if (movies != null) {
                adapter.setData(movies);
            }
            showLoading(false);
        }
    };

    private void  showLoading(Boolean state){
        if(state){
            progressBar.setVisibility(View.VISIBLE);
        }
        else {
            progressBar.setVisibility(View.GONE);
        }
    }

}
