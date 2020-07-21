package com.rapandroid.kamov5.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class FavoriteMovieService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new FavoriteMovieViewsFactory(this.getApplicationContext());
    }
}
