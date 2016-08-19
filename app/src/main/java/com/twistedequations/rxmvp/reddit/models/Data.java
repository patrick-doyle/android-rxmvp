package com.twistedequations.rxmvp.reddit.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Data implements Parcelable {

    @SerializedName("modhash")
    @Expose
    public String modhash;
    @SerializedName("children")
    @Expose
    public List<Child> children = new ArrayList<Child>();

    @Override
    public String toString() {
        return "Data{" +
                "modhash='" + modhash + '\'' +
                ", children=" + children +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Data data = (Data) o;

        if (modhash != null ? !modhash.equals(data.modhash) : data.modhash != null) return false;
        if (children != null ? !children.equals(data.children) : data.children != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = modhash != null ? modhash.hashCode() : 0;
        result = 31 * result + (children != null ? children.hashCode() : 0);
        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.modhash);
        dest.writeTypedList(this.children);
    }

    public Data() {
    }

    protected Data(Parcel in) {
        this.modhash = in.readString();
        this.children = in.createTypedArrayList(Child.CREATOR);
    }

    public static final Creator<Data> CREATOR = new Creator<Data>() {
        @Override
        public Data createFromParcel(Parcel source) {
            return new Data(source);
        }

        @Override
        public Data[] newArray(int size) {
            return new Data[size];
        }
    };
}
