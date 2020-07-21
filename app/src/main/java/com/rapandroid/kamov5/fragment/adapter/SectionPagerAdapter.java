package com.rapandroid.kamov5.fragment.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.rapandroid.kamov5.R;
import com.rapandroid.kamov5.favorite.FavoriteFragment;
import com.rapandroid.kamov5.fragment.MoviesFragment;
import com.rapandroid.kamov5.fragment.TvShowFragment;

public class SectionPagerAdapter extends FragmentPagerAdapter {
    private Context mContext;

    public SectionPagerAdapter(Context context, @NonNull FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mContext = context;
    }

    @StringRes
    private final int[] TAB_TITLES = new int[]{
            R.string.tab_text1, R.string.tab_text2, R.string.tab_text3
    };

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position){
            case 0:
                fragment = new MoviesFragment();
                break;
            case 1:
                fragment = new TvShowFragment();
                break;
            case 2:
                fragment = new FavoriteFragment();
                break;
        }
        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int p){
        return  mContext.getResources().getString(TAB_TITLES[p]);
    }

    @Override
    public int getCount() {
        return 3;
    }

}
