package com.test2.wilfriedmaris.sipme2.utilities;

import android.provider.BaseColumns;

public final class FeedReaderContract {
    private FeedReaderContract(){}

    public static class FeedRating implements BaseColumns{
        public static final String TABLE_NAME = "ratings";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_AVERAGE = "average";
        public static final String COLUMN_NAME_RATERS = "raters";
        public static final String COLUMN_NAME_COCKTAIL_ID = "cocktail_id";
    }

    public static class FeedIngredient implements BaseColumns{
        public static final String TABLE_NAME = "ingredients";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_AMOUNT = "amount";
        public static final String COLUMN_NAME_MEASURE = "measure";
        public static final String COLUMN_NAME_COCKTAIL_ID = "cocktail_id";
    }

    public static class FeedCocktail implements BaseColumns{
        public static final String TABLE_NAME = "cocktails";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_PICTURE = "picture";
        public static final String COLUMN_NAME_RECIPE = "recipe";
    }
}
