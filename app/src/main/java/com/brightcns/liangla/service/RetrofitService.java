package com.brightcns.liangla.service;


import java.util.Map;

import com.brightcns.liangla.service.entity.AppVersionBean;
import com.brightcns.liangla.service.entity.LogoutBean;
import com.brightcns.liangla.service.entity.QRBean;
import com.brightcns.liangla.service.entity.UserInfo;
import com.brightcns.liangla.service.entity.LoginAuthCodeBean;
import com.brightcns.liangla.service.entity.LoginBean;
import com.brightcns.liangla.service.entity.MessageBean;
import com.brightcns.liangla.service.entity.UpdataUserNameBean;
import com.brightcns.liangla.service.entity.UploadIconBean;

import okhttp3.RequestBody;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by 巩贺 on 2017/10/28.
 */

public interface RetrofitService {

    /*get appVersion*/
    @GET("apps/versions/newest")
    Observable<AppVersionBean> getAppVersion(
            @Query("os") Integer os
            , @Query("areaName") String areaName
            , @Query("cVer") Integer cVer);


    /*getAuthCode*/
    @GET("sms/vCode")
    Observable<LoginAuthCodeBean> getAuthCode(
            @Query("phone") String phone
            , @Query("service") String service
            , @Query("sign") String sign);

    /*login*/
    @POST("login")
    Observable<LoginBean> getLogin(
            @Query("phone") String phone
            , @Query("vCode") String vCode
            , @Query("mobileId") String mobileId);

    /*get user charaacterinfo*/
    @GET("appuserinfo")
    Observable<UserInfo> getUserInfo(
            @HeaderMap Map<String, String> headers);

    /*logout*/
    @GET("{userId}")
    Observable<LogoutBean> getLogout(@HeaderMap Map<String, String> headers,
                                  @Path(value = "userId", encoded = true) String userId);

    //******************************************************************************************************************************

    /*modify user icon*/
    @Multipart
    @POST("users/{userId}/avatar")
    Observable<UploadIconBean> uploadIcon(
            @Header("userToken") String userToken
            , @Header("timeStamp") Long timeStamp
            , @Header("sign") String sign
            , @Path(value = "userId", encoded = true) String userId
            , @Part("pictureFile\"; filename=\"image.png\"") RequestBody imgs);

    /*modify user name*/
    @POST("users/{userId}/userName")
    Observable<UpdataUserNameBean> modifyUserName(
            @Header("userToken") String userToken
            , @Header("timeStamp") Long timeStamp
            , @Header("sign") String sign
            , @Path(value = "userId", encoded = true) String userId
            , @Query("userName") String userName
    );


    /*My Message*/
    @GET("{userId}/messages")
    Observable<MessageBean> getMyMsg(
            @Header("userToken") String mobileToken
            , @Header("timeStamp") Long timeStamp
            , @Header("sign") String sign
            , @Path(value = "userId", encoded = true) String userId);

    /*qr Message*/
    @GET("qrCode")
    Observable<QRBean> getQRMsg(@HeaderMap Map<String, String> headers, @Query("area") String area);
}
