package com.rapandroid.kamov5.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static String DATABASE_NAME = "dbKaMov.db";
    private static final int DATABASE_VERSION = 8;

    private static final String SQL_TBMOVIE = String.format("CREATE TABLE %s" +
                    " (%s TEXT NULL," +
                    " %s TEXT NULL," +
                    " %s TEXT NULL," +
                    " %s TEXT NULL," +
                    " %s TEXT NULL," +
                    " %s TEXT NULL," +
                    " %s TEXT NULL)",
            DatabaseContract.TABLE_MOVIE,
            DatabaseContract.MovieColumns.ID,
            DatabaseContract.MovieColumns.POSTER,
            DatabaseContract.MovieColumns.TITLE,
            DatabaseContract.MovieColumns.RELEASE_DATE,
            DatabaseContract.MovieColumns.ORIGINAL_LANGUAGE,
            DatabaseContract.MovieColumns.VOTE_AVERAGE,
            DatabaseContract.MovieColumns.OVERVIEW
    );

    private static final String SQL_TBTVSHOW = String.format("CREATE TABLE %s" +
                    " (%s TEXT NULL," +
                    " %s TEXT NULL," +
                    " %s TEXT NULL," +
                    " %s TEXT NULL," +
                    " %s TEXT NULL," +
                    " %s TEXT NULL," +
                    " %s TEXT NULL)",
            DatabaseContract.TABLE_TVSHOW,
            DatabaseContract.TvShowColumns.ID,
            DatabaseContract.TvShowColumns.POSTER,
            DatabaseContract.TvShowColumns.TITLE,
            DatabaseContract.TvShowColumns.FIRST_AIR_DATE,
            DatabaseContract.TvShowColumns.ORIGINAL_LANGUAGE,
            DatabaseContract.TvShowColumns.VOTE_AVERAGE,
            DatabaseContract.TvShowColumns.OVERVIEW
    );

    private static final String SQL_ALL = String.format("CREATE TABLE %s" +
                    " (%s TEXT NULL," +
                    " %s TEXT NULL," +
                    " %s TEXT NULL," +
                    " %s TEXT NULL," +
                    " %s TEXT NULL," +
                    " %s TEXT NULL," +
                    " %s TEXT NULL)",
            DatabaseContract.TABLE_ALL,
            DatabaseContract.AllColumns.ID,
            DatabaseContract.AllColumns.POSTER,
            DatabaseContract.AllColumns.TITLE,
            DatabaseContract.AllColumns.DATE,
            DatabaseContract.AllColumns.ORIGINAL_LANGUAGE,
            DatabaseContract.AllColumns.VOTE_AVERAGE,
            DatabaseContract.AllColumns.OVERVIEW
    );


    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_TBMOVIE);
        db.execSQL(SQL_TBTVSHOW);
        db.execSQL(SQL_ALL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.TABLE_MOVIE);
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.TABLE_TVSHOW);
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.TABLE_ALL);

        onCreate(db);
    }
}
