package com.test2.wilfriedmaris.sipme2.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.test2.wilfriedmaris.sipme2.R;
import com.test2.wilfriedmaris.sipme2.activities.CocktailListActivity;
import com.test2.wilfriedmaris.sipme2.entities.Cocktail;
import com.test2.wilfriedmaris.sipme2.entities.Ingredient;
import com.test2.wilfriedmaris.sipme2.utilities.CocktailRepository;
import com.test2.wilfriedmaris.sipme2.utilities.DatabaseHelper;
import com.test2.wilfriedmaris.sipme2.activities.UpdateItemInterface;

public class CocktailDetailFragment extends BaseFragment {
    public final static String COCKTAIL_KEY = "cocktail";
    public final static String SIDE_KEY = "side";

    private Cocktail mItem;
    private String mSide;

    public CocktailDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null){
            if (savedInstanceState.containsKey(COCKTAIL_KEY)){
                mItem = savedInstanceState.getParcelable(COCKTAIL_KEY);
                mSide = savedInstanceState.getString(SIDE_KEY);
            }
        } else if (getArguments().containsKey(COCKTAIL_KEY)) {
            //only on intent of detail
            mItem = getArguments().getParcelable(COCKTAIL_KEY);
            mSide = getArguments().getString(SIDE_KEY);
        }

        log("onCreate");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(COCKTAIL_KEY, mItem);
        outState.putString(SIDE_KEY, mSide);

        log("onSaveInstanceState");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Activity parent = CocktailDetailFragment.this.getActivity();
        CollapsingToolbarLayout appBarLayout = parent.findViewById(R.id.toolbar_layout);

        View rootView = inflater.inflate(R.layout.cocktail_detail, container, false);
        LinearLayout ingredientLinearLayout = rootView.findViewById(R.id.ingredient_scroll);
        TextView recipeTextView = rootView.findViewById(R.id.recipe_text);
        ImageView imageView = rootView.findViewById(R.id.header_img);
        TextView titleTextView = rootView.findViewById(R.id.title_text);

        Picasso.get().load(mItem.picture).into(imageView);
        recipeTextView.setText(mItem.recipe);
        for (Ingredient ing : mItem.ingredients){
            ingredientLinearLayout.addView(this.createIngredientView(ing));
        }

        if (titleTextView != null) {
            //only on tablets
            titleTextView.setText(mItem.name);
        }

        if (appBarLayout != null) {
            //only on phones
            appBarLayout.setTitle(mItem.name);
        }

        if (mSide.equals(CocktailListActivity.FAVORITES_SIDE)){
            //only on favorites side
            setRatingBar(rootView);
        }

        log("onCreateView");

        return rootView;
    }

    private LinearLayout createIngredientView(Ingredient ingredient){
        LinearLayout linearLayout = new LinearLayout(this.getContext());
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);

        TextView nameTextView = new TextView(this.getContext());
        nameTextView.setText(ingredient.name);
        nameTextView.setTextColor(getResources().getColor(R.color.white));
        nameTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
        nameTextView.setWidth(400);
        linearLayout.addView(nameTextView);

        TextView amountTextView = new TextView(this.getContext());
        amountTextView.setTextColor(getResources().getColor(R.color.white));
        amountTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
        amountTextView.setText(ingredient.amountDisplayValue());

        linearLayout.addView(amountTextView);

        return linearLayout;
    }

    private void setRatingBar(View rootView){
        RatingBar ratingBar = rootView.findViewById(R.id.rating_bar);
        ratingBar.setRating((float)mItem.rating.average);
        ratingBar.setVisibility(View.VISIBLE);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {
                DatabaseHelper dbHelper = new DatabaseHelper(getActivity());
                CocktailRepository mCocktailRepository = CocktailRepository
                        .getInstance(dbHelper.getWritableDatabase());
                mCocktailRepository.updateRating(mItem.id, rating);
                UpdateItemInterface parent = (UpdateItemInterface) getActivity();
                parent.updateItem(rating, mItem.id);
            }
        });

        log("RatingBar is set");
    }
}
