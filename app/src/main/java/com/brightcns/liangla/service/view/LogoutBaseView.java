package com.brightcns.liangla.service.view;

import com.brightcns.liangla.service.entity.LogoutBean;

/**
 * Created by 巩贺 on 2017/11/10.
 */

public interface LogoutBaseView extends BaseView {
    void onSuccess(LogoutBean logoutBean);
    void onError(String result);
}
