package com.brightcns.liangla.service.view.RidingView;


import com.brightcns.liangla.service.view.BaseView;

/**
 * Created by zhangfeng on 30/11/17.
 */

public interface BleView extends BaseView {
        void onGattSuccess();//通信成功
        void onGattFail();//通信失败
        void onViewInVisibility();//隐藏二维码控件
        void onViewVisibility();//显示二维码控件
}
