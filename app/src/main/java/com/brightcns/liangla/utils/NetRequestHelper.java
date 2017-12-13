package com.brightcns.liangla.utils;

import android.content.Context;
import android.util.Log;

/**
 * Created by 巩贺 on 2017/5/22.
 */

public class NetRequestHelper {

    //返回userId
    public static String getUserId(Context context)
    {
        String userId = context.getSharedPreferences("admin", Context.MODE_PRIVATE).getString("userId", "");
        return userId;
    }

    //返回UserToken
    public static String getUserToken(Context context)
    {
        String userToken = context.getSharedPreferences("admin", Context.MODE_PRIVATE).getString("userToken", "");
        return userToken;
    }


    //返回timstamp
    public static String gettimestamp()
    {
        String timeStamp = GetDate.getSysDate();
        return timeStamp;
    }

    //返回mobileToken
    public static String getMobileToken(Context context)
    {
       String mobileToken = context.getSharedPreferences("mobiletoken", Context.MODE_PRIVATE).getString("mobileToken", "");
        Log.e("getMobileToken NET",mobileToken);
        return mobileToken;
    }


}
