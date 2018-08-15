package com.example.android.popularmovies1;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.ArrayList;

/**
 * Created by lhu513 on 5/18/18.
 */

class MoviesLoader extends AsyncTaskLoader<ArrayList<Movies>> {

    private String mUrl;

    /**
     * MoviesLoader.
     *
     * @param context of the activity
     * @param url     url
     */
    public MoviesLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public ArrayList<Movies> loadInBackground() {
        if (mUrl == null) {
            return null;
        }
        return JsonUtils.fetchMovieData(mUrl);
    }
}
