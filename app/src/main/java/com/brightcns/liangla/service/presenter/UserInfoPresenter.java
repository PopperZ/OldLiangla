package com.brightcns.liangla.service.presenter;

import android.content.Intent;

import com.brightcns.liangla.app.MyApplication;
import com.brightcns.liangla.service.entity.UserInfo;
import com.brightcns.liangla.service.manager.DataManager;
import com.brightcns.liangla.service.view.UserInfoBaseView;
import com.brightcns.liangla.service.view.BaseView;
import com.brightcns.liangla.utils.AppUtil;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by 巩贺 on 2017/11/12.
 */

public class UserInfoPresenter implements Presenter {

    private DataManager mDataManager;
    private CompositeSubscription mCompositeSubscription;
    private UserInfo mCharacterInfo;
    private UserInfoBaseView mUserInfoView;
    private UserInfo mUserInfo;
    private UserInfo userInfo;

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
        mUserInfoView = (UserInfoBaseView) baseView;
    }

    @Override
    public void attachIncomingIntent(Intent intent) {

    }

    /*character*/
    public void getUserCcharacterInfo(){
        mCompositeSubscription.add(mDataManager.getUserInfo(AppUtil.getHeaderMap())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UserInfo>() {
                    @Override
                    public void onCompleted() {
                        mUserInfoView.onSuccess(mUserInfo);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mUserInfoView.onError("你还请求失败吗！");
                    }

                    @Override
                    public void onNext(UserInfo userInfo) {
                        mUserInfo = userInfo;
                    }
                }));
    }

}
