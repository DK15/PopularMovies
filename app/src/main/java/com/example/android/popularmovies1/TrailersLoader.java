package com.example.android.popularmovies1;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.ArrayList;

public class TrailersLoader extends AsyncTaskLoader<ArrayList<Trailers>> {

    private String mUrl;

    private String movie_id;

    /**
     * MoviesLoader.
     *
     * @param context of the activity
     * @param url     url
     */
    public TrailersLoader(Context context, String url, String id) {
        super(context);
        mUrl = url;
        movie_id = id;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public ArrayList<Trailers> loadInBackground() {
        if (mUrl == null) {
            return null;
        }
        return JsonUtils.fetchTrailerData(mUrl, movie_id);
    }
}
