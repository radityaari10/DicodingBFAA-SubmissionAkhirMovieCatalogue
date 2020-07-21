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
import com.rapandroid.kamov5.database.TvShowHelper;
import com.rapandroid.kamov5.favorite.adapter.TvShowFavoriteAdapter;
import com.rapandroid.kamov5.favorite.viewmodel.TvShowFavoriteViewModel;
import com.rapandroid.kamov5.model.TvShow;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TvShowFavoriteFragment extends Fragment {
    private ArrayList<TvShow> tvShows = new ArrayList<>();

    private RecyclerView rvFavorite;
    private ProgressBar progressBar;

    private TvShowHelper tvShowHelper;
    private TvShowFavoriteAdapter adapterTv;

    public TvShowFavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tv_show_favorite, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle sacedInstanceState){
        progressBar = view.findViewById(R.id.progressbarFavoriteTv);
        rvFavorite = view.findViewById(R.id.rv_tvshow_favorite);
        tvShowHelper = TvShowHelper.getInstance(view.getContext());
        tvShowHelper.open();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvFavorite.setLayoutManager(linearLayoutManager);
        adapterTv = new TvShowFavoriteAdapter(view.getContext());
        rvFavorite.setHasFixedSize(true);
        rvFavorite.setAdapter(adapterTv);


        ArrayList<TvShow> data = (ArrayList<TvShow>) loadFavTvShow();

        TvShowFavoriteViewModel movieFavoriteViewModel = ViewModelProviders.of(this).get(TvShowFavoriteViewModel.class);
        movieFavoriteViewModel.setTvShow(data);
        movieFavoriteViewModel.getTvShow().observe(getViewLifecycleOwner(), getTvShow);
        showLoading(true);
    }

    private List<TvShow> loadFavTvShow() {
        tvShows = tvShowHelper.getAllTvShow();
        return tvShows;
    }

    public void onResume() {
        super.onResume();
        tvShows = tvShowHelper.getAllTvShow();
        adapterTv.setData(tvShows);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        tvShowHelper.close();
    }

    private Observer<ArrayList<TvShow>> getTvShow = new Observer<ArrayList<TvShow>>() {

        @Override
        public void onChanged(ArrayList<TvShow> tvShows) {
            if (tvShows != null) {
                adapterTv.setData(tvShows);
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
