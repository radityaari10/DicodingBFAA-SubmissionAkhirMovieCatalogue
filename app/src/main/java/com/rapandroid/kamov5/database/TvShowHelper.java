package com.rapandroid.kamov5.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.rapandroid.kamov5.model.TvShow;

import java.util.ArrayList;

import static com.rapandroid.kamov5.database.DatabaseContract.TABLE_ALL;
import static com.rapandroid.kamov5.database.DatabaseContract.TABLE_TVSHOW;
import static com.rapandroid.kamov5.database.DatabaseContract.TvShowColumns.FIRST_AIR_DATE;
import static com.rapandroid.kamov5.database.DatabaseContract.TvShowColumns.ID;
import static com.rapandroid.kamov5.database.DatabaseContract.TvShowColumns.ORIGINAL_LANGUAGE;
import static com.rapandroid.kamov5.database.DatabaseContract.TvShowColumns.OVERVIEW;
import static com.rapandroid.kamov5.database.DatabaseContract.TvShowColumns.POSTER;
import static com.rapandroid.kamov5.database.DatabaseContract.TvShowColumns.TITLE;
import static com.rapandroid.kamov5.database.DatabaseContract.TvShowColumns.VOTE_AVERAGE;

public class TvShowHelper {
    private static final String DATABASE_TABLE = TABLE_TVSHOW;
    private static DatabaseHelper databaseHelper;
    private static TvShowHelper INSTANCE;

    private static SQLiteDatabase database;

    private TvShowHelper(Context context){
        databaseHelper = new DatabaseHelper(context);
    }

    public static TvShowHelper getInstance(Context context){
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new TvShowHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public void open() throws SQLException {
        database = databaseHelper.getWritableDatabase();
    }

    public void close(){
        databaseHelper.close();

        if (database.isOpen()){
            database.close();
        }
    }

    public ArrayList<TvShow> getAllTvShow() {
        ArrayList<TvShow> arrayList = new ArrayList<>();
        Cursor cursor = database.query(DATABASE_TABLE, null,
                null,
                null,
                null,
                null,
                ID + " DESC",
                null);
        cursor.moveToFirst();
        TvShow tvShow;
        if (cursor.getCount() > 0) {
            do {
                tvShow = new TvShow();
                tvShow.setId(cursor.getInt(cursor.getColumnIndexOrThrow(ID)));
                tvShow.setPhoto(cursor.getString(cursor.getColumnIndexOrThrow(POSTER)));
                tvShow.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                tvShow.setRelease(cursor.getString(cursor.getColumnIndexOrThrow(FIRST_AIR_DATE)));
                tvShow.setLanguage(cursor.getString(cursor.getColumnIndexOrThrow(ORIGINAL_LANGUAGE)));
                tvShow.setVote_avg(cursor.getDouble(cursor.getColumnIndexOrThrow(VOTE_AVERAGE)));
                tvShow.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(OVERVIEW)));

                arrayList.add(tvShow);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insertTvShow(TvShow tvShow) {
        ContentValues args = new ContentValues();
        args.put(ID, tvShow.getId());
        args.put(POSTER, tvShow.getPhoto());
        args.put(TITLE, tvShow.getTitle());
        args.put(FIRST_AIR_DATE, tvShow.getRelease());
        args.put(ORIGINAL_LANGUAGE, tvShow.getLanguage());
        args.put(VOTE_AVERAGE, tvShow.getVote_avg());
        args.put(OVERVIEW, tvShow.getOverview());

        return database.insert(DATABASE_TABLE, null, args);
    }

    public long insertAll(TvShow tvShow) {
        ContentValues args = new ContentValues();
        args.put(DatabaseContract.AllColumns.ID, tvShow.getId());
        args.put(DatabaseContract.AllColumns.POSTER, tvShow.getPhoto());
        args.put(DatabaseContract.AllColumns.TITLE, tvShow.getTitle());
        args.put(DatabaseContract.AllColumns.DATE, tvShow.getRelease());
        args.put(DatabaseContract.AllColumns.ORIGINAL_LANGUAGE, tvShow.getLanguage());
        args.put(DatabaseContract.AllColumns.VOTE_AVERAGE, tvShow.getVote_avg());
        args.put(DatabaseContract.AllColumns.OVERVIEW, tvShow.getOverview());

        return database.insert(TABLE_ALL, null, args);
    }

    public int deleteAll(int id) {
        return database.delete(TABLE_ALL, DatabaseContract.MovieColumns.ID + " = '" + id + "'", null);
    }

    public int deleteTvshow(int id) {
        return database.delete(TABLE_TVSHOW, ID + " = '" + id + "'", null);
    }
}
