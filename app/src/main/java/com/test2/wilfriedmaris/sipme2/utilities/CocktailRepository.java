package com.test2.wilfriedmaris.sipme2.utilities;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.test2.wilfriedmaris.sipme2.entities.Cocktail;
import com.test2.wilfriedmaris.sipme2.entities.Ingredient;

public class CocktailRepository {
    private static final CocktailRepository ourInstance = new CocktailRepository();
    private static SQLiteDatabase mDb;

    public static CocktailRepository getInstance(SQLiteDatabase db) {
        mDb = db;
        return ourInstance;
    }

    private CocktailRepository() {}

    public Cursor getSingle(int id){
        return mDb.query(FeedReaderContract.FeedCocktail.TABLE_NAME,
                null,
                FeedReaderContract.FeedCocktail.COLUMN_NAME_ID + " = " + id,
                null,
                null,
                null,
                null);
    }

    public Cursor getAll(){
        return mDb.query(FeedReaderContract.FeedCocktail.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null);
    }

    public Cursor getAllIngredients(int id){
        return mDb.query(FeedReaderContract.FeedIngredient.TABLE_NAME,
                null,
                FeedReaderContract.FeedIngredient.COLUMN_NAME_COCKTAIL_ID + " = " + id,
                null,
                null,
                null,
                null);
    }

    public Cursor getRating(int id){
        return mDb.query(FeedReaderContract.FeedRating.TABLE_NAME,
                null,
                FeedReaderContract.FeedRating.COLUMN_NAME_COCKTAIL_ID + " = " + id,
                null,
                null,
                null,
                null);
    }

    public void remove(int id){
        mDb.delete(FeedReaderContract.FeedCocktail.TABLE_NAME,
                FeedReaderContract.FeedCocktail.COLUMN_NAME_ID + " =" +
                        id, null);
        mDb.delete(FeedReaderContract.FeedRating.TABLE_NAME,
                FeedReaderContract.FeedRating.COLUMN_NAME_COCKTAIL_ID + " =" +
                        id, null);
        mDb.delete(FeedReaderContract.FeedIngredient.TABLE_NAME,
                FeedReaderContract.FeedIngredient.COLUMN_NAME_COCKTAIL_ID + " =" +
                        id, null);
    }

    public void add(Cocktail cocktail){
        ContentValues cv = new ContentValues();
        cv.put(FeedReaderContract.FeedCocktail.COLUMN_NAME_ID, cocktail.id);
        cv.put(FeedReaderContract.FeedCocktail.COLUMN_NAME_NAME, cocktail.name);
        cv.put(FeedReaderContract.FeedCocktail.COLUMN_NAME_PICTURE, cocktail.picture);
        cv.put(FeedReaderContract.FeedCocktail.COLUMN_NAME_RECIPE, cocktail.recipe);
        mDb.insert(FeedReaderContract.FeedCocktail.TABLE_NAME, null, cv);

        ContentValues cv2 = new ContentValues();
        cv2.put(FeedReaderContract.FeedRating.COLUMN_NAME_AVERAGE, cocktail.rating.average);
        cv2.put(FeedReaderContract.FeedRating.COLUMN_NAME_RATERS, cocktail.rating.raters);
        cv2.put(FeedReaderContract.FeedRating.COLUMN_NAME_COCKTAIL_ID, cocktail.id);
        mDb.insert(FeedReaderContract.FeedRating.TABLE_NAME, null, cv2);

        for (Ingredient ing : cocktail.ingredients){
            ContentValues cv3 = new ContentValues();
            cv3.put(FeedReaderContract.FeedIngredient.COLUMN_NAME_NAME, ing.name);
            cv3.put(FeedReaderContract.FeedIngredient.COLUMN_NAME_AMOUNT, ing.amount);
            cv3.put(FeedReaderContract.FeedIngredient.COLUMN_NAME_MEASURE, ing.measure);
            cv3.put(FeedReaderContract.FeedIngredient.COLUMN_NAME_COCKTAIL_ID, cocktail.id);
            mDb.insert(FeedReaderContract.FeedIngredient.TABLE_NAME, null, cv3);
        }
    }
}
