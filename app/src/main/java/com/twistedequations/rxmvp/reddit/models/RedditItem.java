
package com.twistedequations.rxmvp.reddit.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class RedditItem implements Parcelable {

    @SerializedName("domain")
    @Expose
    public String domain;
    @SerializedName("media_embed")
    @Expose
    public MediaEmbed mediaEmbed;
    @SerializedName("subreddit")
    @Expose
    public String subreddit;
    @SerializedName("selftext_html")
    @Expose
    public String selftextHtml;
    @SerializedName("selftext")
    @Expose
    public String selftext;
    @SerializedName("body")
    @Expose
    public String body;
    @SerializedName("suggested_sort")
    @Expose
    public String suggestedSort;
    @SerializedName("user_reports")
    @Expose
    public List<Object> userReports = new ArrayList<Object>();
    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("gilded")
    @Expose
    public long gilded;
    @SerializedName("archived")
    @Expose
    public boolean archived;
    @SerializedName("clicked")
    @Expose
    public boolean clicked;
    @SerializedName("author")
    @Expose
    public String author;
    @SerializedName("score")
    @Expose
    public long score;
    @SerializedName("over_18")
    @Expose
    public boolean over18;
    @SerializedName("hidden")
    @Expose
    public boolean hidden;
    @SerializedName("num_comments")
    @Expose
    public long numComments;
    @SerializedName("thumbnail")
    @Expose
    public String thumbnail;
    @SerializedName("subreddit_id")
    @Expose
    public String subredditId;
    @SerializedName("hide_score")
    @Expose
    public boolean hideScore;
    @SerializedName("downs")
    @Expose
    public long downs;
    @SerializedName("saved")
    @Expose
    public boolean saved;
    @SerializedName("stickied")
    @Expose
    public boolean stickied;
    @SerializedName("is_self")
    @Expose
    public boolean isSelf;
    @SerializedName("permalink")
    @Expose
    public String permalink;
    @SerializedName("locked")
    @Expose
    public boolean locked;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("created")
    @Expose
    public long created;
    @SerializedName("url")
    @Expose
    public String url;
    @SerializedName("quarantine")
    @Expose
    public boolean quarantine;
    @SerializedName("title")
    @Expose
    public String title;
    @SerializedName("created_utc")
    @Expose
    public long createdUtc;
    @SerializedName("visited")
    @Expose
    public boolean visited;
    @SerializedName("ups")
    @Expose
    public long ups;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.domain);
        dest.writeParcelable(this.mediaEmbed, flags);
        dest.writeString(this.subreddit);
        dest.writeString(this.selftextHtml);
        dest.writeString(this.selftext);
        dest.writeString(this.suggestedSort);
        dest.writeList(this.userReports);
        dest.writeString(this.id);
        dest.writeLong(this.gilded);
        dest.writeByte(this.archived ? (byte) 1 : (byte) 0);
        dest.writeByte(this.clicked ? (byte) 1 : (byte) 0);
        dest.writeString(this.author);
        dest.writeLong(this.score);
        dest.writeByte(this.over18 ? (byte) 1 : (byte) 0);
        dest.writeByte(this.hidden ? (byte) 1 : (byte) 0);
        dest.writeLong(this.numComments);
        dest.writeString(this.thumbnail);
        dest.writeString(this.subredditId);
        dest.writeByte(this.hideScore ? (byte) 1 : (byte) 0);
        dest.writeLong(this.downs);
        dest.writeByte(this.saved ? (byte) 1 : (byte) 0);
        dest.writeByte(this.stickied ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isSelf ? (byte) 1 : (byte) 0);
        dest.writeString(this.permalink);
        dest.writeByte(this.locked ? (byte) 1 : (byte) 0);
        dest.writeString(this.name);
        dest.writeLong(this.created);
        dest.writeString(this.url);
        dest.writeByte(this.quarantine ? (byte) 1 : (byte) 0);
        dest.writeString(this.title);
        dest.writeLong(this.createdUtc);
        dest.writeByte(this.visited ? (byte) 1 : (byte) 0);
        dest.writeLong(this.ups);
    }

    public RedditItem() {
    }

    protected RedditItem(Parcel in) {
        this.domain = in.readString();
        this.mediaEmbed = in.readParcelable(MediaEmbed.class.getClassLoader());
        this.subreddit = in.readString();
        this.selftextHtml = in.readString();
        this.selftext = in.readString();
        this.suggestedSort = in.readString();
        this.userReports = new ArrayList<Object>();
        in.readList(this.userReports, Object.class.getClassLoader());
        this.id = in.readString();
        this.gilded = in.readLong();
        this.archived = in.readByte() != 0;
        this.clicked = in.readByte() != 0;
        this.author = in.readString();
        this.score = in.readLong();
        this.over18 = in.readByte() != 0;
        this.hidden = in.readByte() != 0;
        this.numComments = in.readLong();
        this.thumbnail = in.readString();
        this.subredditId = in.readString();
        this.hideScore = in.readByte() != 0;
        this.downs = in.readLong();
        this.saved = in.readByte() != 0;
        this.stickied = in.readByte() != 0;
        this.isSelf = in.readByte() != 0;
        this.permalink = in.readString();
        this.locked = in.readByte() != 0;
        this.name = in.readString();
        this.created = in.readLong();
        this.url = in.readString();
        this.quarantine = in.readByte() != 0;
        this.title = in.readString();
        this.createdUtc = in.readLong();
        this.visited = in.readByte() != 0;
        this.ups = in.readLong();
    }

    public static final Creator<RedditItem> CREATOR = new Creator<RedditItem>() {
        @Override
        public RedditItem createFromParcel(Parcel source) {
            return new RedditItem(source);
        }

        @Override
        public RedditItem[] newArray(int size) {
            return new RedditItem[size];
        }
    };
}
