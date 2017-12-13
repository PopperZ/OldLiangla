package com.brightcns.liangla.service.view;

import com.brightcns.liangla.service.entity.LoginBean;

/**
 * Created by 巩贺 on 2017/11/9.
 */

public interface LoginBaseView extends BaseView {
    void onSuccess(LoginBean loginbean);
    void onError(String result);
}
