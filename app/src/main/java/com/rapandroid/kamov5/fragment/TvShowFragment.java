package com.rapandroid.kamov5.fragment;


import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rapandroid.kamov5.R;
import com.rapandroid.kamov5.activity.ProfileActivity;
import com.rapandroid.kamov5.activity.SettingActivity;
import com.rapandroid.kamov5.adapter.TvShowAdapter;
import com.rapandroid.kamov5.model.TvShow;
import com.rapandroid.kamov5.viewmodel.TvShowViewModel;

import java.util.ArrayList;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class TvShowFragment extends Fragment {
    private ProgressBar progressBar;
    private TvShowAdapter adapter;
    private TvShowViewModel tvShowViewModel;
    private RecyclerView rvTvShow;
    private LinearLayoutManager linearLayoutManager;

    public TvShowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_tv_show, container, false);

        rvTvShow = root.findViewById(R.id.rv_tvshow);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        adapter = new TvShowAdapter(this.getContext());
        adapter.notifyDataSetChanged();
        rvTvShow.setLayoutManager(linearLayoutManager);
        rvTvShow.setAdapter(adapter);
        rvTvShow.setHasFixedSize(true);

        progressBar = root.findViewById(R.id.progressbarTvshow);

        tvShowViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(TvShowViewModel.class);
        tvShowViewModel.getTvShow().observe(getViewLifecycleOwner(), getTvShow);
        tvShowViewModel.setTvShow();

        showLoading(true);
        setHasOptionsMenu(true);

        return root;

    }

    private void  showLoading(Boolean state){
        if(state){
            progressBar.setVisibility(View.VISIBLE);
        }
        else {
            progressBar.setVisibility(View.GONE);
        }
    }

    private Observer<ArrayList<TvShow>> getTvShow = new Observer<ArrayList<TvShow>>() {

        @Override
        public void onChanged(ArrayList<TvShow> tvShows) {
            if (tvShows != null) {
                adapter.setListTvshow(tvShows);
                linearLayoutManager = new LinearLayoutManager(getActivity());
                rvTvShow.setLayoutManager(linearLayoutManager);
                rvTvShow.setAdapter(adapter);
            }
            showLoading(false);
        }
    };

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.options_menu, menu);

        SearchManager searchManager = (SearchManager) Objects.requireNonNull(getActivity()).getSystemService(Context.SEARCH_SERVICE);

        if (searchManager != null) {
            SearchView searchView = (SearchView) (menu.findItem(R.id.option_search)).getActionView();
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
            searchView.setQueryHint(getResources().getString(R.string.search_hint));
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    tvShowViewModel.getSearchTvShow().observe(Objects.requireNonNull(getActivity()), getSearchTvShow);
                    tvShowViewModel.setSearchTvShow(query);
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }
            });
        }

        MenuItem menuItem = menu.findItem(R.id.option_search);
        menuItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                tvShowViewModel.setTvShow();
                return true;
            }
        });
    }

    private Observer<ArrayList<TvShow>> getSearchTvShow = new Observer<ArrayList<TvShow>>() {

        @Override
        public void onChanged(ArrayList<TvShow> tvShows) {
            if (tvShows != null) {
                adapter.setListTvshow(tvShows);
                linearLayoutManager = new LinearLayoutManager(getActivity());
                rvTvShow.setLayoutManager(linearLayoutManager);
                rvTvShow.setAdapter(adapter);
                if (tvShows.size() == 0)
                    Toast.makeText(getContext(), "No Result", Toast.LENGTH_SHORT).show();
            }
            showLoading(false);
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem mt){
        switch (mt.getItemId()){
            case R.id.option_profile:
                Intent intent = new Intent(getContext(), ProfileActivity.class);
                startActivity(intent);
                break;
            case R.id.option_setingg:
                Intent mIntent = new Intent(getContext(), SettingActivity.class);
                startActivity(mIntent);
                break;
        }
        return true;
    }

}
