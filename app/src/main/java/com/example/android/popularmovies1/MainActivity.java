package com.example.android.popularmovies1;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.popularmovies1.data.FavoritesContract;
import com.example.android.popularmovies1.data.FavoritesDbHelper;

import java.util.ArrayList;

import static android.view.View.GONE;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Movies>> {

//    @BindView(R.id.movie_image_iv)
//    ImageView movieImage;

    ImageView movieImage;

    ImageView gridViewImage;

    TextView no_fav_default_tv;

    private static final String POPULAR_URL = "http://api.themoviedb.org/3/movie/popular?api_key=918a00c9c1503686a84236410cfe46d9";

    private static final String TOPRATED_URL = "http://api.themoviedb.org/3/movie/top_rated?api_key=918a00c9c1503686a84236410cfe46d9";

    private static String DEFAULT_MOVIE_URL = "http://api.themoviedb.org/3/movie/popular?api_key=918a00c9c1503686a84236410cfe46d9";

    private static final int MOVIES_LOADER_ID = 10;

    private static final int FAVORITES_MOVIES = 11;

    private static final String MOVIE_ID = "id";

    private static final String MOVIE_TITLE = "title";

    private static final String MOVIE_PLOT = "overview";

    private static final String MOVIE_POSTER = "poster_path";

    private static final String MOVIE_RATING = "vote_average";

    private static final String MOVIE_DATE = "release_date";

    private MoviesAdapter moviesAdapter;

    private ProgressBar progressBar;

    private TextView defaultTextView;

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private static final String TABLE_NAME = "Favorites";

    private FavoritesDbHelper mDbHelper = new FavoritesDbHelper(this);

    private GridView gridView;

//    private ArrayList<Movies> movies = new ArrayList<Movies>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //   ButterKnife.bind(this);

        movieImage = (ImageView) findViewById(R.id.movie_image_iv);

        gridView = (GridView) findViewById(R.id.gridview);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        defaultTextView = (TextView) findViewById(R.id.default_tv);
        gridViewImage = (ImageView) findViewById(R.id.grid_item_ig);

        no_fav_default_tv = (TextView) findViewById(R.id.no_fav_tv);

    //    movies = new ArrayList<>();

        moviesAdapter = new MoviesAdapter(this, new ArrayList<Movies>());
        gridView.setAdapter(moviesAdapter);

        // Checks internet connection status. Loads movie data if there is connection, else shows a default text message.
        boolean status = checkConnection();
        if (status) {
            // Get reference to loader manager and initialize the loader.
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(MOVIES_LOADER_ID, null, this).forceLoad();
        } else {
            progressBar.setVisibility(GONE);
            defaultTextView.setVisibility(VISIBLE);
        }

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Movies movies = moviesAdapter.getItem(i);
                Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
                assert movies != null;
                intent.putExtra(MOVIE_ID, movies.getMovieId());
                intent.putExtra(MOVIE_TITLE, movies.getMovieTitle());
                intent.putExtra(MOVIE_PLOT, movies.getMoviePlotSynopsis());
                intent.putExtra(MOVIE_POSTER, movies.getMoviePosterPath());
                intent.putExtra(MOVIE_RATING, movies.getMovieVoteAverage());
                intent.putExtra(MOVIE_DATE, movies.getMovieReleaseDate());
                //     Log.v(LOG_TAG, String.valueOf(movies.getMovieId()));
                startActivity(intent);
            }
        });
        checkConnection();
    }

    public boolean checkConnection() {
        ConnectivityManager connectivityMgr = (ConnectivityManager)
            getSystemService(Context.CONNECTIVITY_SERVICE);

        assert connectivityMgr != null;
        NetworkInfo networkInfo = connectivityMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        return networkInfo != null && networkInfo.isConnected();
    }

    @Override
    public Loader<ArrayList<Movies>> onCreateLoader(int i, Bundle bundle) {
        return new MoviesLoader(this, DEFAULT_MOVIE_URL);

    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Movies>> loader, ArrayList<Movies> moviesArrayList) {
        progressBar.setVisibility(INVISIBLE);
        moviesAdapter.clear();
        if (moviesArrayList != null) {
            moviesAdapter.addAll(moviesArrayList);
        }
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Movies>> loader) {
        moviesAdapter.clear();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_popular) {
            DEFAULT_MOVIE_URL = POPULAR_URL;
            getSupportActionBar().setTitle("Most Popular");
        //    moviesAdapter.notifyDataSetChanged();
            boolean status = checkConnection();
            if (status) {
                getLoaderManager().restartLoader(MOVIES_LOADER_ID, null, MainActivity.this).forceLoad();
                defaultTextView.setVisibility(INVISIBLE);
            } else {
                progressBar.setVisibility(GONE);
                defaultTextView.setVisibility(VISIBLE);
                moviesAdapter.clear();
            }
        } else if (id == R.id.action_highest) {
            DEFAULT_MOVIE_URL = TOPRATED_URL;
            getSupportActionBar().setTitle("Top Rated");
            boolean status = checkConnection();
            if (status) {
                getLoaderManager().restartLoader(MOVIES_LOADER_ID, null, MainActivity.this).forceLoad();
                defaultTextView.setVisibility(INVISIBLE);
            } else {
                progressBar.setVisibility(GONE);
                defaultTextView.setVisibility(VISIBLE);
                moviesAdapter.clear();
            }
            return true;
        } else if (id == R.id.action_fav) {
            getSupportActionBar().setTitle("Favorites");
         //   movies.clear();
            getLoaderManager().initLoader(FAVORITES_MOVIES,null,this);
            //   moviesAdapter.setData(null);
            showFavoritesData();
        }
        return super.onOptionsItemSelected(item);
    }

    public void showFavoritesData() {

        String[] projection = {FavoritesContract.MovieFavorites.MOVIE_ID,
            FavoritesContract.MovieFavorites.MOVIE_TITLE,
            FavoritesContract.MovieFavorites.MOVIE_PLOT,
            FavoritesContract.MovieFavorites.MOVIE_POSTER,
            FavoritesContract.MovieFavorites.MOVIE_RATING,
            FavoritesContract.MovieFavorites.MOVIE_DATE
        };

        Cursor cursor1 = getContentResolver().query(FavoritesContract.MovieFavorites.CONTENT_URI, projection, null, null, null);
        if (cursor1 == null) {
            return;
        }

        ArrayList<Movies> movies = new ArrayList<>();

        for (int i = 0; i < cursor1.getCount(); i++) {
            cursor1.moveToPosition(i);
            int id = cursor1.getInt(cursor1.getColumnIndex(FavoritesContract.MovieFavorites.MOVIE_ID));
            String title = cursor1.getString(cursor1.getColumnIndex(FavoritesContract.MovieFavorites.MOVIE_TITLE));
            //       Log.d(LOG_TAG, "title is " + title);
            String poster = cursor1.getString(cursor1.getColumnIndex(FavoritesContract.MovieFavorites.MOVIE_POSTER));
            //       Log.d(LOG_TAG, "poster is " + poster);
            String plot = cursor1.getString(cursor1.getColumnIndex(FavoritesContract.MovieFavorites.MOVIE_PLOT));
            //       Log.d(LOG_TAG, "rating is " + rating);
            String rating = cursor1.getString(cursor1.getColumnIndex(FavoritesContract.MovieFavorites.MOVIE_RATING));
            //       Log.d(LOG_TAG, "date is " + date);
            String date = cursor1.getString(cursor1.getColumnIndex(FavoritesContract.MovieFavorites.MOVIE_DATE));

            Movies movies1 = new Movies(id, title, plot, poster, rating, date);
            movies.add(movies1);
        }
        cursor1.close();
            moviesAdapter.setData(movies);
    }
}

