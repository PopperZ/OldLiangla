package com.brightcns.liangla.service.view;

import com.brightcns.liangla.service.entity.AppVersionBean;

/**
 * Created by 巩贺 on 2017/11/9.
 */

public interface AppvertionPresenterBaseView extends BaseView {
    void onSuccess(AppVersionBean appVersionBean);
    void  onError(String result);
}
