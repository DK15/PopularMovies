package com.example.android.popularmovies1;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.ArrayList;

class ReviewsLoader extends AsyncTaskLoader<ArrayList<Reviews>> {

    private String mUrl;

    private String movie_id;

    /**
     * MoviesLoader.
     *
     * @param context of the activity
     * @param url     url
     */
    public ReviewsLoader(Context context, String url, String id) {
        super(context);
        mUrl = url;
        movie_id = id;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public ArrayList<Reviews> loadInBackground() {
        if (mUrl == null) {
            return null;
        }
        return JsonUtils.fetchReviewData(mUrl, movie_id);
    }
}
