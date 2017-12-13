package com.brightcns.liangla.service.presenter;

import android.content.Intent;

import com.brightcns.liangla.app.MyApplication;
import com.brightcns.liangla.service.entity.UpdataUserNameBean;
import com.brightcns.liangla.service.manager.DataManager;
import com.brightcns.liangla.service.view.BaseView;
import com.brightcns.liangla.service.view.ModifyUserNameBaseView;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by 巩贺 on 2017/11/10.
 */

public class ModifyUserNamePresenter implements Presenter {

    private CompositeSubscription mCompositeSubscription;
    private DataManager mDataManager;
    private ModifyUserNameBaseView mModifyUserNameView;
    private UpdataUserNameBean mUpdateUserNameBean;

    @Override
    public void onCreat() {
        mCompositeSubscription = new CompositeSubscription();
        mDataManager = new DataManager(MyApplication.getGlobalContext());
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
        mModifyUserNameView = (ModifyUserNameBaseView) baseView;
    }

    @Override
    public void attachIncomingIntent(Intent intent) {

    }

    public void modifyUserName(String userToken, long timeStamp, String sign, String userId, String userName) {
        mCompositeSubscription.add(mDataManager.modifyUserName(userToken, timeStamp, sign, userId, userName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UpdataUserNameBean>() {
                    @Override
                    public void onCompleted() {
                        if (mUpdateUserNameBean !=null) {
                            mModifyUserNameView.onSuccess(mUpdateUserNameBean);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mModifyUserNameView.onError("请求失败！");
                    }

                    @Override
                    public void onNext(UpdataUserNameBean updataUserNameBean) {
                        mUpdateUserNameBean =updataUserNameBean;
                    }
                }));
    }

}
