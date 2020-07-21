package com.rapandroid.favoritekamov.database;

import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {
    public static final String TABLE_MOVIE = "tbmovie";
    public static final class MovieColumns implements BaseColumns {
        static final String ID = "id";
        static final String POSTER = "poster_path_string";
        static final String TITLE = "title";
        public static final String RELEASE_DATE = "release_date";
        static final String ORIGINAL_LANGUAGE = "original_language";
        static final String VOTE_AVERAGE = "vote_average";
        static final String OVERVIEW = "overview";
    }

    public static final String AUTHORITY = "com.rapandroid.kamov5";

    public static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
            .authority(AUTHORITY)
            .appendPath(DatabaseContract.TABLE_ALL)
            .build();

    public static final String TABLE_TVSHOW = "tbtvshow";
    public static final class TvShowColumns implements BaseColumns {
        static final String ID = "id";
        static final String POSTER = "poster_path_string";
        static final String TITLE = "title";
        static final String FIRST_AIR_DATE = "first_air_date";
        static final String ORIGINAL_LANGUAGE = "original_language";
        static final String VOTE_AVERAGE = "vote_average";
        static final String OVERVIEW = "overview";
    }

    public static final String TABLE_ALL = "tball";
    public static final class AllColumns implements BaseColumns {
        public static final String ID = "id";
        public static final String POSTER = "poster_path_string";
        public static final String TITLE = "title";
        public static final String DATE = "date";
        public static final String ORIGINAL_LANGUAGE = "original_language";
        public static final String VOTE_AVERAGE = "vote_average";
        public static final String OVERVIEW = "overview";
    }
}
