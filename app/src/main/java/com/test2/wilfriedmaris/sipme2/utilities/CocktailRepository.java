package com.test2.wilfriedmaris.sipme2.utilities;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.test2.wilfriedmaris.sipme2.entities.Cocktail;
import com.test2.wilfriedmaris.sipme2.entities.Ingredient;

public class CocktailRepository {
    private static final CocktailRepository sInstance = new CocktailRepository();
    private static SQLiteDatabase sDatabase;

    public static CocktailRepository getInstance(SQLiteDatabase db) {
        sDatabase = db;
        return sInstance;
    }

    private CocktailRepository() {}

    public Cursor getSingle(int id){
        String selection = CocktailContract.CocktailEntry.COLUMN_NAME_ID + " = " + id;
        return sDatabase.query(CocktailContract.CocktailEntry.TABLE_NAME,
                null, selection,null,null,null,null);
    }

    public Cursor getAll(){
        return sDatabase.query(CocktailContract.CocktailEntry.TABLE_NAME,
                null,null,null,null,null,null);
    }

    public Cursor getAllIngredients(int id){
        String selection = CocktailContract.IngredientEntry.COLUMN_NAME_COCKTAIL_ID + " = " + id;
        return sDatabase.query(CocktailContract.IngredientEntry.TABLE_NAME,
                null, selection,null,null,null,null);
    }

    public Cursor getRating(int id){
        String selection = CocktailContract.RatingEntry.COLUMN_NAME_COCKTAIL_ID + " = " + id;
        return sDatabase.query(CocktailContract.RatingEntry.TABLE_NAME,
                null, selection,null,null,null,null);
    }

    public void remove(int id){
        sDatabase.delete(CocktailContract.CocktailEntry.TABLE_NAME,
                CocktailContract.CocktailEntry.COLUMN_NAME_ID + " =" +
                        id, null);
        sDatabase.delete(CocktailContract.RatingEntry.TABLE_NAME,
                CocktailContract.RatingEntry.COLUMN_NAME_COCKTAIL_ID + " =" +
                        id, null);
        sDatabase.delete(CocktailContract.IngredientEntry.TABLE_NAME,
                CocktailContract.IngredientEntry.COLUMN_NAME_COCKTAIL_ID + " =" +
                        id, null);
    }

    public Cursor updateRating(int cocktailId, float givenRating){
        String where = "cocktail_id" + "=" + cocktailId;
        ContentValues cv = new ContentValues();
        cv.put(CocktailContract.RatingEntry.COLUMN_NAME_AVERAGE, givenRating);
        sDatabase.update(CocktailContract.RatingEntry.TABLE_NAME, cv, where, null);
        return getRating(cocktailId);
    }

    public void add(Cocktail cocktail){
        ContentValues cv = new ContentValues();
        cv.put(CocktailContract.CocktailEntry.COLUMN_NAME_ID, cocktail.id);
        cv.put(CocktailContract.CocktailEntry.COLUMN_NAME_NAME, cocktail.name);
        cv.put(CocktailContract.CocktailEntry.COLUMN_NAME_PICTURE, cocktail.picture);
        cv.put(CocktailContract.CocktailEntry.COLUMN_NAME_RECIPE, cocktail.recipe);
        sDatabase.insert(CocktailContract.CocktailEntry.TABLE_NAME, null, cv);

        ContentValues cv2 = new ContentValues();
        cv2.put(CocktailContract.RatingEntry.COLUMN_NAME_AVERAGE, cocktail.rating.average);
        cv2.put(CocktailContract.RatingEntry.COLUMN_NAME_RATERS, cocktail.rating.raters);
        cv2.put(CocktailContract.RatingEntry.COLUMN_NAME_COCKTAIL_ID, cocktail.id);
        sDatabase.insert(CocktailContract.RatingEntry.TABLE_NAME, null, cv2);

        for (Ingredient ing : cocktail.ingredients){
            ContentValues cv3 = new ContentValues();
            cv3.put(CocktailContract.IngredientEntry.COLUMN_NAME_NAME, ing.name);
            cv3.put(CocktailContract.IngredientEntry.COLUMN_NAME_AMOUNT, ing.amount);
            cv3.put(CocktailContract.IngredientEntry.COLUMN_NAME_MEASURE, ing.measure);
            cv3.put(CocktailContract.IngredientEntry.COLUMN_NAME_COCKTAIL_ID, cocktail.id);
            sDatabase.insert(CocktailContract.IngredientEntry.TABLE_NAME, null, cv3);
        }
    }
}
