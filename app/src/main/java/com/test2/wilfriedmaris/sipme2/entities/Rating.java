package com.test2.wilfriedmaris.sipme2.entities;

import android.os.Parcel;
import android.os.Parcelable;

public class Rating implements Parcelable {
    public double average;
    public int raters;

    public Rating(){}

    public Rating(double average, int raters){
        this.average = average;
        this.raters = raters;
    }

    protected Rating(Parcel in) {
        average = in.readDouble();
        raters = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(average);
        dest.writeInt(raters);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Rating> CREATOR = new Parcelable.Creator<Rating>() {
        @Override
        public Rating createFromParcel(Parcel in) {
            return new Rating(in);
        }

        @Override
        public Rating[] newArray(int size) {
            return new Rating[size];
        }
    };
}
