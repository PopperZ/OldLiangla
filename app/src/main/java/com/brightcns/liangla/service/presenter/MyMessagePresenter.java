package com.brightcns.liangla.service.presenter;

import android.content.Intent;

import com.brightcns.liangla.app.MyApplication;
import com.brightcns.liangla.service.RetrofitService;
import com.brightcns.liangla.service.entity.MessageBean;
import com.brightcns.liangla.utils.HttpsFactroy;
import com.google.gson.GsonBuilder;

import com.brightcns.liangla.service.view.BaseView;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by 巩贺 on 2017/11/10.
 */

public class MyMessagePresenter implements Presenter {

    private CompositeSubscription mCompositeSubscription;
    private Retrofit mRetrofit;
    private MessageBean mMessageBean;

    @Override
    public void onCreat() {
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

    public void getMyMessage(String mobileToken, long timeStamp, String sign, String userId) {
        mRetrofit = new Retrofit.Builder()
                .baseUrl("https://106.14.98.214/api/v1/i/users/")
                .client(HttpsFactroy.getClient(MyApplication.getGlobalContext()))
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        RetrofitService retrofitService = mRetrofit.create(RetrofitService.class);
        mCompositeSubscription.add(retrofitService.getMyMsg(mobileToken, timeStamp, sign, userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MessageBean>() {
                    @Override
                    public void onCompleted() {
                        if (mMessageBean!=null) {

                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(MessageBean messageBean) {
                        mMessageBean = messageBean;
                    }
                }));
    }
}
