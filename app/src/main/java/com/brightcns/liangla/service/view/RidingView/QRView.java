package com.brightcns.liangla.service.view.RidingView;

import com.brightcns.liangla.service.view.BaseView;

import com.brightcns.liangla.service.entity.QRBean;

/**
 * Created by zhangfeng on 30/11/17.
 */

public interface QRView extends BaseView {
    void onSuccess(String  qrBean); //获取二维码成功
    void onFail(String result);//获取二维码失败
}
