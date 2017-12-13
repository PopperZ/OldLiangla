package com.brightcns.liangla.service.presenter;

import java.util.ArrayList;

/**
 * Created by 巩贺 on 2017/7/20.
 */

public interface RequestRuntimePermissionListenner {
    void onPermissionGranted();
    void onPermissionDenied(ArrayList<String> deniedPermissions);
}
