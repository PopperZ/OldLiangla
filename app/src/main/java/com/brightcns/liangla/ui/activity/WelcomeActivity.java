package com.brightcns.liangla.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.blankj.utilcode.util.EmptyUtils;
import com.brightcns.liangla.R;
import com.brightcns.liangla.service.downloadapk.NewUpdateNormalDialog;
import com.brightcns.liangla.service.view.AppvertionPresenterBaseView;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.brightcns.liangla.service.entity.AppVersionBean;
import com.brightcns.liangla.service.presenter.AppVertionPresenter;
import com.brightcns.liangla.utils.ConstantUtil;
import com.brightcns.liangla.utils.NetUtil;
import com.brightcns.liangla.utils.PreferenceUtil;

public class WelcomeActivity extends BaseActivity {
    // 定义一个布尔值
    private String TAG = "TAG";
    private int versionCode;
    private AppVertionPresenter mAppVertionPresenter = new AppVertionPresenter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        mAppVertionPresenter.onCreat();
        checkAppVersion();
    }

    AppvertionPresenterBaseView mAppvertionPresenterView = new AppvertionPresenterBaseView() {
        @Override
        public void onSuccess(AppVersionBean appVersionBean) {
            Log.e("TAG", "onSuccess: " + "成功毁掉");
            String apkUri = appVersionBean.getData().getApkUri();
            int remoteVersionCode = appVersionBean.getData().getVersionCode();
            String brief = appVersionBean.getData().getBrief();
            int currentAppVersion = getCurrentAppVersion();

            if (appVersionBean.getData().getStatus() == 1 && currentAppVersion < remoteVersionCode) {
                NewUpdateNormalDialog newUpdateNormalDialog = new NewUpdateNormalDialog(WelcomeActivity.this, apkUri);
                newUpdateNormalDialog.show();
            } else {
                skip2activity();
            }
        }

        @Override
        public void onError(String result) {
//            requestPermissiones();
            skip2activity();
        }
    };

    private int getCurrentAppVersion() {
        PackageManager packageManager = getPackageManager();
        PackageInfo packageInfo;
        try {
            packageInfo = packageManager.getPackageInfo(getPackageName(), 0);
            versionCode = packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

//$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$  ***++++

    private void checkAppVersion() {
        if (NetUtil.getNetWorkState(this) != -1) {
            getAppVersion();
        } else {
            Toast.makeText(this, "网络断开", Toast.LENGTH_SHORT).show();
//            requestPermissiones();
            skip2activity();
        }
    }


    @Override
    protected void onStop() {
        super.onStop();
        mAppVertionPresenter.onStop();
    }

    private void getAppVersion() {
        versionCode = getCurrentAppVersion();
        Log.e("TAG", "getAppVersion: 当前版本号" + versionCode);
        mAppVertionPresenter.getAppVertion(21, "xiamen", versionCode);
        mAppVertionPresenter.AttachView(mAppvertionPresenterView);
    }


    public void requestPermissiones() {
        new RxPermissions(this).request(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_PHONE_STATE)
                .subscribe(aBoolean -> {
                    if (aBoolean) {
                        skip2activity();
                    } else {
                        Toast.makeText(this, "拒绝权限将导致部分功能不好用！", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void skip2activity() {

        if (PreferenceUtil.getBoolean(ConstantUtil.ISFRIST, true)) {
            PreferenceUtil.put(ConstantUtil.ISFRIST, false);
            startActivity(new Intent(WelcomeActivity.this, SplashActivity.class));
            WelcomeActivity.this.finish();
        } else {
            startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
            finish();
        }

    }
}
