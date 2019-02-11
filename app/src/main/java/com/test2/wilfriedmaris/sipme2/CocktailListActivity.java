package com.test2.wilfriedmaris.sipme2;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.test2.wilfriedmaris.sipme2.utilities.FeedReaderContract;
import com.test2.wilfriedmaris.sipme2.entities.Cocktail;
import com.test2.wilfriedmaris.sipme2.entities.Ingredient;
import com.test2.wilfriedmaris.sipme2.entities.Rating;
import com.test2.wilfriedmaris.sipme2.utilities.BaseActivity;
import com.test2.wilfriedmaris.sipme2.utilities.CocktailRepository;
import com.test2.wilfriedmaris.sipme2.utilities.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class CocktailListActivity extends BaseActivity {
    public static final String LIFECYCLE_CALLBACKS_TEXT_KEY = "callback";
    private boolean mTwoPane;
    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;
    private final String mFirebaseUrl = "cocktails/alcoholic";
    public String mSide;
    private CocktailRepository mCocktailRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cocktail_list);

        if(getIntent().getStringExtra("side") != null){
            mSide = getIntent().getStringExtra("side");
        }

        mProgressBar = findViewById(R.id.pb_loading_indicator);
        mProgressBar.setVisibility(View.VISIBLE);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        if (findViewById(R.id.cocktail_detail_container) != null) {
            mTwoPane = true;
        }

        mRecyclerView = findViewById(R.id.cocktail_list);
        assert mRecyclerView != null;

        if (mSide.equals("cocktails")){
            setUpCocktails();
        } else {
            setUpFavorites();
        }
    }

    private Rating convertCursorToRating(Cursor ratingCursor){
        Rating rating = null;
        try {
            if (ratingCursor.moveToNext()){
               rating = new Rating(
                       ratingCursor.getDouble(ratingCursor.getColumnIndex(
                               FeedReaderContract.FeedRating.COLUMN_NAME_AVERAGE)),
                       ratingCursor.getInt(ratingCursor.getColumnIndex(
                               FeedReaderContract.FeedRating.COLUMN_NAME_RATERS))
               );
            }
        } finally {
            ratingCursor.close();
        }
        return rating;
    }

    private List<Ingredient> convertCursorToIngredients(Cursor ingredientCursor){
        List<Ingredient> ingredients = new ArrayList<>();
        try {
            while (ingredientCursor.moveToNext()){
                Ingredient ingredient = new Ingredient(
                        ingredientCursor.getString(ingredientCursor.getColumnIndex(
                                FeedReaderContract.FeedIngredient.COLUMN_NAME_NAME)),
                        ingredientCursor.getString(ingredientCursor.getColumnIndex(
                                FeedReaderContract.FeedIngredient.COLUMN_NAME_MEASURE)),
                        ingredientCursor.getDouble(ingredientCursor.getColumnIndex(
                                FeedReaderContract.FeedIngredient.COLUMN_NAME_AMOUNT)));
                ingredients.add(ingredient);
            }
        } finally {
            ingredientCursor.close();
        }
        return ingredients;
    }

    private void setUpFavorites(){
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        mCocktailRepository = CocktailRepository.getInstance(dbHelper.getWritableDatabase());
        Cursor cocktailCursor = mCocktailRepository.getAll();
        List<Cocktail> cocktails = new ArrayList<>();

        try {
            while (cocktailCursor.moveToNext()) {
                Cursor ingredientCursor = mCocktailRepository.getAllIngredients(
                        cocktailCursor.getInt(cocktailCursor.getColumnIndex(
                                FeedReaderContract.FeedCocktail.COLUMN_NAME_ID)));
                Cursor ratingCursor = mCocktailRepository.getRating(
                        cocktailCursor.getInt(cocktailCursor.getColumnIndex(
                                FeedReaderContract.FeedCocktail.COLUMN_NAME_ID)));
                Rating rating = convertCursorToRating(ratingCursor);
                List<Ingredient> ingredients = convertCursorToIngredients(ingredientCursor);

                Cocktail cocktail = new Cocktail(
                        cocktailCursor.getInt(cocktailCursor.getColumnIndex(
                                FeedReaderContract.FeedCocktail.COLUMN_NAME_ID)),
                        cocktailCursor.getString(cocktailCursor.getColumnIndex(
                                FeedReaderContract.FeedCocktail.COLUMN_NAME_NAME)),
                        cocktailCursor.getString(cocktailCursor.getColumnIndex(
                                FeedReaderContract.FeedCocktail.COLUMN_NAME_PICTURE)),
                        cocktailCursor.getString(cocktailCursor.getColumnIndex(
                                FeedReaderContract.FeedCocktail.COLUMN_NAME_RECIPE)),
                        ingredients,
                        rating
                );

                cocktails.add(cocktail);
            }
        } finally {
            cocktailCursor.close();
        }

        mProgressBar.setVisibility(View.INVISIBLE);

        mRecyclerView.setAdapter(new CocktailRecyclerViewAdapter(
                CocktailListActivity.this, cocktails, mTwoPane));

        Log.d(CocktailListActivity.this.getClass().getSimpleName(),
                "Cocktails fetched");
    }

    private void setUpCocktails(){
        FirebaseDatabase mFirebase = FirebaseDatabase.getInstance();
        DatabaseReference mFirebaseRef = mFirebase.getReference(mFirebaseUrl);

        mFirebaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Cocktail> cocktails = new ArrayList<>();


                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    Cocktail cocktail = ds.getValue(Cocktail.class);
                    cocktail.id = Integer.parseInt(ds.getKey());
                    cocktail.sortIngredients();
                    cocktails.add(cocktail);
                }

                mProgressBar.setVisibility(View.INVISIBLE);

                mRecyclerView.setAdapter(new CocktailRecyclerViewAdapter(
                        CocktailListActivity.this, cocktails, mTwoPane));

                Log.d(CocktailListActivity.this.getClass().getSimpleName(),
                        "Cocktails fetched");
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.e(CocktailListActivity.this.getClass().getSimpleName(),
                        "Failed to read value.", error.toException());
            }
        });
    }
}
