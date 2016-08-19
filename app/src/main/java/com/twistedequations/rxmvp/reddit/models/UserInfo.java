package com.twistedequations.rxmvp.reddit.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class UserInfo implements Parcelable {

    public String name;

    public String id;

    public long created;

    @SerializedName("created_utc")
    public long createdUtc;

    @SerializedName("link_karma")
    public long linkKarma;

    @SerializedName("comment_karma")
    public long commentKarma;

    @SerializedName("is_gold")
    public boolean isGold;

    @SerializedName("is_friend")
    public boolean isFriend;

    @SerializedName("is_mod")
    public boolean isMod;

    @SerializedName("has_verified_email")
    public boolean hasVerifiedEmail;

    @SerializedName("hide_from_robots")
    public boolean hideFromRobots;

    @Override
    public String toString() {
        return "UserInfo{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", created=" + created +
                ", createdUtc=" + createdUtc +
                ", linkKarma=" + linkKarma +
                ", commentKarma=" + commentKarma +
                ", isGold=" + isGold +
                ", isMod=" + isMod +
                ", hasVerifiedEmail=" + hasVerifiedEmail +
                ", hideFromRobots=" + hideFromRobots +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserInfo userInfo = (UserInfo) o;

        if (created != userInfo.created) return false;
        if (createdUtc != userInfo.createdUtc) return false;
        if (linkKarma != userInfo.linkKarma) return false;
        if (commentKarma != userInfo.commentKarma) return false;
        if (isGold != userInfo.isGold) return false;
        if (isMod != userInfo.isMod) return false;
        if (hasVerifiedEmail != userInfo.hasVerifiedEmail) return false;
        if (hideFromRobots != userInfo.hideFromRobots) return false;
        if (name != null ? !name.equals(userInfo.name) : userInfo.name != null) return false;
        return id != null ? id.equals(userInfo.id) : userInfo.id == null;

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (int) (created ^ (created >>> 32));
        result = 31 * result + (int) (createdUtc ^ (createdUtc >>> 32));
        result = 31 * result + (int) (linkKarma ^ (linkKarma >>> 32));
        result = 31 * result + (int) (commentKarma ^ (commentKarma >>> 32));
        result = 31 * result + (isGold ? 1 : 0);
        result = 31 * result + (isMod ? 1 : 0);
        result = 31 * result + (hasVerifiedEmail ? 1 : 0);
        result = 31 * result + (hideFromRobots ? 1 : 0);
        return result;
    }

    protected UserInfo(Parcel in) {
        name = in.readString();
        id = in.readString();
        created = in.readLong();
        createdUtc = in.readLong();
        linkKarma = in.readLong();
        commentKarma = in.readLong();
        isGold = in.readByte() != 0;
        isMod = in.readByte() != 0;
        hasVerifiedEmail = in.readByte() != 0;
        hideFromRobots = in.readByte() != 0;
    }

    public static final Creator<UserInfo> CREATOR = new Creator<UserInfo>() {
        @Override
        public UserInfo createFromParcel(Parcel in) {
            return new UserInfo(in);
        }

        @Override
        public UserInfo[] newArray(int size) {
            return new UserInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(id);
        parcel.writeLong(created);
        parcel.writeLong(createdUtc);
        parcel.writeLong(linkKarma);
        parcel.writeLong(commentKarma);
        parcel.writeByte((byte) (isGold ? 1 : 0));
        parcel.writeByte((byte) (isMod ? 1 : 0));
        parcel.writeByte((byte) (hasVerifiedEmail ? 1 : 0));
        parcel.writeByte((byte) (hideFromRobots ? 1 : 0));
    }
}
