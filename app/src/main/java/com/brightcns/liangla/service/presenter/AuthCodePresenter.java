package com.brightcns.liangla.service.presenter;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.brightcns.liangla.service.manager.DataManager;
import com.brightcns.liangla.service.view.BaseView;
import com.brightcns.liangla.utils.CheckErrorCode;
import com.brightcns.liangla.utils.LogUtil;
import com.brightcns.liangla.utils.ToastUtils;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by 巩贺 on 2017/11/9.
 */

public class AuthCodePresenter implements Presenter {

    private Context mContext;
    private CompositeSubscription mCompositeSubscription;
    private DataManager mDataManager;

    public AuthCodePresenter(Context context) {
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

    }

    @Override
    public void attachIncomingIntent(Intent intent) {

    }

    public void getAuthCode(String phone, String service, String sign) {
        mCompositeSubscription.add(mDataManager.getAuthCode(phone, service, sign)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(loginAuthCodeBean -> {
                    if (loginAuthCodeBean != null) {
                        //相应的操作
                        if (loginAuthCodeBean.getCode() == 0) {
                            ToastUtils.showShort(mContext, "验证码发送成功！");
                        } else {
                            CheckErrorCode.checkErrorCodeAndToast(mContext, loginAuthCodeBean.getCode());
                        }
                    }
                }, throwable -> {
                    Toast.makeText(mContext, "网络错误", Toast.LENGTH_SHORT).show();
                    LogUtil.e("TAG",throwable.getMessage());
                }));
    }
}
