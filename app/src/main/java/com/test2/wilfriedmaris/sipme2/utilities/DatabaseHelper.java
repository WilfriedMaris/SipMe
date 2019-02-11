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
        final String SQL_STATEMENT_1 = "CREATE TABLE " + FeedReaderContract.FeedCocktail.TABLE_NAME + " (" +
                FeedReaderContract.FeedCocktail.COLUMN_NAME_ID + " INTEGER PRIMARY KEY, " +
                FeedReaderContract.FeedCocktail.COLUMN_NAME_NAME + " TEXT NOT NULL, " +
                FeedReaderContract.FeedCocktail.COLUMN_NAME_PICTURE + " TEXT NOT NULL, " +
                FeedReaderContract.FeedCocktail.COLUMN_NAME_RECIPE + " TEXT NOT NULL" +
                "); ";

        final String SQL_STATEMENT_2 = "CREATE TABLE " + FeedReaderContract.FeedIngredient.TABLE_NAME + " (" +
                FeedReaderContract.FeedIngredient.COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                FeedReaderContract.FeedIngredient.COLUMN_NAME_NAME + " TEXT NOT NULL, " +
                FeedReaderContract.FeedIngredient.COLUMN_NAME_AMOUNT + " REAL, " +
                FeedReaderContract.FeedIngredient.COLUMN_NAME_MEASURE + " TEXT, " +
                FeedReaderContract.FeedIngredient.COLUMN_NAME_COCKTAIL_ID + " INTEGER NOT NULL, " +
                "FOREIGN KEY(" + FeedReaderContract.FeedIngredient.COLUMN_NAME_COCKTAIL_ID +
                ") REFERENCES " + FeedReaderContract.FeedCocktail.TABLE_NAME +
                "(" + FeedReaderContract.FeedCocktail.COLUMN_NAME_ID + ")" +
                "); ";

        final String SQL_STATEMENT_3 = "CREATE TABLE " + FeedReaderContract.FeedRating.TABLE_NAME + " (" +
                FeedReaderContract.FeedRating.COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                FeedReaderContract.FeedRating.COLUMN_NAME_AVERAGE + " REAL NOT NULL, " +
                FeedReaderContract.FeedRating.COLUMN_NAME_RATERS + " INTEGER NOT NULL, " +
                FeedReaderContract.FeedRating.COLUMN_NAME_COCKTAIL_ID + " INTEGER NOT NULL, " +
                "FOREIGN KEY(" + FeedReaderContract.FeedRating.COLUMN_NAME_COCKTAIL_ID +
                ") REFERENCES " + FeedReaderContract.FeedCocktail.TABLE_NAME +
                "(" + FeedReaderContract.FeedCocktail.COLUMN_NAME_ID + ")" +
                "); ";

        db.execSQL(SQL_STATEMENT_1);
        db.execSQL(SQL_STATEMENT_2);
        db.execSQL(SQL_STATEMENT_3);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + FeedReaderContract.FeedCocktail.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + FeedReaderContract.FeedIngredient.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + FeedReaderContract.FeedRating.TABLE_NAME);
        onCreate(db);
    }
}
