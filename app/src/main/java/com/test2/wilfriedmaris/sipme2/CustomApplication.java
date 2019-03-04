package com.test2.wilfriedmaris.sipme2;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.test2.wilfriedmaris.sipme2.utilities.ApplicationHelper;

public class CustomApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String colorPreference = sharedPreferences.getString(
                getString(R.string.pref_color_key), getString(R.string.default_color_key));
        ApplicationHelper.colorPicker(colorPreference, this);
    }
}
