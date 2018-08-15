package com.example.android.popularmovies1.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

public class FavoritesProvider extends ContentProvider {

    public static final String LOG_TAG = FavoritesProvider.class.getSimpleName();

    private static final int MOVIES = 100;

    private static final int MOVIES_ID = 101;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(FavoritesContract.CONTENT_AUTHORITY, FavoritesContract.FAVORITE_MOVIES, MOVIES);
        sUriMatcher.addURI(FavoritesContract.CONTENT_AUTHORITY, FavoritesContract.FAVORITE_MOVIES + "/#", MOVIES_ID);
    }

    private FavoritesDbHelper mDbHelper;

    @Override
    public boolean onCreate() {
        mDbHelper = new FavoritesDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1,
        @Nullable String s1) {

        SQLiteDatabase database = mDbHelper.getReadableDatabase();
        Cursor cursor;
        int match = sUriMatcher.match(uri);
        switch (match) {
            case MOVIES:
                cursor = database.query(FavoritesContract.MovieFavorites.TABLE_NAME, strings, s, strings1, null, null, s1);
                break;
            case MOVIES_ID:
                s = FavoritesContract.MovieFavorites._ID + "=?";
                strings1 = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = database.query(FavoritesContract.MovieFavorites.TABLE_NAME, strings, s, strings1, null, null,
                    s1);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {

        final int match = sUriMatcher.match(uri);
        switch (match) {
            case MOVIES:
                return FavoritesContract.MovieFavorites.CONTENT_LIST_TYPE;
            case MOVIES_ID:
                return FavoritesContract.MovieFavorites.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case MOVIES:
                assert contentValues != null;
                return insertProduct(uri, contentValues);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    private Uri insertProduct(Uri uri, ContentValues values) {

        String name = values.getAsString(FavoritesContract.MovieFavorites.MOVIE_TITLE);
        if (TextUtils.isEmpty(name)) {
            throw new IllegalArgumentException("Movie name is required !!");
        }

        String poster = values.getAsString(FavoritesContract.MovieFavorites.MOVIE_POSTER);
        if (poster == null) {
            throw new IllegalArgumentException("Movie poster is required !!");
        }

        String plot = values.getAsString(FavoritesContract.MovieFavorites.MOVIE_PLOT);
        if (TextUtils.isEmpty(plot)) {
            throw new IllegalArgumentException("Movie plot is required !!");
        }

        String rating = values.getAsString(FavoritesContract.MovieFavorites.MOVIE_RATING);
        if (TextUtils.isEmpty(rating)) {
            throw new IllegalArgumentException("Movie rating is required !!");
        }

        String date = values.getAsString(FavoritesContract.MovieFavorites.MOVIE_DATE);
        if (date == null) {
            throw new IllegalArgumentException("Movie date is required !!");
        }
        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        long id = database.insert(FavoritesContract.MovieFavorites.TABLE_NAME, null, values);
        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        final int match = sUriMatcher.match(uri);
        int rowsDeleted;

        switch (match) {
            case MOVIES:
                rowsDeleted = database.delete(FavoritesContract.MovieFavorites.TABLE_NAME, s, strings);
                break;
            case MOVIES_ID:
                s = FavoritesContract.MovieFavorites._ID + "=?";
                strings = new String[]{String.valueOf(ContentUris.parseId(uri))};
                rowsDeleted = database.delete(FavoritesContract.MovieFavorites.TABLE_NAME, s, strings);
                break;
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }


    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case MOVIES:
                return updateData(uri, contentValues, s, strings);
            case MOVIES_ID:
                s = FavoritesContract.MovieFavorites._ID + "=?";
                strings = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return updateData(uri, contentValues, s, strings);
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
    }

    /*
    This method updates the data while editing product details. Checks validation and prompts user to enter valid data.
     */

    private int updateData(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        if (values.containsKey(FavoritesContract.MovieFavorites.MOVIE_TITLE)) {
            String name = values.getAsString(FavoritesContract.MovieFavorites.MOVIE_TITLE);

            if (TextUtils.isEmpty(name)) {
                Toast.makeText(getContext(), "Movie name is required !", Toast.LENGTH_SHORT).show();
            }
        }

        if (values.containsKey(FavoritesContract.MovieFavorites.MOVIE_POSTER)) {
            String poster = values.getAsString(FavoritesContract.MovieFavorites.MOVIE_POSTER);

            if (TextUtils.isEmpty(poster)) {
                Toast.makeText(getContext(), "Movie poster is required !", Toast.LENGTH_SHORT).show();
            }
        }

        if (values.containsKey(FavoritesContract.MovieFavorites.MOVIE_PLOT)) {
            String plot = values.getAsString(FavoritesContract.MovieFavorites.MOVIE_PLOT);
            if (plot == null) {
                throw new IllegalArgumentException("Movie plot is required !!");
            }
        }

        if (values.containsKey(FavoritesContract.MovieFavorites.MOVIE_RATING)) {
            String supplierName = values.getAsString(FavoritesContract.MovieFavorites.MOVIE_RATING);

            if (TextUtils.isEmpty(supplierName)) {
                Toast.makeText(getContext(), "Movie rating is required !", Toast.LENGTH_SHORT).show();
            }
        }

        if (values.containsKey(FavoritesContract.MovieFavorites.MOVIE_DATE)) {
            String supplierName = values.getAsString(FavoritesContract.MovieFavorites.MOVIE_DATE);

            if (TextUtils.isEmpty(supplierName)) {
                Toast.makeText(getContext(), "Movie date is required !", Toast.LENGTH_SHORT).show();
            }
        }

        if (values.size() == 0) {
            return 0;
        }

        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        int rowsUpdated = database.update(FavoritesContract.MovieFavorites.TABLE_NAME, values, selection, selectionArgs);
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }
}
