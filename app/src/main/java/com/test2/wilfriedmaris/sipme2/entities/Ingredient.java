package com.test2.wilfriedmaris.sipme2.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Ingredient implements Serializable, Parcelable {
    public String name;
    public String measure;
    public double amount;

    public Ingredient(){}

    public Ingredient(String name, String measure, double amount){
        this.name = name;
        this.measure = measure;
        this.amount = amount;
    }

    protected Ingredient(Parcel in) {
        name = in.readString();
        measure = in.readString();
        amount = in.readDouble();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(measure);
        dest.writeDouble(amount);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Ingredient> CREATOR = new Parcelable.Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel in) {
            return new Ingredient(in);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };
}
