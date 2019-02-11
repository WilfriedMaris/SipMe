package com.test2.wilfriedmaris.sipme2;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.test2.wilfriedmaris.sipme2.entities.Cocktail;
import com.test2.wilfriedmaris.sipme2.utilities.CocktailRepository;
import com.test2.wilfriedmaris.sipme2.utilities.DatabaseHelper;

import java.util.List;

public class CocktailRecyclerViewAdapter
        extends RecyclerView.Adapter<CocktailRecyclerViewAdapter.ViewHolder> {

    private final CocktailListActivity mParentActivity;
    private final List<Cocktail> mValues;
    private final boolean mTwoPane;
    private CocktailRepository mCocktailRepository;

    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Cocktail cocktail = (Cocktail) view.getTag();
            if (mTwoPane) {
                CocktailDetailFragment fragment = new CocktailDetailFragment();
                Bundle bundle = new Bundle();
                bundle.putParcelable(fragment.LIFECYCLE_CALLBACKS_TEXT_KEY, cocktail);
                fragment.setArguments(bundle);
                mParentActivity.getSupportFragmentManager().beginTransaction()
                        .add(R.id.cocktail_detail_container, fragment)
                        .commit();
            } else {
                Context context = view.getContext();
                Intent intent = new Intent(context, CocktailDetailActivity.class);
                intent.putExtra("cocktail", cocktail);

                context.startActivity(intent);
            }
        }
    };

    CocktailRecyclerViewAdapter(CocktailListActivity parent, List<Cocktail> items,
            boolean twoPane) {
        mValues = items;
        mParentActivity = parent;
        mTwoPane = twoPane;
        DatabaseHelper dbHelper = new DatabaseHelper(parent);
        mCocktailRepository = CocktailRepository.getInstance(dbHelper.getWritableDatabase());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cocktail_list_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Cocktail cocktail = mValues.get(position);
        Cursor cocktailCursor = mCocktailRepository.getSingle(cocktail.id);

        holder.mContentView.setText(mValues.get(position).name);
        Picasso.get().load(mValues.get(position).picture).into(holder.mImageView);
        holder.mIngredients.setText(mValues.get(position).totalIngredients());

        if (cocktailCursor != null && cocktailCursor.moveToFirst()){
            holder.mImgButton.setImageResource(R.drawable.ic_star_golden_24dp);
        }else{
            holder.mImgButton.setImageResource(R.drawable.ic_star_border_black_24dp);
        }

        holder.itemView.setTag(mValues.get(position));
        holder.itemView.setOnClickListener(mOnClickListener);

        holder.mImgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cocktail cocktail = mValues.get(position);
                Cursor cocktailCursor = mCocktailRepository.getSingle(cocktail.id);
                if (cocktailCursor != null && cocktailCursor.moveToFirst()){
                    holder.mImgButton.setImageResource(R.drawable.ic_star_border_black_24dp);
                    mCocktailRepository.remove(cocktail.id);
                    if(mParentActivity.mSide.equals("favorites")){
                        mValues.remove(cocktail);
                        notifyDataSetChanged();
                    }
                }else{
                    holder.mImgButton.setImageResource(R.drawable.ic_star_golden_24dp);
                    mCocktailRepository.add(cocktail);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final TextView mContentView;
        final ImageView mImageView;
        final TextView mIngredients;
        final ImageButton mImgButton;

        ViewHolder(View view) {
            super(view);
            mContentView = view.findViewById(R.id.content);
            mImageView = view.findViewById(R.id.img_view_list_item);
            mIngredients = view.findViewById(R.id.ingredients);
            mImgButton = view.findViewById(R.id.fav);
        }
    }
}