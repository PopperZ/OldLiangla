package com.brightcns.liangla.service.presenter;

import android.content.Intent;

import com.brightcns.liangla.app.MyApplication;
import com.brightcns.liangla.service.entity.UploadIconBean;
import com.brightcns.liangla.service.manager.DataManager;
import com.brightcns.liangla.service.view.UpLoadIconBaseView;
import com.brightcns.liangla.service.view.BaseView;

import okhttp3.RequestBody;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by 巩贺 on 2017/11/10.
 */

public class UpLoadIconPresenter implements Presenter {

    private CompositeSubscription mCompositeSubscription;
    private DataManager mDataManager;
    private UpLoadIconBaseView mUpLoadIconView;
    private UploadIconBean mUpiconBean;

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

    }

    @Override
    public void AttachView(BaseView baseView) {
        mUpLoadIconView = (UpLoadIconBaseView) baseView;
    }

    @Override
    public void attachIncomingIntent(Intent intent) {

    }

    public void upLoadIcon(String useToken, long timeStamp, String sign, String userId, RequestBody imgs){
            mCompositeSubscription.add(mDataManager.upLoadIcon(useToken,timeStamp,sign,userId,imgs)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Observer<UploadIconBean>() {
                @Override
                public void onCompleted() {
                    if (mUpiconBean!=null) {
                        mUpLoadIconView.onSuccess(mUpiconBean);
                    }
                }

                @Override
                public void onError(Throwable e) {
                        mUpLoadIconView.onError("请求失败！！");
                }

                @Override
                public void onNext(UploadIconBean uploadIconBean) {
                    mUpiconBean = uploadIconBean;
                }
            }));
    }
}
