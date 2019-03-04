package com.test2.wilfriedmaris.sipme2.utilities;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;
import com.test2.wilfriedmaris.sipme2.R;
import com.test2.wilfriedmaris.sipme2.activities.CocktailDetailActivity;
import com.test2.wilfriedmaris.sipme2.activities.CocktailListActivity;
import com.test2.wilfriedmaris.sipme2.entities.Cocktail;
import com.test2.wilfriedmaris.sipme2.fragments.CocktailDetailFragment;
import com.test2.wilfriedmaris.sipme2.activities.UpdateItemInterface;

import java.util.List;

public class CocktailRecyclerViewAdapter
        extends RecyclerView.Adapter<CocktailViewHolder> {
    private final CocktailListActivity mParentActivity;
    private final boolean mTwoPane;
    private List<Cocktail> mCocktails;
    private CocktailRepository mCocktailRepository;

    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Cocktail cocktail = (Cocktail) view.getTag();
            if (mTwoPane) {
                CocktailDetailFragment fragment = new CocktailDetailFragment();
                Bundle bundle = new Bundle();
                bundle.putParcelable(fragment.COCKTAIL_KEY, cocktail);
                bundle.putString(fragment.SIDE_KEY, mParentActivity.side);
                fragment.setArguments(bundle);
                mParentActivity.getSupportFragmentManager().beginTransaction()
                            .add(R.id.cocktail_detail_container, fragment)
                            .commit();
            } else {
                Context context = mParentActivity;
                Intent intent = new Intent(context, CocktailDetailActivity.class);
                intent.putExtra(CocktailDetailActivity.SIDE_KEY, mParentActivity.side);
                intent.putExtra(CocktailDetailActivity.COCKTAIL_KEY, cocktail);
                ((CocktailListActivity) context).startActivityForResult(
                        intent, UpdateItemInterface.RESULT_CODE);
            }

            mParentActivity.logd("onClick");
        }
    };

    public CocktailRecyclerViewAdapter(CocktailListActivity parent, List<Cocktail> items,
            boolean twoPane) {
        mCocktails = items;
        mParentActivity = parent;
        mTwoPane = twoPane;

        mCocktailRepository =
                CocktailRepository.getInstance(new DatabaseHelper(parent).getWritableDatabase());
    }

    @Override
    public CocktailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cocktail_list_content, parent, false);

        mParentActivity.logd("onCreateViewHolder");

        return new CocktailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CocktailViewHolder viewHolder, final int position) {
        viewHolder.contentView.setText(mCocktails.get(position).name);
        Picasso.get().load(mCocktails.get(position).picture).into(viewHolder.imageView);
        viewHolder.ingredients.setText(mCocktails.get(position).totalIngredients());

        viewHolder.itemView.setTag(mCocktails.get(position));
        viewHolder.itemView.setOnClickListener(mOnClickListener);

        handleFavoriteButton(viewHolder, position);

        mParentActivity.logd("onBindViewHolder");
    }

    @Override
    public int getItemCount() {
        return mCocktails.size();
    }

    public void updateRating(float rating, int cocktailId){
        for (Cocktail cocktail : mCocktails){
            if (cocktail.id == cocktailId){
                cocktail.rating.average = rating;
            }
        }
        notifyDataSetChanged();

        mParentActivity.logd("Rating updated in Adapter");
    }

    private void handleFavoriteButton(final CocktailViewHolder viewHolder, final int position){
        final Cocktail cocktail = mCocktails.get(position);
        final Cursor cocktailCursor = mCocktailRepository.getSingle(cocktail.id);

        if (cocktailCursor != null && cocktailCursor.moveToFirst()){
            viewHolder.imgButton.setImageResource(R.drawable.ic_star_golden_24dp);
        }else{
            viewHolder.imgButton.setImageResource(R.drawable.ic_star_border_black_24dp);
        }

        viewHolder.imgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cocktailCursor != null && cocktailCursor.moveToFirst()){
                    viewHolder.imgButton.setImageResource(R.drawable.ic_star_border_black_24dp);
                    mCocktailRepository.remove(cocktail.id);

                    if(mParentActivity.side.equals(mParentActivity.FAVORITES_SIDE)){
                        mCocktails.remove(cocktail);
                        notifyDataSetChanged();
                    }
                }else{
                    viewHolder.imgButton.setImageResource(R.drawable.ic_star_golden_24dp);
                    mCocktailRepository.add(cocktail);
                }
            }
        });

        mParentActivity.logd("FavoriteButton is handled");
    }
}