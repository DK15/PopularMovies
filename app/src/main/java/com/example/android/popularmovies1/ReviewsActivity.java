package com.example.android.popularmovies1;

import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ReviewsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Reviews>> {

    private List<Reviews> reviews = new ArrayList<>();

    private RecyclerView recyclerView;

    private ReviewAdapter reviewAdapter;

    private TextView author_tv;

    private TextView content_tv;

    RecyclerViewListener mRecyclerViewListener;

    private String movieId;

    private static final int LOADER_ID = 1337;

    public static final String REVIEWS_URL = "http://api.themoviedb.org/3/movie/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.review_activity);

//        author_tv = (TextView) findViewById(R.id.review_item_author);
//        content_tv = (TextView) findViewById(R.id.review_item_content);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
//        recyclerView.setHasFixedSize(true);
//        reviewAdapter = new ReviewAdapter(reviews);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            return;
        } else {
            movieId = bundle.getString("movie_id");
        }

//        recyclerView.setAdapter(reviewAdapter);

        // start loader
        getLoaderManager().initLoader(LOADER_ID, null, this).forceLoad();

        //    showReviewData();

    }

    private void showReviewData() {
    }

    @Override
    public Loader<ArrayList<Reviews>> onCreateLoader(int i, Bundle bundle) {
        return new ReviewsLoader(this, REVIEWS_URL, movieId);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Reviews>> loader, ArrayList<Reviews> reviews) {
//        reviewAdapter.setData(reviews);
//        no_review.setVisibility(View.VISIBLE);
        reviewAdapter = new ReviewAdapter(reviews);
        recyclerView.setAdapter(reviewAdapter);

        // make progress bar View.GONE
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Reviews>> loader) {

    }
}
