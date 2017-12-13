package com.brightcns.liangla.service.presenter;

import android.content.Intent;

import com.brightcns.liangla.service.view.BaseView;


/**
 * Created by 巩贺 on 2017/10/30.
 */

public interface Presenter {
    void onCreat();
    void onStart();//暂时没用到
    void onPause();//暂时没用到
    void onStop();
    void AttachView(BaseView baseView);
    void attachIncomingIntent(Intent intent);//暂时没用到
}
