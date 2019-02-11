package com.test2.wilfriedmaris.sipme2.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Cocktail implements Parcelable {
    public int id;
    public String name;
    public String picture;
    public String recipe;
    public String source;
    public List<Ingredient> ingredients;
    public Rating rating;

    public Cocktail(){}

    public Cocktail(int id, String name, String picture, String recipe,
                    List<Ingredient> ingredients, Rating rating){
        this.id = id;
        this.name = name;
        this.picture = picture;
        this.recipe = recipe;
        this.ingredients = ingredients;
        this.rating = rating;
        this.source = null;
    }

    public void sortIngredients(){
        Collections.sort(this.ingredients, new Comparator<Ingredient>() {
            @Override
            public int compare(Ingredient o1, Ingredient o2) {
                return Double.valueOf(o2.amount).compareTo(o1.amount);
            }
        });
    }

    public String totalIngredients(){
        String totalString = "";
        for (Ingredient ing : ingredients){
            totalString += ing.name + ", ";
        }
        int lastIndex = totalString.lastIndexOf(',');
        return totalString.trim().substring(0, lastIndex);
    }

    protected Cocktail(Parcel in) {
        id = in.readInt();
        name = in.readString();
        picture = in.readString();
        recipe = in.readString();
        source = in.readString();
        if (in.readByte() == 0x01) {
            ingredients = new ArrayList<Ingredient>();
            in.readList(ingredients, Ingredient.class.getClassLoader());
        } else {
            ingredients = null;
        }
        rating = (Rating) in.readValue(Rating.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(picture);
        dest.writeString(recipe);
        dest.writeString(source);
        if (ingredients == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(ingredients);
        }
        dest.writeValue(rating);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Cocktail> CREATOR = new Parcelable.Creator<Cocktail>() {
        @Override
        public Cocktail createFromParcel(Parcel in) {
            return new Cocktail(in);
        }

        @Override
        public Cocktail[] newArray(int size) {
            return new Cocktail[size];
        }
    };
}