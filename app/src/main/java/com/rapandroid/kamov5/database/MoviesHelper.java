package com.rapandroid.kamov5.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.rapandroid.kamov5.model.Movie;

import java.util.ArrayList;

import static com.rapandroid.kamov5.database.DatabaseContract.MovieColumns.ID;
import static com.rapandroid.kamov5.database.DatabaseContract.MovieColumns.ORIGINAL_LANGUAGE;
import static com.rapandroid.kamov5.database.DatabaseContract.MovieColumns.OVERVIEW;
import static com.rapandroid.kamov5.database.DatabaseContract.MovieColumns.POSTER;
import static com.rapandroid.kamov5.database.DatabaseContract.MovieColumns.RELEASE_DATE;
import static com.rapandroid.kamov5.database.DatabaseContract.MovieColumns.TITLE;
import static com.rapandroid.kamov5.database.DatabaseContract.MovieColumns.VOTE_AVERAGE;
import static com.rapandroid.kamov5.database.DatabaseContract.TABLE_ALL;
import static com.rapandroid.kamov5.database.DatabaseContract.TABLE_MOVIE;

public class MoviesHelper {
    private static final String DATABASE_TABLE = TABLE_MOVIE;
    private static DatabaseHelper databaseHelper;
    private static MoviesHelper INSTANCE;

    private static SQLiteDatabase database;

    public MoviesHelper(Context context){
        databaseHelper = new DatabaseHelper(context);
    }

    public static MoviesHelper getInstance(Context context){
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MoviesHelper(context);
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

        if (database.isOpen())
            database.close();

    }

    public ArrayList<Movie> getAllMovies() {
        ArrayList<Movie> arrayList = new ArrayList<>();
        Cursor cursor = database.query(DATABASE_TABLE, null,
                null,
                null,
                null,
                null,
                ID + " DESC",
                null);
        cursor.moveToFirst();
        Movie movie;
        if (cursor.getCount() > 0) {
            do {
                movie = new Movie();
                movie.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                movie.setPhoto(cursor.getString(cursor.getColumnIndexOrThrow(POSTER)));
                movie.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(OVERVIEW)));
                movie.setVote_avg(cursor.getDouble(cursor.getColumnIndexOrThrow(VOTE_AVERAGE)));
                movie.setLanguage(cursor.getString(cursor.getColumnIndexOrThrow(ORIGINAL_LANGUAGE)));
                movie.setRelease(cursor.getString(cursor.getColumnIndexOrThrow(RELEASE_DATE)));
                movie.setId(cursor.getInt(cursor.getColumnIndexOrThrow(ID)));

                arrayList.add(movie);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insertMovies(Movie movie) {
        ContentValues args = new ContentValues();
        args.put(ID, movie.getId());
        args.put(POSTER, movie.getPhoto());
        args.put(TITLE, movie.getTitle());
        args.put(RELEASE_DATE, movie.getRelease());
        args.put(ORIGINAL_LANGUAGE, movie.getLanguage());
        args.put(VOTE_AVERAGE, movie.getVote_avg());
        args.put(OVERVIEW, movie.getOverview());

        return database.insert(DATABASE_TABLE, null, args);
    }

    public long insertAll(Movie movie) {
        ContentValues args = new ContentValues();
        args.put(DatabaseContract.AllColumns.ID, movie.getId());
        args.put(DatabaseContract.AllColumns.POSTER, movie.getPhoto());
        args.put(DatabaseContract.AllColumns.TITLE, movie.getTitle());
        args.put(DatabaseContract.AllColumns.DATE, movie.getRelease());
        args.put(DatabaseContract.AllColumns.ORIGINAL_LANGUAGE, movie.getLanguage());
        args.put(DatabaseContract.AllColumns.VOTE_AVERAGE, movie.getVote_avg());
        args.put(DatabaseContract.AllColumns.OVERVIEW, movie.getOverview());

        return database.insert(TABLE_ALL, null, args);
    }

    public ArrayList<Movie> getAllData() {
        ArrayList<Movie> arrayList = new ArrayList<>();
        database = databaseHelper.getReadableDatabase();
        Cursor cursor = database.query(TABLE_ALL, null,
                null,
                null,
                null,
                null,
                ID + " DESC",
                null);
        cursor.moveToFirst();
        Movie allData;
        if (cursor.getCount() > 0) {
            do {
                allData = new Movie();
                allData.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.AllColumns.TITLE)));
                allData.setPhoto(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.AllColumns.POSTER)));
                allData.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.AllColumns.OVERVIEW)));
                allData.setVote_avg(cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseContract.AllColumns.VOTE_AVERAGE)));
                allData.setLanguage(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.AllColumns.ORIGINAL_LANGUAGE)));
                allData.setRelease(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.AllColumns.DATE)));
                allData.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.AllColumns.ID)));

                arrayList.add(allData);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public int deleteAll(int id) {
        return database.delete(TABLE_ALL, DatabaseContract.AllColumns.ID + " = '" + id + "'", null);
    }

    public int deleteMovie(int id) {
        return database.delete(TABLE_MOVIE, ID + " = '" + id + "'", null);
    }

    public Cursor queryProvider() {
        return database.query(
                DatabaseContract.TABLE_ALL,
                null,
                null,
                null,
                null,
                null,
                DatabaseContract.MovieColumns.ID + " DESC"
        );
    }

    public Cursor queryProviderById(String id) {
        return database.query(TABLE_ALL, null
                , ID + " = ?"
                , new String[]{id}
                , null
                , null
                , null
                , null);
    }

    public long insertProvider(ContentValues values) {
        return database.insert(TABLE_ALL, null, values);
    }

    public int updateProvider(String id, ContentValues values) {
        return database.update(TABLE_ALL, values,
                ID + " = ?", new String[]{id});
    }

    public int deleteProvider(String id) {
        return database.delete(TABLE_ALL,
                ID + " = ?", new String[]{id});
    }

    public int countRowTable(){
        database = databaseHelper.getWritableDatabase();
        String count = "SELECT count(*) FROM tball";
        Cursor mcursor = database.rawQuery(count, null);
        mcursor.moveToFirst();
        int countRow = mcursor.getInt(0);
        return countRow;
    }
}
