package com.brightcns.liangla.service.presenter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.brightcns.liangla.app.MyApplication;
import com.brightcns.liangla.service.entity.AppVersionBean;
import com.brightcns.liangla.service.manager.DataManager;
import com.brightcns.liangla.service.view.AppvertionPresenterBaseView;
import com.brightcns.liangla.service.view.BaseView;
import com.brightcns.liangla.utils.CheckErrorCode;
import com.brightcns.liangla.utils.LogUtil;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by 巩贺 on 2017/11/8.
 */

public class AppVertionPresenter implements Presenter {

    private Context mContext;
    private DataManager mDataManager;
    private CompositeSubscription mCompositeSubscription;
    private AppVersionBean mAppVersionBean;
    private SharedPreferences mShareAdmin;
    private SharedPreferences.Editor mEditedAdmin;
    private SharedPreferences mMobileTokenShare;
    private SharedPreferences.Editor mMobileTokenEdited;
    private SharedPreferences mShare;
    private SharedPreferences.Editor mEdited;
    private AppvertionPresenterBaseView mVertionView;

    public AppVertionPresenter(Context context) {
        this.mContext = context;
    }

    @Override
    public void onCreat() {
        mDataManager = new DataManager(mContext);
        mCompositeSubscription = new CompositeSubscription();
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {
        if (mCompositeSubscription.hasSubscriptions()) {
            mCompositeSubscription.unsubscribe();
        }
    }

    @Override
    public void AttachView(BaseView baseView) {
        mVertionView = (AppvertionPresenterBaseView) baseView;
    }

    @Override
    public void attachIncomingIntent(Intent intent) {

    }

    public void getAppVertion(Integer os, String areaName, Integer cVer) {
        Log.e("TAG", "os" + os + "areaName" + areaName + "cVer" + cVer);
        mCompositeSubscription.add(mDataManager.getAppVersion(os, areaName, cVer)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(appVersionBean -> {
                    if (appVersionBean.getCode() == 0) {
                        LogUtil.e("TAG", "获取版本信息接口请求成功" + appVersionBean.toString());
                        mVertionView.onSuccess(appVersionBean);
                    } else {
                        CheckErrorCode.checkErrorCodeAndToast(MyApplication.getGlobalContext(), appVersionBean.getCode());
                    }
                },throwable -> {
                    mVertionView.onError("请求失败！！");
                    LogUtil.e("TAG","请求失败！！"+throwable.getMessage());
                }));
    }

}
