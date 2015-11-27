package cn.owltf.daily.domain.application;


import android.app.Activity;
import android.app.Application;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import cn.owltf.daily.domain.utils.CrashCatcher;

public class App extends Application {
    private static Context mContext;
    private static List<Activity> mActivities = new ArrayList<>();

    @Override
    public void onCreate(){
        super.onCreate();
        mContext = this;
        CrashCatcher crashCatcher = CrashCatcher.newInstance();
        crashCatcher.setDefaultCrashCatcher();
    }

    public static Context getContext(){
        return mContext;
    }

    public static void addActivity(Activity activity){
        mActivities.add(activity);
    }

    public static void removeActivity(Activity activity){
        mActivities.remove(activity);
    }

    public static void exitAll(){
        for (Activity activity : mActivities){
            activity.finish();
        }
        System.exit(0);
    }
}
