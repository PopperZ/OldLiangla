package com.brightcns.liangla.ui.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.brightcns.liangla.R;
import com.brightcns.liangla.app.MyApplication;
import com.brightcns.liangla.service.entity.LoginBean;
import com.brightcns.liangla.service.entity.UserInfo;
import com.brightcns.liangla.service.presenter.AuthCodePresenter;
import com.brightcns.liangla.service.presenter.UserInfoPresenter;
import com.brightcns.liangla.service.view.LoginBaseView;
import com.brightcns.liangla.service.view.UserInfoBaseView;
import com.brightcns.liangla.utils.CheckErrorCode;
import com.brightcns.liangla.utils.ConstantUtil;
import com.brightcns.liangla.utils.MD5;
import com.brightcns.liangla.utils.NetRequestHelper;
import com.brightcns.liangla.weight.CustomTimerButton;
import com.tbruyelle.rxpermissions2.RxPermissions;

import com.brightcns.liangla.service.presenter.LoginPresenter;
import com.brightcns.liangla.utils.ACache;
import com.brightcns.liangla.utils.LogUtil;
import com.brightcns.liangla.utils.NetUtil;
import com.brightcns.liangla.utils.PreferenceUtil;
import com.brightcns.liangla.utils.TelephoneUtil;
import com.brightcns.liangla.utils.ToastUtils;

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private EditText mPhone, mAuthCode;
    private LinearLayout mFinish;
    private Button mLogin;
    private TextView mAgr;
    private CustomTimerButton mGetCode;
    private String mPhoneNum, mCode;
    private SharedPreferences mShareInit, mShare;
    private SharedPreferences.Editor mEditor, mEditedInit;
    private String mMobileToken;
    private ACache mCache;
    private String mNum;
    private String imei;
    private static final String PACKAGE_URL_SCHEME = "package:"; // 方案
    private CheckBox mCheck;
    private boolean mIsCheck = true;
    private SharedPreferences mMobileTokenShare;
    private SharedPreferences.Editor mMobileTokenEdited;
    private String code;

    private AuthCodePresenter mAuthCodePresenter = new AuthCodePresenter(this);
    private LoginPresenter mLoginPresenter = new LoginPresenter(this);
    private UserInfoPresenter mUserInfoPresenter = new UserInfoPresenter();
    private long lastClickTime;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
        mShare = getSharedPreferences("admin", MODE_PRIVATE);
        mEditor = mShare.edit();

        mAuthCodePresenter.onCreat();
        mLoginPresenter.onCreat();
        mUserInfoPresenter.onCreat();

        mMobileToken = NetRequestHelper.getMobileToken(LoginActivity.this);
        //初始化缓存管理诶
        mCache = ACache.get(LoginActivity.this);

    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuthCodePresenter.onStop();
        mLoginPresenter.onStop();
        mUserInfoPresenter.onStop();
    }

    /**
     * init view for this LoginActivity
     */
    private void initView() {
        initToolBar();
        mPhone = (EditText) findViewById(R.id.phone);
        mPhone.setOnClickListener(this);
        mAuthCode = (EditText) findViewById(R.id.authCode);

        mAuthCode.setOnClickListener(this);
        mLogin = (Button) findViewById(R.id.login);
        mLogin.setOnClickListener(this);
        mGetCode = (CustomTimerButton) findViewById(R.id.getCode);
        mGetCode.setOnClickListener(this);

        mCheck = (CheckBox) findViewById(R.id.checkbox);
        mCheck.setChecked(true);
        mCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mIsCheck = true;
                } else {
                    mIsCheck = false;
                }
            }
        });
        mAgr = (TextView) findViewById(R.id.lianglaagr);
        mAgr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, WebActivity.class);
                intent.putExtra("skipUrl", "http://doc.brightcns.com/appdoc/917082811.html");
                intent.putExtra("menuName", "亮啦用户注册协议");
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login:
                if (mIsCheck) {
                    mCode = mAuthCode.getText().toString().trim();
                    mNum = mPhone.getText().toString().trim();
                    if (mCode.isEmpty() || mNum.isEmpty()) {
                        ToastUtils.showNoRepetToast(LoginActivity.this, "验证码或手机号为空");
                    } else {
                        //预防多次点击请求发送重复的问题
                        long curClickTime = System.currentTimeMillis();
                        if ((curClickTime - lastClickTime) >= 1000) {
                            lastClickTime = curClickTime;
                            login();
                        }
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "请同意亮啦协议后再登陆", Toast.LENGTH_SHORT).show();
                }
                break;
/*            case R.id.agreement:
                Toast.makeText(LoginActivity.this, "协议", Toast.LENGTH_SHORT).show();
                if (NetUtil.getNetWorkState(LoginActivity.this) != -1) {
                } else {
                    ToastUtils.showShort(LoginActivity.this, "网络已断开！");
                }
                break;*/
            case R.id.getCode:
//                getImei();
                if (!mPhone.getText().toString().trim().matches("^(13|15|18|17)[0-9]{9}$")) {
                    Toast.makeText(this, "号码输入有误", Toast.LENGTH_SHORT).show();
                    if (mPhone.isFocused()) {
                        //已获取焦点
                    } else {
                        mPhone.requestFocus();//获得焦点
                    }
                } else {
                    mGetCode.setEnabled(true);
                    mGetCode.start();
                    if (NetUtil.getNetWorkState(LoginActivity.this) != -1) {
                        getCode();
                    } else {
                        ToastUtils.showShort(LoginActivity.this, "网络已断开！");
                    }

                }
                break;
        }
    }

    /**
     * &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
     */
    LoginBaseView mLoginView = new LoginBaseView() {
        @Override
        public void onSuccess(LoginBean loginBean) {
            if (loginBean.getCode() == 0) {
                PreferenceUtil.put(ConstantUtil.TOKENENDTIME, loginBean.getData().getTokenEndTime() + "");
                PreferenceUtil.put(ConstantUtil.USERID, loginBean.getData().getUserId());
                PreferenceUtil.put(ConstantUtil.USERTOKEN, loginBean.getData().getUserToken());
                PreferenceUtil.put(ConstantUtil.LOGINTOKEN, loginBean.getData().getLoginToken());
                PreferenceUtil.put(ConstantUtil.PHONENUM, mPhone.getText().toString().trim());
                PreferenceUtil.put(ConstantUtil.ISLOGIN, true);
                /*调一下获取信息的接口*/
//                LoginActivity.this.finish();
                getUserCharacter();

            } else {
                hideDialog();
                CheckErrorCode.checkErrorCodeAndToast(MyApplication.getGlobalContext(), loginBean.getCode());
            }
        }

        @Override
        public void onError(String result) {
            hideDialog();
            Toast.makeText(LoginActivity.this, result, Toast.LENGTH_SHORT).show();
        }
    };


    UserInfoBaseView mUserInfoView = new UserInfoBaseView() {
        @Override
        public void onSuccess(UserInfo userInfo) {
            LogUtil.e("TAG", userInfo.toString());
            if (userInfo.getCode() == 0) {
                //存储用户信息
                PreferenceUtil.put(ConstantUtil.RIDE_TIME, String.valueOf(userInfo.getData().getRideTime()));
                PreferenceUtil.put(ConstantUtil.PHONENUM, userInfo.getData().getPhone());
                PreferenceUtil.put(ConstantUtil.NICKNAME, userInfo.getData().getNickname());
                PreferenceUtil.put(ConstantUtil.AVATAR, userInfo.getData().getAvatar());
                PreferenceUtil.put(ConstantUtil.AUTOGRAPH, userInfo.getData().getAutograph());
                PreferenceUtil.put(ConstantUtil.USER_ACCOUNT, String.valueOf(userInfo.getData().getUserAccount()));
                PreferenceUtil.put(ConstantUtil.INTEGRAL, String.valueOf(userInfo.getData().getIntegral()));
                PreferenceUtil.put(ConstantUtil.GRADE, String.valueOf(userInfo.getData().getGrade()));
                PreferenceUtil.put(ConstantUtil.ISLOGIN, true);
                PreferenceUtil.put(ConstantUtil.LOW_CARBON, String.valueOf(userInfo.getData().getLowCarbon()));
                hideDialog();
                LoginActivity.this.finish();
            } else {
                hideDialog();
                CheckErrorCode.checkErrorCodeAndToast(MyApplication.getGlobalContext(), userInfo.getCode());
            }
        }

        @Override
        public void onError(String result) {
            hideDialog();
            ToastUtils.showShort(MyApplication.getGlobalContext(), result);
        }
    };


    //&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&

    private void login() {
        showDialog("拼命登录中...");
        mLoginPresenter.getLogin(mPhoneNum, mAuthCode.getText().toString().trim(), getImei());
        mLoginPresenter.AttachView(mLoginView);
    }


    private void getCode() {
        mPhoneNum = mPhone.getText().toString().trim();
        String timeStamp = NetRequestHelper.gettimestamp();
//        String md5 = "/api/v1/i/login/sms" + "?token=" + mMobileToken + "&timestamp=" + timeStamp;
        String readyForMd5 = "phone=" + mPhoneNum + "&service=login" + "532c28d5412dd75bf975fb951c740a30";
        String sign = MD5.stringToMD5(readyForMd5);

        mAuthCodePresenter.getAuthCode(mPhoneNum, "login", sign);
    }


    private void getUserCharacter() {

        mUserInfoPresenter.getUserCcharacterInfo();
        mUserInfoPresenter.AttachView(mUserInfoView);
    }


    /**
     * get the only identification for a phone
     */
    private String getImei() {
        new RxPermissions(this)
                .request(Manifest.permission.READ_PHONE_STATE)
                .subscribe(aBoolean -> {
                    if (aBoolean) {
                        imei = TelephoneUtil.getTeleInfo(LoginActivity.this).imei_1;
                    } else {
                        imei = null;
                    }
                });

        return imei;
    }


    private void showDialog() {
        AlertDialog dialog = new AlertDialog.Builder(LoginActivity.this).setTitle("还可以手动开启哦~").setMessage("可以前往设置->app->myapp->permission打开").setPositiveButton("确定!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startAppSettings();
            }
        }).show();
    }

    // 启动应用的设置
    private void startAppSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse(PACKAGE_URL_SCHEME + getPackageName()));
        startActivity(intent);
    }


    /**
     * init toobar
     */
    private void initToolBar() {
    /*init toolbar*/
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("");

        setSupportActionBar(mToolbar);

        ImageView mBack = (ImageView) findViewById(R.id.iv_back);
        mBack.setImageResource(R.mipmap.del);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginActivity.this.finish();
            }
        });
        /*设置标题栏*/
        TextView toolbarTitle = (TextView) findViewById(R.id.tv_toolbartitle);
        toolbarTitle.setText("用户登录");
    }


    @Override
    protected void onResume() {
        super.onResume();
    }
}
