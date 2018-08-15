package com.example.android.popularmovies1;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lhu513 on 5/17/18.
 */

public class Movies implements Parcelable {

    private int movieId;

    private String movieTitle;

    private String moviePlotSynopsis;

    private String moviePosterPath;

    private String movieVoteAverage;

    private String movieReleaseDate;

    public Movies(int movieId, String movieTitle, String moviePlotSynopsis, String moviePosterPath, String movieVoteAverage,
        String movieReleaseDate) {
        this.movieId = movieId;
        this.movieTitle = movieTitle;
        this.moviePlotSynopsis = moviePlotSynopsis;
        this.moviePosterPath = moviePosterPath;
        this.movieVoteAverage = movieVoteAverage;
        this.movieReleaseDate = movieReleaseDate;
    }

    private Movies(Parcel in) {
        movieId = in.readInt();
        movieTitle = in.readString();
        moviePlotSynopsis = in.readString();
        moviePosterPath = in.readString();
        movieVoteAverage = in.readString();
        movieReleaseDate = in.readString();
    }

    public static final Creator<Movies> CREATOR = new Creator<Movies>() {
        @Override
        public Movies createFromParcel(Parcel in) {
            return new Movies(in);
        }

        @Override
        public Movies[] newArray(int size) {
            return new Movies[size];
        }
    };

    public String getMovieVoteAverage() {
        return movieVoteAverage;
    }

    public String getMoviePlotSynopsis() {
        return moviePlotSynopsis;
    }

    public String getMoviePosterPath() {
        return moviePosterPath;
    }

    public String getMovieReleaseDate() {
        return movieReleaseDate;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public Integer getMovieId() {
        return movieId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(movieId);
        parcel.writeString(movieTitle);
        parcel.writeString(moviePlotSynopsis);
        parcel.writeString(moviePosterPath);
        parcel.writeString(movieVoteAverage);
        parcel.writeString(movieReleaseDate);
    }
}
