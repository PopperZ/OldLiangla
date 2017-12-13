package com.brightcns.liangla.utils;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 巩贺 on 2017/8/15.
 */

public class ActivityCollector {

    private static List<Activity> activityList = new ArrayList<>();

    public static void addActivity(Activity activity) {
        activityList.add(activity);
    }
    public static void removeActivity(Activity activity){
        activityList.remove(activity);
    }

    public static Activity getTopActivity(){
        if(activityList.isEmpty()){
            return null;
        }else {
            return activityList.get(activityList.size() - 1);
        }
    }
}
