package com.example.android.popularmovies1.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public class FavoritesContract {

    public static final String CONTENT_AUTHORITY = "com.example.android.popularmovies1";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String FAVORITE_MOVIES = "popularmovies1";

    private FavoritesContract() {

    }

    public static final class MovieFavorites implements BaseColumns {

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, FAVORITE_MOVIES);

        public static final String CONTENT_LIST_TYPE =
            ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + FAVORITE_MOVIES;

        public static final String CONTENT_ITEM_TYPE =
            ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + FAVORITE_MOVIES;

        public static final String TABLE_NAME = "Favorites";

        public static final Uri FAVORITE_URI = Uri.parse("content://" + CONTENT_AUTHORITY + "/" + TABLE_NAME);

        public static final String _ID = BaseColumns._ID;

        public static final String MOVIE_ID = "id";

        public static final String MOVIE_TITLE = "title";

        public static final String MOVIE_PLOT = "overview";

        public static final String MOVIE_POSTER = "poster_path";

        public static final String MOVIE_RATING = "vote_average";

        public static final String MOVIE_DATE = "release_date";

    }
}

