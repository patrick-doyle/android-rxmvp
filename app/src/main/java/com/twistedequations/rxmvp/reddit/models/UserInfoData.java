package com.twistedequations.rxmvp.reddit.models;

import android.os.Parcel;
import android.os.Parcelable;

public class UserInfoData implements Parcelable {

    public String kind;

    public UserInfo data;

    protected UserInfoData(Parcel in) {
        kind = in.readString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserInfoData that = (UserInfoData) o;

        if (kind != null ? !kind.equals(that.kind) : that.kind != null) return false;
        return data != null ? data.equals(that.data) : that.data == null;

    }

    @Override
    public int hashCode() {
        int result = kind != null ? kind.hashCode() : 0;
        result = 31 * result + (data != null ? data.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserInfoData{" +
                "kind='" + kind + '\'' +
                ", data=" + data +
                '}';
    }

    public static final Creator<UserInfoData> CREATOR = new Creator<UserInfoData>() {
        @Override
        public UserInfoData createFromParcel(Parcel in) {
            return new UserInfoData(in);
        }

        @Override
        public UserInfoData[] newArray(int size) {
            return new UserInfoData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(kind);
    }
}
