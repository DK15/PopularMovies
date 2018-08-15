package com.example.android.popularmovies1;

import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class TrailersActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Trailers>> {

    private List<Trailers> trailers = new ArrayList<>();

    private RecyclerView recyclerView;

    private TrailerAdapter trailerAdapter;

    private TextView trailer_name_tv;
//    private TextView content_tv;

    private String movieId;

    //    @BindView(R.id.play_trailer_btn)
//    Button playTrailer;
    Button playTrailer;

    private static final int LOADER_ID = 1338;

    public static final String TRAILER_URL = "http://api.themoviedb.org/3/movie/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trailer_activity);
        //   ButterKnife.bind(this);
        trailer_name_tv = (TextView) findViewById(R.id.trailer_name);
        //   playTrailer = (Button) findViewById(R.id.play_trailer_btn1);

        //    content_tv = (TextView) findViewById(R.id.review_item_content);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_tr);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            return;
        } else {
            movieId = bundle.getString("movie_id");
        }

        // start loader
        getLoaderManager().initLoader(LOADER_ID, null, this).forceLoad();
        //    showReviewData();

    }

    @Override
    public Loader<ArrayList<Trailers>> onCreateLoader(int i, Bundle bundle) {
        return new TrailersLoader(this, TRAILER_URL, movieId);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Trailers>> loader, ArrayList<Trailers> trailers) {
        trailerAdapter = new TrailerAdapter(trailers);
        recyclerView.setAdapter(trailerAdapter);

    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Trailers>> loader) {

    }
}