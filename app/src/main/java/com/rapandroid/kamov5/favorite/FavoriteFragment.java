package com.rapandroid.kamov5.favorite;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.rapandroid.kamov5.R;
import com.rapandroid.kamov5.activity.ProfileActivity;
import com.rapandroid.kamov5.activity.SettingActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment {
    private Button btnMovie;
    private Button btnTvShow;

//    private ProgressBar progressBar;

    public FavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle sacedInstanceState){
        btnMovie = view.findViewById(R.id.btn_movie);
        btnTvShow = view.findViewById(R.id.btn_tvshow);

        btnMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMovie();
                setBtnMovie();
            }
        });

        btnTvShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTvShow();
                setBtnTvShow();
            }
        });
        showMovie();
        setBtnMovie();
        setHasOptionsMenu(true);
    }

    private void setBtnMovie(){
        btnMovie.setBackgroundResource(R.drawable.btn_merah);
        btnMovie.setTextColor(getResources().getColor(R.color.textWhite));
        btnMovie.setEnabled(false);
        btnTvShow.setBackgroundResource(R.drawable.btn_white);
        btnTvShow.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        btnTvShow.setEnabled(true);
    }

    private void setBtnTvShow(){
        btnTvShow.setBackgroundResource(R.drawable.btn_merah);
        btnTvShow.setTextColor(getResources().getColor(R.color.textWhite));
        btnTvShow.setEnabled(false);
        btnMovie.setBackgroundResource(R.drawable.btn_white);
        btnMovie.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        btnMovie.setEnabled(true);
    }

    public void showMovie() {
        MoviesFavoriteFragment a = new MoviesFavoriteFragment();
        FragmentManager fragmentManager = getFragmentManager();
        if ((fragmentManager != null)) {
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.frame_content, a, MoviesFavoriteFragment.class.
                            getSimpleName())
                    .addToBackStack(null)
                    .commit();
        }
    }

    public void showTvShow() {
        TvShowFavoriteFragment b = new TvShowFavoriteFragment();
        FragmentManager fragmentManager1 = getFragmentManager();
        if ((fragmentManager1 != null)) {
            fragmentManager1
                    .beginTransaction()
                    .replace(R.id.frame_content, b, TvShowFavoriteFragment.class.
                            getSimpleName())
                    .addToBackStack(null)
                    .commit();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.settingprofile_menu, menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem mt){
        switch (mt.getItemId()){
            case R.id._profile:
                Intent intent = new Intent(getContext(), ProfileActivity.class);
                startActivity(intent);
                break;
            case R.id._setting:
                Intent mIntent = new Intent(getContext(), SettingActivity.class);
                startActivity(mIntent);
                break;
        }
        return true;
    }
}
