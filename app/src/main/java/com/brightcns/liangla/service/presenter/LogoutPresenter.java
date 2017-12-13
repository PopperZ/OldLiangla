package com.brightcns.liangla.service.presenter;

import android.content.Intent;

import com.brightcns.liangla.app.MyApplication;
import com.brightcns.liangla.service.entity.LogoutResponseBean;
import com.brightcns.liangla.service.manager.DataManager;
import com.brightcns.liangla.service.view.BaseView;
import com.brightcns.liangla.service.view.LogoutBaseView;
import com.brightcns.liangla.utils.AppUtil;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by 巩贺 on 2017/11/10.
 */

public class LogoutPresenter implements Presenter {

    private DataManager mDataManager;
    private CompositeSubscription mCompositeSubscription;
    private LogoutBaseView mLogoutView;
    private LogoutResponseBean mLogoutResponseBean;

    @Override
    public void onCreat() {
        mDataManager = new DataManager(MyApplication.getGlobalContext());
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
        mLogoutView = (LogoutBaseView) baseView;
    }

    @Override
    public void attachIncomingIntent(Intent intent) {

    }

    public void getLogout() {
        mCompositeSubscription.add(mDataManager.getLogout(AppUtil.getHeaderMap(), AppUtil.getUserId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(logoutBean -> {
                    if (logoutBean != null) {
                        mLogoutView.onSuccess(logoutBean);
                    }
                }, throwable -> {
                    mLogoutView.onError("网络请求失败！");
                }));
    }

}
