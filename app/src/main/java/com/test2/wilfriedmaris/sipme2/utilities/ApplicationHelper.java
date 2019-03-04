package com.test2.wilfriedmaris.sipme2.utilities;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.test2.wilfriedmaris.sipme2.activities.BaseActivity;

import io.multimoon.colorful.Defaults;
import io.multimoon.colorful.ThemeColor;

import static io.multimoon.colorful.ColorfulKt.initColorful;

public class ApplicationHelper {
    private static final ApplicationHelper ourInstance = new ApplicationHelper();

    public static ApplicationHelper getInstance() {
        return ourInstance;
    }

    private ApplicationHelper() {}

    public static void colorPicker(String color, Application application){
        ThemeColor primaryColor = ThemeColor.LIGHT_GREEN;
        ThemeColor secondaryColor = ThemeColor.GREEN;

        switch (color){
            case "green":
                primaryColor = ThemeColor.GREEN;
                secondaryColor = ThemeColor.LIGHT_GREEN;
                break;
            case "orange":
                primaryColor = ThemeColor.ORANGE;
                secondaryColor = ThemeColor.DEEP_ORANGE;
                break;
            case "red":
                primaryColor = ThemeColor.RED;
                secondaryColor = ThemeColor.AMBER;
                break;
        }

        Defaults defaults = new Defaults(
                primaryColor, secondaryColor, false, false, 0);
        initColorful(application, defaults);
    }

    public static void setUpToolbar(BaseActivity activity, int id){
        Toolbar toolbar = activity.findViewById(id);
        activity.setSupportActionBar(toolbar);

        // Show the Up button in the action bar.
        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public static boolean isNetworkAvailable(BaseActivity activity) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static void shortToast(BaseActivity activity, String message){
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
    }
}
