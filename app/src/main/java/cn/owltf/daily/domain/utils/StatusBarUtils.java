package cn.owltf.daily.domain.utils;


import android.app.Activity;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;

import com.readystatesoftware.systembartint.SystemBarTintManager;

import cn.owltf.daily.R;

public class StatusBarUtils {

    public StatusBarUtils(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = activity.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            SystemBarTintManager tintManager = new SystemBarTintManager(activity);
            tintManager.setTintColor(R.color.primary);
            tintManager.setStatusBarTintEnabled(true);
        }
    }
}
