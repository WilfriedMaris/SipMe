package com.test2.wilfriedmaris.sipme2.utilities;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "cocktails.db";

    private static final int DATABASE_VERSION = 3;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_STATEMENT_1 = "CREATE TABLE " + CocktailContract.CocktailEntry.TABLE_NAME + " (" +
                CocktailContract.CocktailEntry.COLUMN_NAME_ID + " INTEGER PRIMARY KEY, " +
                CocktailContract.CocktailEntry.COLUMN_NAME_NAME + " TEXT NOT NULL, " +
                CocktailContract.CocktailEntry.COLUMN_NAME_PICTURE + " TEXT NOT NULL, " +
                CocktailContract.CocktailEntry.COLUMN_NAME_RECIPE + " TEXT NOT NULL" +
                "); ";

        final String SQL_STATEMENT_2 = "CREATE TABLE " + CocktailContract.IngredientEntry.TABLE_NAME + " (" +
                CocktailContract.IngredientEntry.COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CocktailContract.IngredientEntry.COLUMN_NAME_NAME + " TEXT NOT NULL, " +
                CocktailContract.IngredientEntry.COLUMN_NAME_AMOUNT + " REAL, " +
                CocktailContract.IngredientEntry.COLUMN_NAME_MEASURE + " TEXT, " +
                CocktailContract.IngredientEntry.COLUMN_NAME_COCKTAIL_ID + " INTEGER NOT NULL, " +
                "FOREIGN KEY(" + CocktailContract.IngredientEntry.COLUMN_NAME_COCKTAIL_ID +
                ") REFERENCES " + CocktailContract.CocktailEntry.TABLE_NAME +
                "(" + CocktailContract.CocktailEntry.COLUMN_NAME_ID + ")" +
                "); ";

        final String SQL_STATEMENT_3 = "CREATE TABLE " + CocktailContract.RatingEntry.TABLE_NAME + " (" +
                CocktailContract.RatingEntry.COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CocktailContract.RatingEntry.COLUMN_NAME_AVERAGE + " REAL NOT NULL, " +
                CocktailContract.RatingEntry.COLUMN_NAME_RATERS + " INTEGER NOT NULL, " +
                CocktailContract.RatingEntry.COLUMN_NAME_COCKTAIL_ID + " INTEGER NOT NULL, " +
                "FOREIGN KEY(" + CocktailContract.RatingEntry.COLUMN_NAME_COCKTAIL_ID +
                ") REFERENCES " + CocktailContract.CocktailEntry.TABLE_NAME +
                "(" + CocktailContract.CocktailEntry.COLUMN_NAME_ID + ")" +
                "); ";

        db.execSQL(SQL_STATEMENT_1);
        db.execSQL(SQL_STATEMENT_2);
        db.execSQL(SQL_STATEMENT_3);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CocktailContract.CocktailEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + CocktailContract.IngredientEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + CocktailContract.RatingEntry.TABLE_NAME);
        onCreate(db);
    }
}
