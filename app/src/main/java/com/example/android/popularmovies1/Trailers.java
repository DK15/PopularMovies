package com.example.android.popularmovies1;

import android.os.Parcel;
import android.os.Parcelable;

public class Trailers implements Parcelable {

    protected Trailers(Parcel in) {
        title = in.readString();
        source = in.readString();
    }

    public static final Creator<Trailers> CREATOR = new Creator<Trailers>() {
        @Override
        public Trailers createFromParcel(Parcel in) {
            return new Trailers(in);
        }

        @Override
        public Trailers[] newArray(int size) {
            return new Trailers[size];
        }
    };

    public String getTitle() {
        return title;
    }

    String title;

    public String getSource() {
        return source;
    }

    String source;

    public Trailers(String title, String source) {
        this.title = title;
        this.source = source;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(source);
    }
}
