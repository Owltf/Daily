package cn.owltf.daily.domain.utils;


import android.content.Context;
import android.content.SharedPreferences;

import cn.owltf.daily.domain.application.App;

public class PrefUtils {

    private static final String PRE_NAME = "cn.owltf.daily_preferences";

    public static final String PRE_CRASH_ISLASTTIMECRASHED = "isLastTimeCrashed";
    public static final String PRE_CRASH_URI = "crashUri";

    public static final String PRE_CACHE_ENABLE = "enable_cache";


    private static SharedPreferences getSharedPreferences() {
        return App.getContext()
                .getSharedPreferences(PRE_NAME, Context.MODE_PRIVATE);
    }

    public static boolean isEnableCache() {
        return getSharedPreferences().getBoolean(PRE_CACHE_ENABLE, false);
    }



    public static boolean isCrashedLastTime() {
        return getSharedPreferences().getBoolean(PRE_CRASH_ISLASTTIMECRASHED, false);
    }

    public static String getCrashUri() {
        return getSharedPreferences().getString(PRE_CRASH_URI, null);
    }

    public static void setCrash(boolean isLastTimeCrashed, String crashUri) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putBoolean(PRE_CRASH_ISLASTTIMECRASHED, isLastTimeCrashed);
        editor.putString(PRE_CRASH_URI, crashUri);
        editor.commit();
    }

    public static void setCrash(boolean isLastTimeCrashed) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putBoolean(PRE_CRASH_ISLASTTIMECRASHED, isLastTimeCrashed);
        editor.apply();
    }
}
