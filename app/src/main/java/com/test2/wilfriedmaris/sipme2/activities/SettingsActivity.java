package com.test2.wilfriedmaris.sipme2.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;

import com.test2.wilfriedmaris.sipme2.R;
import com.test2.wilfriedmaris.sipme2.utilities.ApplicationHelper;

public class SettingsActivity extends BaseActivity
        implements SharedPreferences.OnSharedPreferenceChangeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        ApplicationHelper.setUpToolbar(this, R.id.detail_toolbar);

        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);

        logd("onCreate");
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(getString(R.string.pref_color_key))){
            String colorPreference = sharedPreferences.getString(
                    getString(R.string.pref_color_key), getString(R.string.default_color_key));

            ApplicationHelper.colorPicker(colorPreference, this.getApplication());
            this.recreate();
        }

        logd("Settings changed");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(this);

        logd("onDestroy");
    }
}
