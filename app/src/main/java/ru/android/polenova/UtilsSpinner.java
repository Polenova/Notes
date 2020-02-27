package ru.android.polenova;

import android.app.Activity;
import android.content.Intent;

public class UtilsSpinner {

    private static int sTheme;

    public final static int THEME_COLD = 0;
    public final static int THEME_CLASSIC = 1;
    public final static int THEME_DARK = 2;
    public final static int THEME_FUN = 3;

    public static void changeToTheme(Activity activity, int theme) {
        sTheme = theme;
        activity.finish();
        activity.startActivity(new Intent(activity, activity.getClass()));
    }

    public static void onActivityCreateSetTheme(Activity activity) {
        switch (sTheme) {
            default:
            case THEME_COLD:
                activity.setTheme(R.style.AppTheme);
                break;
            case THEME_CLASSIC:
                activity.setTheme(R.style.ClassicTheme);
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

