package com.example.android.popularmovies1.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class FavoritesDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "favorites.db";

    private static final String LOG_TAG = FavoritesDbHelper.class.getSimpleName();

    private static final int DATABASE_VERSION = 1;

    public FavoritesDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE_FAVORITES_TABLE = "CREATE TABLE " + FavoritesContract.MovieFavorites.TABLE_NAME + " (" +
            FavoritesContract.MovieFavorites._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            FavoritesContract.MovieFavorites.MOVIE_ID + " INTEGER, " +
            FavoritesContract.MovieFavorites.MOVIE_TITLE + " TEXT NOT NULL, " +
            FavoritesContract.MovieFavorites.MOVIE_PLOT + " TEXT NOT NULL, " +
            FavoritesContract.MovieFavorites.MOVIE_POSTER + " TEXT NOT NULL, " +
            FavoritesContract.MovieFavorites.MOVIE_RATING + " TEXT NOT NULL, " +
            FavoritesContract.MovieFavorites.MOVIE_DATE + " TEXT NOT NULL " +
            "); ";
        sqLiteDatabase.execSQL(SQL_CREATE_FAVORITES_TABLE);
        Log.v(LOG_TAG, "Table created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + FavoritesContract.MovieFavorites.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
