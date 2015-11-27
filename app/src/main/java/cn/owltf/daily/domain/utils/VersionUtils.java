package cn.owltf.daily.domain.utils;


import android.content.Context;
import android.content.pm.PackageManager;

import cn.owltf.daily.domain.application.App;

public class VersionUtils {

    public static String setUpVersionName(Context context) {
        String versionName = null;
        try {
            versionName = context.getApplicationContext().getPackageManager()
                    .getPackageInfo(context.getApplicationContext().getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }

    public static int getVerisonCode() {
        int versionCode = 0;
        try {
            versionCode = App.getContext().getPackageManager().getPackageInfo(App.getContext().getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }
}
