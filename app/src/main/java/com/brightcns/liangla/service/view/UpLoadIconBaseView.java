package com.brightcns.liangla.service.view;

import com.brightcns.liangla.service.entity.UploadIconBean;

/**
 * Created by 巩贺 on 2017/11/10.
 */

public interface UpLoadIconBaseView extends BaseView {
    void onSuccess(UploadIconBean uploadIconBean);
    void onError(String result);
}
