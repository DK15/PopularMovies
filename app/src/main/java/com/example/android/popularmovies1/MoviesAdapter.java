package com.example.android.popularmovies1;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lhu513 on 5/17/18.
 */

class MoviesAdapter extends ArrayAdapter<Movies> {

    private static final String LOG_TAG = MoviesAdapter.class.getSimpleName();

    private List<Movies> movieList;

    public MoviesAdapter(Activity context, ArrayList<Movies> movies) {
        super(context, 0, movies);
        this.movieList = movies;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        // Gets the object from the ArrayAdapter at the position
        Movies movies = movieList.get(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.grid_layout, parent, false);
        }
        ImageView iconView = (ImageView) convertView.findViewById(R.id.grid_item_ig);
        String image_path = "http://image.tmdb.org/t/p/w185/";
        assert movies != null;
        Picasso.with(getContext()).load(image_path + movies.getMoviePosterPath()).into(iconView);
        return convertView;
    }

    @Override
    public int getCount() {
        if (movieList.size() == 0) {
            return 0;
        } else {
            return movieList.size();
        }
    }

    public void setData(List<Movies> movies) {
        movieList = (ArrayList<Movies>) movies;
        notifyDataSetChanged();

    }
}
