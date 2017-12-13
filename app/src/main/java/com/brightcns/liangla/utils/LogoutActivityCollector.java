package com.brightcns.liangla.utils;

import android.app.Activity;

import java.util.ArrayList;

/**
 * Created by luis_gong on 2017/11/18.
 */

public class LogoutActivityCollector {
    private static ArrayList<Activity> activityArrayList = new ArrayList();

    public static void addActivity(Activity activity) {
        activityArrayList.add(activity);
    }

    public static void removeActivity(Activity activity) {
        activityArrayList.remove(activity);
    }

    public static void finishAllActivity() {
        for (Activity activity : activityArrayList) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }
}
