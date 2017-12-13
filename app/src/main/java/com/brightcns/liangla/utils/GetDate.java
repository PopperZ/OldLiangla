package com.brightcns.liangla.utils;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zhangfeng on 20/2/17.
 */

public class GetDate {
    public static String getSysDate() {//获取系统时间
        //获取系统时间的13位的时间戳
        long time = System.currentTimeMillis();
        String str = String.valueOf(time);
        Log.e("TAG", "当前时间：" + str);
        return str;
    }

    public static boolean getDiffTime(long date) {//计算时间差
        long time = System.currentTimeMillis();
        long diff = time - date;
        Log.e("TAG", "SSSS" + diff);
        return date > time;
    }

    public static String getMinDate(Long diffTime) {
        //获取系统时间的10位的时间戳
        long time = (System.currentTimeMillis() + diffTime) / (1000);
        Log.e("Tag", "我difftime" + diffTime);
        long time2 = time / 2;
        long time3 = time2 + 100;
        String str = String.valueOf(time3);
        Log.e("TAG", "同一时间：" + str);
        return str;
    }

    public static Long getDiffTime(String time) {
        Long time1 = null, time2 = null;
        try {
            time1 = Long.parseLong(time);
            time2 = Long.parseLong(String.valueOf(System.currentTimeMillis()));

        } catch (NumberFormatException e) {
            e.printStackTrace();
            Log.e("TAG", "NumberFormatException");
        }

        return time1 - time2;
    }

    public static String getformatTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd    HH:mm:ss ");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        return str;
    }

    public static String getformatTime(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        @SuppressWarnings("unused")
        long lcc = Long.valueOf(time);
        int i = Integer.parseInt(time.substring(0, 10));
        String times = sdr.format(new Date(i * 1000L));
        return times;
    }


    public static String formatTime(String data) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 hh:mm");
        String str = null;
        try {
            Date date = formatter.parse(data);
            str = formatter.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    public static String getSysformatTime() {
        String data= getSysDate();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 hh:mm");
        String str = null;
        try {
            Date date = formatter.parse(data);
            str = formatter.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    public static String getQRExpire(){
        long time=System.currentTimeMillis()/1000+10800;
        String hexTime=Long.toHexString(time);
        return hexTime;
    }
}
