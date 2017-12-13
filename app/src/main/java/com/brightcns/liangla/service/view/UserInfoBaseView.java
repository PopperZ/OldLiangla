package com.brightcns.liangla.service.view;

import com.brightcns.liangla.service.entity.UserInfo;

/**
 * Created by 巩贺 on 2017/11/12.
 */

public interface UserInfoBaseView extends BaseView {
    void onSuccess(UserInfo userInfo);
    void onError(String result);
}
