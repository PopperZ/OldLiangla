package com.brightcns.liangla.service.presenter;

import android.content.Context;
import android.content.Intent;

import com.brightcns.liangla.service.manager.DataManager;
import com.brightcns.liangla.service.view.BaseView;
import com.brightcns.liangla.service.view.LoginBaseView;
import com.brightcns.liangla.utils.LogUtil;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by 巩贺 on 2017/11/9.
 */

public class LoginPresenter implements Presenter {
    private Context mContext;
    private DataManager mDataManager;
    private CompositeSubscription mCompositeSubscription;
    private LoginBaseView mLoginView;

    public LoginPresenter(Context context) {
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
        mLoginView = (LoginBaseView) baseView;
    }

    @Override
    public void attachIncomingIntent(Intent intent) {

    }

    public void getLogin(String phone, String Vcode, String mobileId) {
        LogUtil.e("TAG", "phone" + phone + "Vcode" + Vcode + "mobileId" + mobileId);
        mCompositeSubscription.add(mDataManager.getLogin(phone, Vcode, mobileId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(loginBean -> {
                    if (loginBean != null) {
                        mLoginView.onSuccess(loginBean);
                    }
                }, throwable -> {
                    mLoginView.onError("请求错误！");
                }));
    }

}
