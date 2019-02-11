package com.test2.wilfriedmaris.sipme2;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.test2.wilfriedmaris.sipme2.entities.Cocktail;
import com.test2.wilfriedmaris.sipme2.entities.Ingredient;

public class CocktailDetailFragment extends Fragment {
    public static final String LIFECYCLE_CALLBACKS_TEXT_KEY = "callback";
    private Cocktail mItem;

    public CocktailDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null){
            if (savedInstanceState.containsKey(LIFECYCLE_CALLBACKS_TEXT_KEY)){
                mItem = savedInstanceState.getParcelable(LIFECYCLE_CALLBACKS_TEXT_KEY);
            }
        }

        if (getArguments().containsKey(LIFECYCLE_CALLBACKS_TEXT_KEY)) {
            mItem = getArguments().getParcelable(LIFECYCLE_CALLBACKS_TEXT_KEY);
            //only on the intent of the detail not the sole fragment
        }

        if (mItem != null){
            Activity activity = CocktailDetailFragment.this.getActivity();
            CollapsingToolbarLayout appBarLayout = activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mItem.name);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mItem != null){
            Activity activity = CocktailDetailFragment.this.getActivity();
            CollapsingToolbarLayout appBarLayout = activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mItem.name);
            }
        }

        View rootView = inflater.inflate(R.layout.cocktail_detail, container, false);

        if (mItem != null) {
            ImageView imgView = rootView.findViewById(R.id.header_img);
            Picasso.get().load(mItem.picture).into(imgView);

            if (rootView.findViewById(R.id.title_text) != null) {
                ((TextView) rootView.findViewById(R.id.title_text)).setText(mItem.name);
            }

            LinearLayout ingLayout = rootView.findViewById(R.id.ingredient_scroll);
            LinearLayout resLayout = rootView.findViewById(R.id.recipe_scroll);
            TextView resTextView = new TextView(this.getContext());
            resTextView.setTextColor(getResources().getColor(R.color.white));
            resTextView.setText(mItem.recipe);
            resTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
            resLayout.addView(resTextView);

            for (Ingredient ing : mItem.ingredients){
                ingLayout.addView(this.createIngredientView(ing));
            }
        }

        return rootView;
    }

    private LinearLayout createIngredientView(Ingredient ingredient){
        LinearLayout linearLayout = new LinearLayout(this.getContext());
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);

        TextView textView = new TextView(this.getContext());
        textView.setText(ingredient.name);
        textView.setTextColor(getResources().getColor(R.color.white));
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
        textView.setWidth(400);
        linearLayout.addView(textView);

        TextView textView2 = new TextView(this.getContext());
        textView2.setTextColor(getResources().getColor(R.color.white));
        textView2.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);

        if (ingredient.amount > 0){
            String amountMeasure = ingredient.amount + " " + ingredient.measure;
            textView2.setText(amountMeasure);
        }

        linearLayout.addView(textView2);

        return linearLayout;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(LIFECYCLE_CALLBACKS_TEXT_KEY, mItem);
    }
}
