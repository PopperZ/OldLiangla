package com.brightcns.liangla.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.ArrayMap;

import com.blankj.utilcode.util.DeviceUtils;
import com.blankj.utilcode.util.EmptyUtils;

import java.io.File;
import java.util.Map;


/**
 * Created by Administrator on 2017/8/31.
 */

public class AppUtil {

    /**
     * 安装apk
     *
     * @param context 上下文
     * @param file    APK文件
     */
    public static void installApk(Context context, File file) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file),
                "application/vnd.android.package-archive");
        context.startActivity(intent);
    }

    /**
     * 获取状态栏高度
     *
     * @param context 上下文
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 添加头部信息
     */
    public static Map<String, String> getHeaderMap() {
        Map<String, String> mMap = new ArrayMap<>();
        String userId = getUserId();
        String userToken =getUserToken();
        String LoginToken = getLoginToken();
        int sdkVersion = DeviceUtils.getSDKVersion();
        if (EmptyUtils.isNotEmpty(userId) && EmptyUtils.isNotEmpty(userToken)) {
            String time = (System.currentTimeMillis() + 1 * 60 * 1000) / 1000 + "";
            String sign = EncryptedUtil.with()
                    .setParams("X-User-Id", userId)
                    .setParams("X-Device-Id", "1" + sdkVersion)
                    .setParams("X-User-Timestamp", time)
                    .setSignKey(LoginToken)
                    .mdkey();
            mMap.put("X-User-Id", userId);
            mMap.put("X-User-Token", userToken);
            mMap.put("X-Device-Id", "1" + sdkVersion);
            mMap.put("X-User-Timestamp", time);
            mMap.put("X-User-Sign", sign);
        }
        return mMap;
    }

    //获取userID
    public static String getUserId(){
        return PreferenceUtil.getString(ConstantUtil.USERID,"");
    }

    //获取usertoken
    public static String getUserToken(){
        return PreferenceUtil.getString(ConstantUtil.USERTOKEN,"");
    }

    //获取登录token
    public static String getLoginToken(){
        return PreferenceUtil.getString(ConstantUtil.LOGINTOKEN,"");
    }

    /**
     * 获取城市
     * 默认上海
     */

    public static String getArea(){
        return PreferenceUtil.getString(ConstantUtil.AREA_CODE,"2000");
    }


    /**
     * 获取业务类型
     * 默认上海地铁协议
     */
    public static String getModes(){
        return PreferenceUtil.getString(ConstantUtil.AREA_NAME,"01");
    }
}



