package com.example.android.popularmovies1;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder> {

    private List<Trailers> trailers;

    //   private final RecyclerViewListener recyclerViewListener;
    private final static String YOUTUBE_URL = "https://www.youtube.com/watch?v=";


    public TrailerAdapter(List<Trailers> trailers) {//, RecyclerViewListener recyclerViewListener) {
        this.trailers = (ArrayList<Trailers>) trailers;
        //    this.recyclerViewListener = recyclerViewListener;
    }

    @Override
    public TrailerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.trailer_list_row, parent, false);
        return new TrailerViewHolder(itemView);//,recyclerViewListener);
    }

    @Override
    public void onBindViewHolder(final TrailerViewHolder holder, final int position) {
        Trailers trailers1 = trailers.get(position);
        holder.trailer_name.setText(trailers1.getTitle());
        //    holder.content_tv.setText(reviews.getContent());
        final String youtube_url = YOUTUBE_URL + trailers1.getSource();

        holder.trailer_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //     Toast.makeText(view.getContext(), "this onClick", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(youtube_url));
                view.getContext().startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {

        return trailers == null ? 0 : trailers.size();
    }


    public class TrailerViewHolder extends RecyclerView.ViewHolder {// implements View.OnClickListener{

        public TextView trailer_name;
        //     private WeakReference<RecyclerViewListener> listenerWeakReference;

        public TrailerViewHolder(View itemView) {// {
            super(itemView);
            //     listenerWeakReference = new WeakReference<>(recyclerViewListener);
            trailer_name = (TextView) itemView.findViewById(R.id.trailer_name);
            //     itemView.setOnClickListener(this);
        }

    }
}


