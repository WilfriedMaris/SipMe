package com.test2.wilfriedmaris.sipme2.activities;

import android.content.Intent;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.test2.wilfriedmaris.sipme2.R;

import io.multimoon.colorful.CAppCompatActivity;

public class BaseActivity extends CAppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.preferences_menu_item:
                Intent settingsIntent = new Intent(this, SettingsActivity.class);
                this.startActivity(settingsIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void logd(String message) {
        String person = "WM: ";
        Log.d(this.getClass().getSimpleName(), person + message);
    }

    public void loge(String message) {
        String person = "WM: ";
        Log.e(this.getClass().getSimpleName(), person + message);
    }
}