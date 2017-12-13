package com.brightcns.liangla.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.brightcns.liangla.R;

import com.brightcns.liangla.service.downloadapk.NewUpdateNormalDialog;
import com.brightcns.liangla.service.entity.LogoutBean;
import com.brightcns.liangla.service.presenter.LogoutPresenter;
import com.brightcns.liangla.service.view.LogoutBaseView;
import com.brightcns.liangla.utils.CheckErrorCode;
import com.brightcns.liangla.utils.ConstantUtil;
import com.brightcns.liangla.utils.LogoutActivityCollector;
import com.brightcns.liangla.utils.PreferenceUtil;

public class SettingActivity extends BaseActivity implements View.OnClickListener {
    private SharedPreferences mShared;
    private SharedPreferences.Editor mEditor;
    private TextView mPhone;
    private RelativeLayout mMLogout;
    private TextView mLogoutv;
    private RelativeLayout mMPhoneNum;
    private RelativeLayout mMPaySetting;
    private RelativeLayout mSettinglogout;
    private String usericonname = "usericon.png";
    private LogoutPresenter mLogoutPresenter = new LogoutPresenter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        LogoutActivityCollector.addActivity(this);
        mShared = getSharedPreferences("admin", MODE_PRIVATE);
        mEditor = mShared.edit();
        mLogoutPresenter.onCreat();
        initView();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mLogoutPresenter.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogoutActivityCollector.removeActivity(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_setting_phonenum:
                Toast.makeText(SettingActivity.this, "模块开发中...", Toast.LENGTH_SHORT).show();
                //                startActivity(new Intent(SettingActivity.this, ChangePhoneNumActivity.class));
                break;
            case R.id.paypwdsettingrl:
//                startActivity(new Intent(SettingActivity.this, PayPwdSettingActivity.class));
                break;
            case R.id.aboutusrl:
//                startActivity(new Intent(SettingActivity.this, AboutUsActivity.class));
//                overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
                break;
            case R.id.rl_setting_helpcenterrl:
//                startActivity(new Intent(SettingActivity.this, HelpCenterActivity.class));
                break;
            case R.id.setting_logoutrl:
                if (PreferenceUtil.getBoolean(ConstantUtil.ISLOGIN,false)) {
                    onSureLogOut();
                } else {
                    Toast.makeText(SettingActivity.this, "未登录", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_downloadapk:

                break;
            default:
                break;
        }
    }

    LogoutBaseView mLogoutView = new LogoutBaseView() {
        @Override
        public void onSuccess(LogoutBean logoutBean) {
            if (logoutBean.getCode() == 0) {

                startActivity(new Intent(SettingActivity.this,LoginActivity.class));
                LogoutActivityCollector.finishAllActivity();
            } else {
                CheckErrorCode.checkErrorCodeAndToast(SettingActivity.this, logoutBean.getCode());
            }
            PreferenceUtil.reset(getApplicationContext());
        }

        @Override
        public void onError(String result) {
            Toast.makeText(SettingActivity.this, result, Toast.LENGTH_SHORT).show();
        }
    };

    private void initView() {
        initToolBar();
        /*phone num*/
        mMPhoneNum = (RelativeLayout) findViewById(R.id.tv_setting_phonenum);
        mMPhoneNum.setOnClickListener(this);

        /*paypwdSetting*/
        mMPaySetting = (RelativeLayout) findViewById(R.id.paypwdsettingrl);
        mMPaySetting.setOnClickListener(this);
        /*about us*/
        RelativeLayout mAboutUs = (RelativeLayout) findViewById(R.id.aboutusrl);
        mAboutUs.setOnClickListener(this);
        /*helpceneter*/
        RelativeLayout mHelpCenter = (RelativeLayout) findViewById(R.id.rl_setting_helpcenterrl);
        mHelpCenter.setOnClickListener(this);

        mLogoutv = (TextView) findViewById(R.id.tv_logout);
        /*logout*/
        mSettinglogout = (RelativeLayout) findViewById(R.id.setting_logoutrl);
        mSettinglogout.setOnClickListener(this);
        /*logoutTextView*/
        TextView logoutTv = (TextView) findViewById(R.id.tv_logout);

        mPhone = (TextView) findViewById(R.id.spphonenumtv);
        Button downloadapk = (Button) findViewById(R.id.btn_downloadapk);
        downloadapk.setOnClickListener(this);
        initLogView();
    }

    private void initToolBar() {
    /*init toolbar*/
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        ImageView mBack = (ImageView) findViewById(R.id.iv_back);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingActivity.this.finish();
            }
        });
        /*设置标题栏*/
        TextView toolbarTitle = (TextView) findViewById(R.id.tv_toolbartitle);
        toolbarTitle.setText("设置");
    }

    /*登出的网络操作*/
    private void logout() {
        showDialog("正在退出...");
        mLogoutPresenter.getLogout();
        mLogoutPresenter.AttachView(mLogoutView);
    }

    public void onSureLogOut() {
        new AlertDialog.Builder(this).setTitle("确定退出吗？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(SettingActivity.this, "已退出", Toast.LENGTH_SHORT).show();
                        logout();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(SettingActivity.this, "取消", Toast.LENGTH_SHORT).show();
                    }
                }).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initLogView();
    }

    public void initLogView() {
        if (PreferenceUtil.getBoolean(ConstantUtil.ISLOGIN,false)) {
            mMPhoneNum.setVisibility(View.VISIBLE);
            mMPaySetting.setVisibility(View.VISIBLE);
            mSettinglogout.setVisibility(View.VISIBLE);
            String phoneNum = mShared.getString("phoneNum", "");
            String secretnum = phoneNum.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
            mPhone.setText(secretnum);
            //
        }
    }


}
