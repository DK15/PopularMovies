package com.example.android.popularmovies1;

import android.os.Parcel;
import android.os.Parcelable;

class Reviews implements Parcelable {

    protected Reviews(Parcel in) {
        author = in.readString();
        content = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(author);
        dest.writeString(content);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Reviews> CREATOR = new Creator<Reviews>() {
        @Override
        public Reviews createFromParcel(Parcel in) {
            return new Reviews(in);
        }

        @Override
        public Reviews[] newArray(int size) {
            return new Reviews[size];
        }
    };

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    private String author;

    private String content;

//    public String getId() {
//        return id;
//    }

    //   private String id;

    public Reviews(String author, String content) {
        //    this.id = id;
        this.author = author;
        this.content = content;
    }
}
