package com.rapandroid.kamov5.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.Nullable;

import com.rapandroid.kamov5.database.DatabaseContract;
import com.rapandroid.kamov5.database.MoviesHelper;

import java.util.Objects;

import static com.rapandroid.kamov5.database.DatabaseContract.AUTHORITY;
import static com.rapandroid.kamov5.database.DatabaseContract.CONTENT_URI;

public class FavoriteKaMovProvider extends ContentProvider {
    private static final int ALL = 301;
    private static final int ALL_ID = 302;
    private MoviesHelper moviesHelper;
    private static final UriMatcher uriMatcher =new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(AUTHORITY,
                DatabaseContract.TABLE_ALL, ALL);
        uriMatcher.addURI(AUTHORITY,
                DatabaseContract.TABLE_ALL+ "/#",
                ALL_ID);
    }


    @Override
    public int delete(@Nullable Uri uri,@Nullable String selection,@Nullable String[] selectionArgs) {
        int movieDelete;
        Log.v("kaMovPov", ""+uri);
        int match = uriMatcher.match(uri);
        Log.v("kaMovPov", ""+match);
        switch (match) {
            case ALL_ID:
                movieDelete =  moviesHelper.deleteProvider(uri.getLastPathSegment());
                Log.v("kaMovPov", ""+movieDelete);
                break;
            default:
                movieDelete = 0;
                break;
        }
        if (movieDelete > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return movieDelete;
    }

    @Nullable
    @Override
    public String getType(@Nullable Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@Nullable Uri uri,@Nullable ContentValues values) {
        long add;
        switch (uriMatcher.match(uri)){
            case ALL:
                add = moviesHelper.insertProvider(values);
                break;
            default:
                add = 0;
                break;
        }

        if (add > 0) {
            Objects.requireNonNull(getContext()).getContentResolver().notifyChange(uri, null);
        }
        return Uri.parse(CONTENT_URI + "/" + add);
    }

    @Override
    public boolean onCreate() {
        moviesHelper = new MoviesHelper(getContext());
        moviesHelper.open();
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@Nullable Uri uri,@Nullable String[] projection,@Nullable String selection,@Nullable
                        String[] selectionArgs,@Nullable String sortOrder) {
        Cursor cursor;
        int a = uriMatcher.match(uri);
        Log.v("kaMovPov", ""+a);
        Log.v("kaMovPov", ""+uri);
        Log.v("kaMovPov", ""+uri.getLastPathSegment());
        switch(a){
            case ALL:
                cursor = moviesHelper.queryProvider();
                break;
            case ALL_ID:
                cursor = moviesHelper.queryProviderById(uri.getLastPathSegment());
                break;
            default:
                cursor = null;
                break;
        }

        if (cursor != null){
            cursor.setNotificationUri(getContext().getContentResolver(),uri);
        }

        return cursor;
    }

    @Override
    public int update(@Nullable Uri uri,@Nullable ContentValues values,@Nullable String selection,@Nullable
                      String[] selectionArgs) {
        int movieUpdate ;
        switch (uriMatcher.match(uri)) {
            case ALL_ID:
                movieUpdate =  moviesHelper.updateProvider(uri.getLastPathSegment(),values);
                break;
            default:
                movieUpdate = 0;
                break;
        }
        if (movieUpdate > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return movieUpdate;
    }
}
