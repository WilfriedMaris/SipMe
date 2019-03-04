package com.test2.wilfriedmaris.sipme2.activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.test2.wilfriedmaris.sipme2.utilities.CocktailRecyclerViewAdapter;
import com.test2.wilfriedmaris.sipme2.R;
import com.test2.wilfriedmaris.sipme2.utilities.CursorConverter;
import com.test2.wilfriedmaris.sipme2.entities.Cocktail;
import com.test2.wilfriedmaris.sipme2.utilities.CocktailRepository;
import com.test2.wilfriedmaris.sipme2.utilities.DatabaseHelper;
import com.test2.wilfriedmaris.sipme2.utilities.ApplicationHelper;

import java.util.ArrayList;
import java.util.List;

public class CocktailListActivity extends BaseActivity implements UpdateItemInterface {
    public final static String COCKTAIL_SIDE = "cocktail";
    public final static String FAVORITES_SIDE = "favorites";
    public final static String SIDE_KEY = "side";

    private static final String sFirebaseUrl = "cocktails/alcoholic";

    private boolean mTwoPane;
    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;
    private CocktailRecyclerViewAdapter mAdapter;

    public String side;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cocktail_list);

        mProgressBar = findViewById(R.id.pb_loading_indicator);
        mProgressBar.setVisibility(View.VISIBLE);

        if (findViewById(R.id.cocktail_detail_container) != null) {
            mTwoPane = true;
        }

        mRecyclerView = findViewById(R.id.cocktail_list);

        ApplicationHelper.setUpToolbar(this, R.id.toolbar);

        side = getIntent().getStringExtra(SIDE_KEY);
        if (side.equals(COCKTAIL_SIDE)){
            setUpCocktails();
        } else {
            setUpFavorites();
        }

        logd("onCreate");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        int defaultValue = 0;
        if (resultCode == UpdateItemInterface.RESULT_CODE && data != null){
            updateItem(data.getFloatExtra(UpdateItemInterface.RATING_KEY, defaultValue),
                    data.getIntExtra(UpdateItemInterface.ID_KEY, defaultValue));
        }
    }

    @Override
    public void updateItem(float rating, int cocktailId) {
        mAdapter.updateRating(rating, cocktailId);

        logd("Rating passed");
    }

    private void setUpFavorites(){
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        CocktailRepository cocktailRepository = CocktailRepository.getInstance(
                dbHelper.getWritableDatabase());
        Cursor cocktailCursor = cocktailRepository.getAll();
        List<Cocktail> cocktails = CursorConverter.convertCocktailCursor(
                cocktailCursor, cocktailRepository);

        mAdapter = new CocktailRecyclerViewAdapter(
                this, cocktails, mTwoPane);
        mRecyclerView.setAdapter(mAdapter);

        mProgressBar.setVisibility(View.INVISIBLE);

        logd("Favorites loaded");
    }

    private void setUpCocktails(){
        if (ApplicationHelper.isNetworkAvailable(this)){
            FirebaseDatabase firebase = FirebaseDatabase.getInstance();
            DatabaseReference firebaseRef = firebase.getReference(sFirebaseUrl);

            firebaseRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    List<Cocktail> cocktails = new ArrayList<>();

                    for (DataSnapshot ds : dataSnapshot.getChildren()){
                        Cocktail cocktail = ds.getValue(Cocktail.class);
                        cocktail.id = Integer.parseInt(ds.getKey());
                        cocktail.sortIngredients();
                        cocktails.add(cocktail);
                    }

                    mAdapter = new CocktailRecyclerViewAdapter(
                            CocktailListActivity.this, cocktails, mTwoPane);
                    mRecyclerView.setAdapter(mAdapter);

                    mProgressBar.setVisibility(View.INVISIBLE);

                    logd("Cocktails loaded");
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    Log.e(CocktailListActivity.this.getClass().getSimpleName(),
                            "Failed to read value.", error.toException());
                }
            });
        } else {
          loge("No network connection!");
          ApplicationHelper.shortToast(this,
                  "Er is helaas geen netwerk beschikbaar. " +
                          "U kan uw favorite cocktails nog steeds benaderen!");
          finish();
        }

    }
}
