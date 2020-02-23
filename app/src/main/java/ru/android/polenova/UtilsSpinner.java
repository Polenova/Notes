package ru.android.polenova;

import android.app.Activity;
import android.content.Intent;

public class UtilsSpinner {

    private static int sTheme;

    public final static int THEME_APP = 0;
    public final static int THEME_COLD = 1;
    public final static int THEME_DARK = 2;
    public final static int THEME_FUN = 3;

    /**
     * Set the theme of the Activity, and restart it by creating a new Activity of the same type.
     */
    public static void changeToTheme(Activity activity, int theme) {
        sTheme = theme;
        activity.finish();

        activity.startActivity(new Intent(activity, activity.getClass()));

    }

    /**
     * Set the theme of the activity, according to the configuration.
     */
    public static void onActivityCreateSetTheme(Activity activity) {
        switch (sTheme) {
            default:
            case THEME_COLD:
                activity.setTheme(R.style.ColdTheme);
                break;
            case THEME_APP:
                activity.setTheme(R.style.AppTheme);
                break;
            case THEME_DARK:
                activity.setTheme(R.style.DarkTheme);
                break;
            case THEME_FUN:
                activity.setTheme(R.style.FunTheme);
                break;
        }
    }
}

