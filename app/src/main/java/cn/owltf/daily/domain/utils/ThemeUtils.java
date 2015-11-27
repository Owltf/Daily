package cn.owltf.daily.domain.utils;


import android.app.Activity;

import cn.owltf.daily.R;

public class ThemeUtils {

    public static boolean isLight = true;

    public static void changeTheme(Activity activity) {
        if (!isLight) {
            activity.setTheme(R.style.Base_Theme_AppTheme_Dark);
        }
    }
}

