package com.test2.wilfriedmaris.sipme2.utilities;

import android.provider.BaseColumns;

public final class CocktailContract {
    private CocktailContract(){}

    public static class RatingEntry implements BaseColumns{
        public static final String TABLE_NAME = "ratings";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_AVERAGE = "average";
        public static final String COLUMN_NAME_RATERS = "raters";
        public static final String COLUMN_NAME_COCKTAIL_ID = "cocktail_id";
    }

    public static class IngredientEntry implements BaseColumns{
        public static final String TABLE_NAME = "ingredients";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_AMOUNT = "amount";
        public static final String COLUMN_NAME_MEASURE = "measure";
        public static final String COLUMN_NAME_COCKTAIL_ID = "cocktail_id";
    }

    public static class CocktailEntry implements BaseColumns{
        public static final String TABLE_NAME = "cocktails";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_PICTURE = "picture";
        public static final String COLUMN_NAME_RECIPE = "recipe";
    }
}
