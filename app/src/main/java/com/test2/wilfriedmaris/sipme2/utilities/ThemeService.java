package com.test2.wilfriedmaris.sipme2.utilities;

import android.app.Application;

import io.multimoon.colorful.Defaults;
import io.multimoon.colorful.ThemeColor;

import static io.multimoon.colorful.ColorfulKt.initColorful;

public class ThemeService {
    private static final ThemeService ourInstance = new ThemeService();

    public static ThemeService getInstance() {
        return ourInstance;
    }

    private ThemeService() {}

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
}
