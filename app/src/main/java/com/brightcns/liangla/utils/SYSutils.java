package com.brightcns.liangla.utils;

import android.app.Activity;
import android.content.Context;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by zhangfeng on 20/5/17.
 * 获取系统参数的工具类
 */

public class SYSutils {

    //获取屏幕像素密度
    public static float getDensity(Context context) {
        DisplayMetrics displayMetrics =context.getResources().getDisplayMetrics();
        Log.e("getDensity","Density is "+displayMetrics.density+" densityDpi is "+displayMetrics.densityDpi+" height: "+displayMetrics.heightPixels+
                " width: "+displayMetrics.widthPixels);
        return displayMetrics.density;
    }

    //得到屏幕宽
    public static int getWidth(Context context){
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);

        int width = wm.getDefaultDisplay().getWidth();
        return width;
    }
    //得到屏幕高
    public static int gethHight(Context context){
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);

        int hight = wm.getDefaultDisplay().getHeight();
        return hight;
    }


    /**
     * 将ip的整数形式转换成ip形式
     *
     * @param ipInt
     * @return
     */
    public static String int2ip(int ipInt) {
        StringBuilder sb = new StringBuilder();
        sb.append(ipInt & 0xFF).append(".");
        sb.append((ipInt >> 8) & 0xFF).append(".");
        sb.append((ipInt >> 16) & 0xFF).append(".");
        sb.append((ipInt >> 24) & 0xFF);
        return sb.toString();
    }



    // 根据亮度值修改当前window亮度
    public void changeAppBrightness(Context context, int brightness) {
        Window window = ((Activity) context).getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        if (brightness == -1) {
            lp.screenBrightness = WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_NONE;
        } else {
            lp.screenBrightness = (brightness <= 0 ? 1 : brightness) / 255f;
        }
        window.setAttributes(lp);
    }

    // 根据修改当前window为最大亮度
    public static void changeAppBrightness(Context context) {
//        Window window = ((Activity) context).getWindow();
//        WindowManager.LayoutParams lp = window.getAttributes();
        WindowManager.LayoutParams lp = ((Activity) context).getWindow().getAttributes();
        lp.screenBrightness = Float.valueOf(255) *(1f / 255f);
        ((Activity) context).getWindow().setAttributes(lp);
    }

    //得到系统当前亮度
    public static int getSystemBrightness(Context context) {
                 int systemBrightness = 0;
                try {
                        systemBrightness = Settings.System.getInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
                     } catch (Settings.SettingNotFoundException e) {
                      e.printStackTrace();
                     }
                return systemBrightness;
             }
}




