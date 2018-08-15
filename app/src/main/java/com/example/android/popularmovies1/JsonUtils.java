package com.example.android.popularmovies1;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Created by lhu513 on 5/17/18.
 */

class JsonUtils {

    private static final String LOG_TAG = JsonUtils.class.getName();

    private static final String MOVIE_ID = "id";

    private static final String MOVIE_TITLE = "title";

    private static final String MOVIE_PLOT = "overview";

    private static final String MOVIE_POSTER = "poster_path";

    private static final String MOVIE_RATING = "vote_average";

    private static final String MOVIE_DATE = "release_date";

    private static final String RESULTS = "results";

    private static final String AUTHOR = "author";

    private static final String CONTENT = "content";

    private static final String VIDEO_TYPE = "youtube";

    private static final String NAME = "name";

    private static final String SOURCE = "source";

    private JsonUtils() {

    }

    //to create url//
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error in creating url", e);
            e.printStackTrace();
        }
        return url;
    }

    // This method fetches data from url

    public static ArrayList<Movies> fetchMovieData(String requestUrl) {
        URL url = createUrl(requestUrl);
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return extractMovies(jsonResponse);
    }

    // This method fetches review data from url

    public static ArrayList<Reviews> fetchReviewData(String requestUrl, String id) {
        URL url = createReviewUrl(requestUrl, id);
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return extractReviews(jsonResponse);
    }

    private static URL createReviewUrl(String requestUrl, String id) {
        String REVIEW_URL = "http://api.themoviedb.org/3/movie/";
        String REVIEW = "reviews";
        String API_KEY = "918a00c9c1503686a84236410cfe46d9";
        Uri reviewUri = Uri.parse(REVIEW_URL).buildUpon()
            .appendPath(id)
            .appendPath(REVIEW)
            .appendQueryParameter("api_key", API_KEY)
            .build();
        Log.d(LOG_TAG, "Review url is " + reviewUri);
        URL url = null;
        try {
            url = new URL(reviewUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    private static ArrayList<Reviews> extractReviews(String jsonResponse) {
        if (TextUtils.isEmpty(jsonResponse)) {
            return null;
        }
        ArrayList<Reviews> reviews = new ArrayList<>();
        try {
            // fetch main node
            JSONObject root = new JSONObject(jsonResponse);
            JSONArray results = root.getJSONArray(RESULTS);

            for (int i = 0; i < results.length(); i++) {

                JSONObject reviewData = results.getJSONObject(i);
                //    int movieId = reviewData.getInt(MOVIE_ID);
                String author = reviewData.getString(AUTHOR);
                String content = reviewData.getString(CONTENT);
                //    Log.d(LOG_TAG,"Id is" + movieId);
                Log.d(LOG_TAG, "Author is" + author);
                Log.d(LOG_TAG, "Content is" + content);

                reviews.add(new Reviews(author, content));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return reviews;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = null;
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(50000);
            urlConnection.connect();
            int responseCode = urlConnection.getResponseCode();
            if (responseCode == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Response Code" + responseCode);
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error while retrieving data", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    // This method parses the api response in json format

    private static ArrayList<Movies> extractMovies(String movieJson) {
        if (TextUtils.isEmpty(movieJson)) {
            return null;
        }
        ArrayList<Movies> movies = new ArrayList<>();
        try {
            // fetch main node
            JSONObject root = new JSONObject(movieJson);
            JSONArray results = root.getJSONArray(RESULTS);

            for (int i = 0; i < results.length(); i++) {

                JSONObject movieData = results.getJSONObject(i);
                int movieId = movieData.getInt(MOVIE_ID);
                String movieTitle = movieData.getString(MOVIE_TITLE);
                String movieReleaseDate = movieData.getString(MOVIE_DATE);
                String moviePlotSynopsis = movieData.getString(MOVIE_PLOT);
                String moviePosterPath = movieData.getString(MOVIE_POSTER);
                String movieVoteAverage = movieData.getString(MOVIE_RATING);
                Log.v(LOG_TAG, "Movie Id is" + movieId);

                movies.add(new Movies(movieId, movieTitle, moviePlotSynopsis, moviePosterPath, movieVoteAverage, movieReleaseDate));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return movies;
    }

    public static ArrayList<Trailers> fetchTrailerData(String mUrl, String movie_id) {
        URL url = createTrailerUrl(mUrl, movie_id);
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return extractTrailers(jsonResponse);
    }

    private static ArrayList<Trailers> extractTrailers(String jsonResponse) {

        if (TextUtils.isEmpty(jsonResponse)) {
            return null;
        }
        ArrayList<Trailers> trailers = new ArrayList<>();
        try {
            // fetch main node
            JSONObject root = new JSONObject(jsonResponse);
            JSONArray youtube = root.getJSONArray(VIDEO_TYPE);

            for (int i = 0; i < youtube.length(); i++) {

                JSONObject trailerData = youtube.getJSONObject(i);
                //    int movieId = reviewData.getInt(MOVIE_ID);
                //   String key = trailerData.getString("key");
                String name = trailerData.getString(NAME);
                String source = trailerData.getString(SOURCE);
                //    String content = reviewData.getString(CONTENT);
                //    Log.d(LOG_TAG,"Id is" + movieId);
                Log.d(LOG_TAG, "Name is" + name);
                Log.d(LOG_TAG, "Source is " + source);
                //     Log.d(LOG_TAG,"Content is" + content);

                trailers.add(new Trailers(name, source));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return trailers;
    }

    private static URL createTrailerUrl(String mUrl, String movie_id) {
        String TRAILER_URL = "http://api.themoviedb.org/3/movie/";
        String MOVIE = "trailers";
        String API_KEY = "918a00c9c1503686a84236410cfe46d9";
        Uri trailerUri = Uri.parse(TRAILER_URL).buildUpon()
            .appendPath(movie_id)
            .appendPath(MOVIE)
            .appendQueryParameter("api_key", API_KEY)
            .build();
        Log.d(LOG_TAG, "Trailer url is " + trailerUri);
        URL url = null;
        try {
            url = new URL(trailerUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }
}
