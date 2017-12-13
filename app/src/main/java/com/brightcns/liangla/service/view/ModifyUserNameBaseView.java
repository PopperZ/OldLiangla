package com.brightcns.liangla.service.view;

import com.brightcns.liangla.service.entity.UpdataUserNameBean;

/**
 * Created by 巩贺 on 2017/11/10.
 */

public interface ModifyUserNameBaseView extends BaseView {
    void onSuccess(UpdataUserNameBean updataUserNameBean);
    void onError(String result);
}
