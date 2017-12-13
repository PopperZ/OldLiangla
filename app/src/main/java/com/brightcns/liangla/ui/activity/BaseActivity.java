package com.brightcns.liangla.ui.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.brightcns.liangla.R;

import com.brightcns.liangla.service.presenter.RequestRuntimePermissionListenner;
import com.brightcns.liangla.utils.ActivityCollector;
import com.brightcns.liangla.weight.CustomProgressDialog;
import com.brightcns.liangla.utils.NetUtil;

public class BaseActivity extends AppCompatActivity{
    /**
     * 网络类型
     */
    private int netMobile;
    private CustomProgressDialog mCustomProgressDialog;
    private static RequestRuntimePermissionListenner mRequestRuntimePermissionListenner;
    private SharedPreferences mMobileTokenShare;
    private SharedPreferences.Editor mMobileTokenEdited;
    private SharedPreferences mShare;
    private SharedPreferences mShareAdmin;
    private SharedPreferences.Editor mEdited;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        ActivityCollector.addActivity(this);

        setTitlebarStyle();
        /*判断网路哟状态*/
        inspectNet();
        initSp();

        //初始化自定义Dialog  gonghe
        mCustomProgressDialog = CustomProgressDialog.createDialog(this);
        mCustomProgressDialog.setCancelable(false);
    }

    /**
     * 设置沉浸式状态栏
     */
    private void setTitlebarStyle() {
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            //状态栏为深色(BaseView.SYSTEM_UI_FLAG_LAYOUT_STABLE:为浅色)
            int option =  View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            decorView.setSystemUiVisibility(option);
//            getWindow().setNavigationBarColor(Color.TRANSPARENT);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    /**
     * init many sharepreferences
     */
    private void initSp() {
        mMobileTokenShare = getSharedPreferences("mobiletoken", MODE_PRIVATE);
        mMobileTokenEdited = mMobileTokenShare.edit();

        mShare = getSharedPreferences("Initialize", MODE_PRIVATE);
        mShareAdmin = getSharedPreferences("admin", Context.MODE_PRIVATE);
        mEdited = mShare.edit();
    }


    /**
     * 初始化时判断有没有网络
     */

    public boolean inspectNet() {
        this.netMobile = NetUtil.getNetWorkState(BaseActivity.this);

        if (netMobile == NetUtil.NETWORK_WIFI) {
            System.out.println("inspectNet：连接wifi");
        } else if (netMobile == NetUtil.NETWORK_MOBILE) {
            System.out.println("inspectNet:连接移动数据");
        } else if (netMobile == NetUtil.NETWORK_NONE) {
            System.out.println("inspectNet:当前没有网络");
        }
        return isNetConnect();
    }




    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

    /**
     * 判断有无网络 。
     *
     * @return true 有网, false 没有网络.
     */
    public boolean isNetConnect() {
        if (netMobile == NetUtil.NETWORK_WIFI) {
            return true;
        } else if (netMobile == NetUtil.NETWORK_MOBILE) {
            return true;
        } else if (netMobile == NetUtil.NETWORK_NONE) {
            return false;
        }
        return false;
    }

    public void showDialog(String msg) {
        mCustomProgressDialog.setMessage(msg);
        mCustomProgressDialog.show();
    }

    public void hideDialog() {
        mCustomProgressDialog.dismiss();
    }
}
