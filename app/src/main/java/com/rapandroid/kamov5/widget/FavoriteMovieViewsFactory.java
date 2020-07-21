package com.rapandroid.kamov5.widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Binder;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.rapandroid.kamov5.R;
import com.rapandroid.kamov5.database.MoviesHelper;
import com.rapandroid.kamov5.model.Movie;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class FavoriteMovieViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private final Context mContext;
    private MoviesHelper mMovieHelper;
    private ArrayList<Movie> listMovies = new ArrayList<>();

    public FavoriteMovieViewsFactory(Context context) {
        this.mContext = context;
        mMovieHelper = MoviesHelper.getInstance(mContext);
    }

    @Override
    public void onCreate() {
        final long identityToken = Binder.clearCallingIdentity();
//        mMovieHelper.open();
        Binder.restoreCallingIdentity(identityToken);
    }

    @Override
    public void onDataSetChanged() {
        try{
            listMovies.clear();
            listMovies.addAll(mMovieHelper.getAllData());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        mMovieHelper.close();
    }

    @Override
    public int getCount() {
        return listMovies.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.item_widget_favorite_movie);
        Bitmap bmp;
        try {
            bmp = Glide.with(mContext)
                    .asBitmap()
                    .load("https://image.tmdb.org/t/p/w500/" + listMovies.get(position).getPhoto())
                    .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .get();
            rv.setImageViewBitmap(R.id.ig_widget_favorite_movie, bmp);
            Log.d("Widget", "Success");
        } catch (InterruptedException | ExecutionException e) {
            Log.d("Widget Load Error", "Error");
        }
        Bundle extras = new Bundle();
        extras.putInt(FavoriteWidget.EXTRA_ITEM, position);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);

        rv.setOnClickFillInIntent(R.id.ig_widget_favorite_movie, fillInIntent);
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
