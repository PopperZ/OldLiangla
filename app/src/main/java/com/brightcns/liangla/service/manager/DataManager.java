package com.brightcns.liangla.service.manager;

import android.content.Context;

import java.util.Map;

import com.brightcns.liangla.service.RetrofitHelper;
import com.brightcns.liangla.service.RetrofitService;
import com.brightcns.liangla.service.entity.AppVersionBean;
import com.brightcns.liangla.service.entity.LogoutBean;
import com.brightcns.liangla.service.entity.QRBean;
import com.brightcns.liangla.service.entity.UserInfo;
import com.brightcns.liangla.service.entity.LoginAuthCodeBean;
import com.brightcns.liangla.service.entity.LoginBean;
import com.brightcns.liangla.service.entity.UpdataUserNameBean;
import com.brightcns.liangla.service.entity.UploadIconBean;

import okhttp3.RequestBody;


/**
 * Created by 巩贺 on 2017/10/30.
 */

public class DataManager {
    private RetrofitService mRetrofitService;

    public DataManager(Context context) {
        this.mRetrofitService = RetrofitHelper.getInstance(context).getServer();
    }

    /*v2   getAppVersion*/
    public rx.Observable<AppVersionBean> getAppVersion(Integer os, String areaName, Integer cVer) {
        return mRetrofitService.getAppVersion(os, areaName, cVer);
    }

    public rx.Observable<LoginAuthCodeBean> getAuthCode(String phone, String service, String sign) {
        return mRetrofitService.getAuthCode(phone, service, sign);
    }

    public rx.Observable<LoginBean> getLogin(String phone, String Vcode, String mobileId) {
        return mRetrofitService.getLogin(phone, Vcode, mobileId);
    }

    public rx.Observable<UserInfo> getUserInfo(Map headmap) {
        return mRetrofitService.getUserInfo(headmap);
    }

    public rx.Observable<LogoutBean> getLogout(Map headmap, String userId){
        return mRetrofitService.getLogout(headmap,userId);
    }

    //&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&


    public rx.Observable<UploadIconBean> upLoadIcon(String useToken, long timeStamp, String sign, String userId, RequestBody imgs) {
        return mRetrofitService.uploadIcon(useToken, timeStamp, sign, userId, imgs);
    }

    public rx.Observable<UpdataUserNameBean> modifyUserName(String userToken, long timeStamp, String sign, String userId, String userName) {
        return mRetrofitService.modifyUserName(userToken, timeStamp, sign, userId, userName);
    }
    public rx.Observable<QRBean> getQRMsg(Map<String, String>headers, String area) {
        return mRetrofitService.getQRMsg(headers, area);
    }
}
