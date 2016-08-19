package com.twistedequations.rxmvp.reddit.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class RedditListing implements Parcelable {

    @SerializedName("kind")
    @Expose
    public String kind;

    @SerializedName("data")
    @Expose
    public Data data;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.kind);
        dest.writeParcelable(this.data, flags);
    }

    public RedditListing() {
    }

    protected RedditListing(Parcel in) {
        this.kind = in.readString();
        this.data = in.readParcelable(Data.class.getClassLoader());
    }

    public static final Creator<RedditListing> CREATOR = new Creator<RedditListing>() {
        @Override
        public RedditListing createFromParcel(Parcel source) {
            return new RedditListing(source);
        }

        @Override
        public RedditListing[] newArray(int size) {
            return new RedditListing[size];
        }
    };
}
