package com.test2.wilfriedmaris.sipme2.activities;

import android.content.Intent;
import android.os.Bundle;

import com.test2.wilfriedmaris.sipme2.fragments.CocktailDetailFragment;
import com.test2.wilfriedmaris.sipme2.R;
import com.test2.wilfriedmaris.sipme2.entities.Cocktail;
import com.test2.wilfriedmaris.sipme2.utilities.ApplicationHelper;

public class CocktailDetailActivity extends BaseActivity implements UpdateItemInterface {
    public final static String COCKTAIL_KEY = "cocktail";
    public final static String SIDE_KEY = "side";
    public final static String FRAGMENT_TAG = "detail_fragment";

    private Cocktail mItem;
    private String mSide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cocktail_detail);

        mItem = getIntent().getParcelableExtra(COCKTAIL_KEY);
        mSide = getIntent().getStringExtra(SIDE_KEY);

        ApplicationHelper.setUpToolbar(this, R.id.detail_toolbar);

        if (getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG) == null){
            createFragment();
        }

        logd("onCreate");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(COCKTAIL_KEY, mItem);
        outState.putString(SIDE_KEY, mSide);

        logd("onSaveInstanceState");
    }

    @Override
    public void updateItem(float rating, int cocktailId) {
        Intent resultIntent = new Intent();
        resultIntent.putExtra(UpdateItemInterface.RATING_KEY, rating);
        resultIntent.putExtra(UpdateItemInterface.ID_KEY, cocktailId);
        setResult(UpdateItemInterface.RESULT_CODE, resultIntent);

        logd("Rating passed");
    }

    private void createFragment(){
        CocktailDetailFragment fragment = new CocktailDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(fragment.COCKTAIL_KEY, mItem);
        bundle.putString(fragment.SIDE_KEY, mSide);
        fragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.cocktail_detail_container, fragment, FRAGMENT_TAG)
                .commit();

        logd("Fragment created");
    }
}
