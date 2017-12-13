package com.brightcns.liangla.service.view;

import com.brightcns.liangla.service.entity.MessageBean;

/**
 * Created by 巩贺 on 2017/11/10.
 */

public interface MyMessageBaseView extends BaseView {
    void onSuccess(MessageBean messageBean);
    void onError(String result);
}
