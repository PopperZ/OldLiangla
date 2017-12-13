package com.brightcns.liangla.utils;

import android.util.Log;

/**
 * Created by luis_gong on 2017/11/1.
 */

public class LogUtil {
    //处理调试阶段日志打印，上线版本后不打印的问题
    public static final int VERBOSE = 1;
    public static final int DEBUG = 2;
    public static final int INFO = 3;
    public static final int WARN = 4;
    public static final int ERROR = 5;
    public static final int NOTHING = 6;
    public static final int level = VERBOSE;

    public static void v(String tag,String msg){
        if (level<=VERBOSE) {
            Log.v(tag,msg);
        }
    }

    public static void d(String tag,String msg){
        if (level<=DEBUG) {
            Log.d(tag,msg);
        }
    }

    public static void i(String tag,String msg){
        if (level<=INFO) {
            Log.i(tag,msg);
        }
    }

    public static void w(String tag,String msg){
        if (level<=WARN) {
            Log.w(tag,msg);
        }
    }

    public static void e(String tag,String msg){
        if (level<=ERROR) {
            Log.e(tag,msg);
        }
    }




}
