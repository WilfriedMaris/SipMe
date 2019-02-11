package com.test2.wilfriedmaris.sipme2;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.ActionBar;

import com.test2.wilfriedmaris.sipme2.entities.Cocktail;
import com.test2.wilfriedmaris.sipme2.utilities.BaseActivity;

public class CocktailDetailActivity extends BaseActivity {
    private Cocktail mItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mItem = getIntent().getParcelableExtra("cocktail");
        setContentView(R.layout.activity_cocktail_detail);
        Toolbar toolbar = findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        if (savedInstanceState == null) {
            createFragment();
        }
    }

    private void createFragment(){
        CocktailDetailFragment fragment = new CocktailDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(fragment.LIFECYCLE_CALLBACKS_TEXT_KEY, mItem);
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.cocktail_detail_container, fragment)
                .commit();
    }

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            navigateUpTo(new Intent(this, CocktailListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }*/
}
