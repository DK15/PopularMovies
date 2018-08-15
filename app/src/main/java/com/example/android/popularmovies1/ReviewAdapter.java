package com.example.android.popularmovies1;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.MyViewHolder> {

    private ArrayList<Reviews> reviewsList;


    public ReviewAdapter(List<Reviews> reviewsList) {
        this.reviewsList = (ArrayList<Reviews>) reviewsList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_list_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Reviews reviews = reviewsList.get(position);
        holder.author_tv.setText(reviews.getAuthor());
        holder.content_tv.setText(reviews.getContent());
    }

    @Override
    public int getItemCount() {

        return reviewsList == null ? 0 : reviewsList.size();
    }
    //        return super.getItemId(position);
    //    public long getItemId(int position) {
    //    @Override
    //
    //    }
    //        return super.getItemViewType(position);
    //    public int getItemViewType(int position) {
//    @Override

//    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView author_tv;

        public TextView content_tv;

        public MyViewHolder(View view) {
            super(view);
            author_tv = (TextView) view.findViewById(R.id.review_item_author);
            content_tv = (TextView) view.findViewById(R.id.review_item_content);
        }

    }

    public void setData(ArrayList<Reviews> reviewsList) {
        this.reviewsList = reviewsList;
        notifyDataSetChanged();
    }
}
