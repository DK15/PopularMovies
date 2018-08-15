package com.example.android.popularmovies1;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.Intent;
import android.content.Loader;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.popularmovies1.data.FavoritesContract;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lhu513 on 5/20/18.
 */

public class DetailsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Movies>> {

    private static String DEFAULT_MOVIE_URL = "http://api.themoviedb.org/3/movie/popular?api_key=918a00c9c1503686a84236410cfe46d9";

//    private static final String TRAILER_URL = "http://api.themoviedb.org/3/movie/id/trailer?api_key=918a00c9c1503686a84236410cfe46d9";

//    private static final String REVIEW_URL = "http://api.themoviedb.org/3/movie/id/review?api_key=918a00c9c1503686a84236410cfe46d9";

    private static final String LOG_TAG = DetailsActivity.class.getSimpleName();

//    private static final String RESULTS = "results";

    @BindView(R.id.movie_image_iv)
    ImageView movieImage;

    @BindView(R.id.title_tv)
    TextView movieTitle;

    @BindView(R.id.plot_tv)
    TextView moviePlot;

    @BindView(R.id.rating_tv)
    TextView movieRating;

    @BindView(R.id.date_tv)
    TextView releaseDate;

    @BindView(R.id.play_btn)
    Button trailerButton;

    @BindView(R.id.review_btn)
    Button reviewButton;

    @BindView(R.id.fav_btn)
    Button favButton;

    @BindView(R.id.rem_fav_btn)
    Button removeFavButton;

    ArrayList<Reviews> mReviews;

    private static final String MOVIE_ID = "id";

    private static final String MOVIE_TITLE = "title";

    private static final String MOVIE_PLOT = "overview";

    private static final String MOVIE_POSTER = "poster_path";

    private static final String MOVIE_RATING = "vote_average";

    private static final String MOVIE_DATE = "release_date";

    private static final String AUTHOR = "author";

    private static final String CONTENT = "content";

    private Uri mCurrentProductUri;

    boolean ifFav = false;

    private int movie_id;

    private String title;

    private String plot;

    private String poster;

    private String rating;

    MoviesAdapter moviesAdapter;

    ReviewAdapter reviewAdapter;

    ListView listView;

    ArrayList<Reviews> reviews;
//    ArrayList<Movies> movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        moviesAdapter = new MoviesAdapter(this, new ArrayList<Movies>());

        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            Toast.makeText(this, R.string.api_error, Toast.LENGTH_SHORT).show();
        } else {
            mCurrentProductUri = getIntent().getData();
            movie_id = bundle.getInt(FavoritesContract.MovieFavorites.MOVIE_ID);
            title = bundle.getString(MOVIE_TITLE);
            plot = bundle.getString(MOVIE_PLOT);
            poster = bundle.getString(MOVIE_POSTER);
            rating = bundle.getString(MOVIE_RATING);
            movieTitle.setText(title);
            String poster_path = "http://image.tmdb.org/t/p/w185/" + poster;
            Picasso.with(this).load(poster_path).into(movieImage);
            moviePlot.setText(plot);
            movieRating.setText(rating);

            // formatting date to be displayed in mm-dd-yyyy format

            SimpleDateFormat dateFormatJSON = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            SimpleDateFormat dateFormat2 = new SimpleDateFormat("MM-dd-yyyy", Locale.ENGLISH);

            try {
                Date dateNews = dateFormatJSON.parse(bundle.getString(MOVIE_DATE));
                String date = dateFormat2.format(dateNews);
                releaseDate.setText(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        favButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveFavoritesData();
              //  ifFav = true;
            }
        });

        removeFavButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteFavoritesData();
             //   ifFav = false;
            }
        });
        //     tapReviewButton();

        reviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), ReviewsActivity.class);
                Bundle bundle1 = new Bundle();
                bundle1.putString("movie_id", String.valueOf(movie_id));
                intent.putExtras(bundle1);
                Log.d(LOG_TAG, "movie id" + movie_id);
                startActivity(intent);
            }
        });

        trailerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), TrailersActivity.class);
                Bundle bundle1 = new Bundle();
                bundle1.putString("movie_id", String.valueOf(movie_id));
                intent.putExtras(bundle1);
                Log.d(LOG_TAG, "movie id" + movie_id);
                startActivity(intent);
            }
        });
    }

    private void deleteFavoritesData() {
        Uri uri = FavoritesContract.MovieFavorites.CONTENT_URI;
        uri.buildUpon().appendPath(MOVIE_ID).build();
        int deleted = getContentResolver().delete(uri, null, null);
        if (deleted == 1) {
            Toast.makeText(this, "Removed from favorites", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveFavoritesData() {

        String title = movieTitle.getText().toString().trim();
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        String poster = bundle.getString(MOVIE_POSTER);
        String poster_path = "http://image.tmdb.org/t/p/w185/" + poster;
        Picasso.with(this).load(poster_path).into(movieImage);
        String plot = moviePlot.getText().toString().trim();
        String rating = movieRating.getText().toString().trim();
        String date = releaseDate.getText().toString().trim();

        ContentValues cv = new ContentValues();
        cv.put(FavoritesContract.MovieFavorites.MOVIE_ID, movie_id);
        cv.put(FavoritesContract.MovieFavorites.MOVIE_TITLE, title);
        cv.put(FavoritesContract.MovieFavorites.MOVIE_POSTER, poster_path);
        cv.put(FavoritesContract.MovieFavorites.MOVIE_PLOT, plot);
        cv.put(FavoritesContract.MovieFavorites.MOVIE_RATING, rating);
        cv.put(FavoritesContract.MovieFavorites.MOVIE_DATE, date);

        if (mCurrentProductUri == null) {
            Uri uri = getContentResolver().insert(FavoritesContract.MovieFavorites.CONTENT_URI, cv);
            if (uri == null) {
                Toast.makeText(this, "Save failed", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Movie saved as Favorite !", Toast.LENGTH_SHORT).show();
            }
            //  favButton.setEnabled(false);
        }
    }


    @Override
    public Loader<ArrayList<Movies>> onCreateLoader(int i, Bundle bundle) {
        return new MoviesLoader(this, DEFAULT_MOVIE_URL);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Movies>> loader, ArrayList<Movies> movies) {
        moviesAdapter.clear();
        if (movies != null) {
            moviesAdapter.addAll(movies);
        }
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Movies>> loader) {

    }
}

