package com.test2.wilfriedmaris.sipme2.utilities;

import android.database.Cursor;

import com.test2.wilfriedmaris.sipme2.entities.Cocktail;
import com.test2.wilfriedmaris.sipme2.entities.Ingredient;
import com.test2.wilfriedmaris.sipme2.entities.Rating;

import java.util.ArrayList;
import java.util.List;

public class CursorConverter {
    private static final CursorConverter ourInstance = new CursorConverter();

    public static CursorConverter getInstance() {
        return ourInstance;
    }

    private CursorConverter() {
    }

    public static List<Cocktail> convertCocktailCursor(Cursor cocktailCursor,
                                                       CocktailRepository cocktailRepository){
        List<Cocktail> cocktails = new ArrayList<>();

        try {
            while (cocktailCursor.moveToNext()) {
                Cursor ingredientCursor = cocktailRepository.getAllIngredients(
                        cocktailCursor.getInt(cocktailCursor.getColumnIndex(
                                CocktailContract.CocktailEntry.COLUMN_NAME_ID)));
                Cursor ratingCursor = cocktailRepository.getRating(
                        cocktailCursor.getInt(cocktailCursor.getColumnIndex(
                                CocktailContract.CocktailEntry.COLUMN_NAME_ID)));
                Rating rating = convertRatingCursor(ratingCursor);
                List<Ingredient> ingredients = convertIngredientsCursor(ingredientCursor);

                Cocktail cocktail = new Cocktail(
                        cocktailCursor.getInt(cocktailCursor.getColumnIndex(
                                CocktailContract.CocktailEntry.COLUMN_NAME_ID)),
                        cocktailCursor.getString(cocktailCursor.getColumnIndex(
                                CocktailContract.CocktailEntry.COLUMN_NAME_NAME)),
                        cocktailCursor.getString(cocktailCursor.getColumnIndex(
                                CocktailContract.CocktailEntry.COLUMN_NAME_PICTURE)),
                        cocktailCursor.getString(cocktailCursor.getColumnIndex(
                                CocktailContract.CocktailEntry.COLUMN_NAME_RECIPE)),
                        ingredients,
                        rating
                );

                cocktails.add(cocktail);
            }
        } finally {
            cocktailCursor.close();
        }

        return cocktails;
    }

    public static Rating convertRatingCursor(Cursor ratingCursor){
        Rating rating = null;
        try {
            if (ratingCursor.moveToNext()){
                rating = new Rating(
                        ratingCursor.getDouble(ratingCursor.getColumnIndex(
                                CocktailContract.RatingEntry.COLUMN_NAME_AVERAGE)),
                        ratingCursor.getInt(ratingCursor.getColumnIndex(
                                CocktailContract.RatingEntry.COLUMN_NAME_RATERS))
                );
            }
        } finally {
            ratingCursor.close();
        }
        return rating;
    }

    public static List<Ingredient> convertIngredientsCursor(Cursor ingredientCursor){
        List<Ingredient> ingredients = new ArrayList<>();
        try {
            while (ingredientCursor.moveToNext()){
                Ingredient ingredient = new Ingredient(
                        ingredientCursor.getString(ingredientCursor.getColumnIndex(
                                CocktailContract.IngredientEntry.COLUMN_NAME_NAME)),
                        ingredientCursor.getString(ingredientCursor.getColumnIndex(
                                CocktailContract.IngredientEntry.COLUMN_NAME_MEASURE)),
                        ingredientCursor.getDouble(ingredientCursor.getColumnIndex(
                                CocktailContract.IngredientEntry.COLUMN_NAME_AMOUNT)));
                ingredients.add(ingredient);
            }
        } finally {
            ingredientCursor.close();
        }
        return ingredients;
    }
}
