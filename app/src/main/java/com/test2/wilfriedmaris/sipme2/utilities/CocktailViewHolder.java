package com.test2.wilfriedmaris.sipme2.utilities;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.test2.wilfriedmaris.sipme2.R;

public class CocktailViewHolder extends RecyclerView.ViewHolder {
    public final TextView contentView;
    public final ImageView imageView;
    public final TextView ingredients;
    public final ImageButton imgButton;

    CocktailViewHolder(View view) {
        super(view);
        contentView = view.findViewById(R.id.content);
        imageView = view.findViewById(R.id.img_view_list_item);
        ingredients = view.findViewById(R.id.ingredients);
        imgButton = view.findViewById(R.id.fav);
    }
}
