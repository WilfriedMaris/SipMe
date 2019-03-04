package com.test2.wilfriedmaris.sipme2.activities;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.test2.wilfriedmaris.sipme2.R;
import com.test2.wilfriedmaris.sipme2.utilities.AlarmHelper;
import com.test2.wilfriedmaris.sipme2.utilities.ApplicationHelper;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button mainButton = findViewById(R.id.main_button);
        mainButton.setOnClickListener(this);

        Button secondaryButton = findViewById(R.id.secondary_button);
        secondaryButton.setOnClickListener(this);

        AlarmHelper alarmService = new AlarmHelper(this);
        alarmService.startAlarm();

        logd("onCreate");
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, CocktailListActivity.class);

        switch (v.getId()){
            case R.id.main_button:
                logd("Pressed main_button");
                intent.putExtra(CocktailListActivity.SIDE_KEY, CocktailListActivity.COCKTAIL_SIDE);
                break;
            case R.id.secondary_button:
                logd("Pressed secondary_button");
                intent.putExtra(CocktailListActivity.SIDE_KEY, CocktailListActivity.FAVORITES_SIDE);
                break;
        }

        this.startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        TextView title = findViewById(R.id.textView);
        YoYo.with(Techniques.Flash).duration(1500).playOn(title);
        if (!ApplicationHelper.isNetworkAvailable(this)){
            YoYo.with(Techniques.Hinge).duration(2500).playOn(title);
            Button mainButton = findViewById(R.id.main_button);
            mainButton.setPaintFlags(mainButton.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            mainButton.setEnabled(false);
        }
    }
}
