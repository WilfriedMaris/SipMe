package com.test2.wilfriedmaris.sipme2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.test2.wilfriedmaris.sipme2.utilities.BaseActivity;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private Button mMainButton;
    private Button mSecondaryButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMainButton = findViewById(R.id.main_button);
        mMainButton.setOnClickListener(this);

        mSecondaryButton = findViewById(R.id.secondary_button);
        mSecondaryButton.setOnClickListener(this);

        Log.d(this.getClass().getSimpleName(), "onCreate: start MainActivity");
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, CocktailListActivity.class);

        switch (v.getId()){
            case R.id.main_button:
                Log.d(this.getClass().getSimpleName(), "onClick: main_button");
                intent.putExtra("side", "cocktails");
                break;
            case R.id.secondary_button:
                Log.d(this.getClass().getSimpleName(), "onClick: secondary_button");
                intent.putExtra("side", "favorites");
                break;
        }

        this.startActivity(intent);
    }
}
