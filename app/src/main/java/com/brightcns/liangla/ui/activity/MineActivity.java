package com.brightcns.liangla.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.StringUtils;
import com.brightcns.liangla.R;

import com.brightcns.liangla.service.entity.UpdataUserNameBean;
import com.brightcns.liangla.service.entity.UploadIconBean;
import com.brightcns.liangla.service.presenter.ModifyUserNamePresenter;
import com.brightcns.liangla.service.presenter.UpLoadIconPresenter;
import com.brightcns.liangla.service.view.ModifyUserNameBaseView;
import com.brightcns.liangla.service.view.UpLoadIconBaseView;
import com.brightcns.liangla.utils.CheckErrorCode;
import com.brightcns.liangla.weight.CircleImageView;
import com.brightcns.liangla.utils.ConstantUtil;
import com.brightcns.liangla.utils.GlideUtil;
import com.brightcns.liangla.utils.LogoutActivityCollector;
import com.brightcns.liangla.utils.PreferenceUtil;
import com.brightcns.liangla.weight.SelectPicPopupWindow;
import com.brightcns.liangla.utils.ToastUtils;

public class MineActivity extends BaseActivity implements View.OnClickListener {

    private SharedPreferences mShare;
    private SharedPreferences.Editor mEditor;
    private LinearLayout mReducell;
    private RelativeLayout mMineInformation;
    private RelativeLayout mUnlogxml;
    private ImageButton mBackimgbtn;
    private TextView mPhnumtv;
    private ImageView mCircleImg, mUpdataName;
    private CircleImageView mPerIcon;
    private String TAG = "TAG";
    private RelativeLayout mHeadViewrl;
    private AlertDialog mAlertDialog;
    private String mName;
    private SelectPicPopupWindow mPopupWindow;
    protected static final int CHOOSE_PICTURE = 0;
    protected static final int TAKE_PICTURE = 1;
    private static final int CROP_SMALL_PICTURE = 2;
    private Bitmap mPhoto;
    private RelativeLayout mMiles;
    private TextView mCreditNum;
    private TextView mMyTrip;
    private TextView mRecharge;
    private TextView mPocketnum;
    private double availableQuota = 0;
    private ModifyUserNamePresenter mModifyUserNamePresenter = new ModifyUserNamePresenter();
    private UpLoadIconPresenter mUpLoadIconPresenter = new UpLoadIconPresenter();
    private TextView mYuantv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine);
        LogoutActivityCollector.addActivity(this);
        ImmersiveStatusBar();
        initView();
        initData();
    }

    private void initData() {
        boolean isLogin = PreferenceUtil.getBoolean(ConstantUtil.ISLOGIN, false);
        if (isLogin) {
            mUnlogxml.setVisibility(View.GONE);
            mMineInformation.setVisibility(View.VISIBLE);
            mReducell.setVisibility(View.VISIBLE);
            mMiles.setVisibility(View.VISIBLE);
            ViewGroup.LayoutParams layoutParams = mHeadViewrl.getLayoutParams();
            layoutParams.height = ((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 155, getResources().getDisplayMetrics()));
            setPhonenum();
            String userIconPath = PreferenceUtil.getString(ConstantUtil.AVATAR, "");
            if (StringUtils.isEmpty(userIconPath)) {
                GlideUtil.glideCircleDrawable(getApplicationContext(), mCircleImg, R.mipmap.mine_oritoux);
            } else {
                GlideUtil.glideCircleAvatar(getApplicationContext(), mCircleImg, userIconPath);
            }
//            mCreditNum.setText(PreferenceUtil.getString(ConstantUtil.INTEGRAL, "0"));
        } else {
            mUnlogxml.setVisibility(View.VISIBLE);
            mMineInformation.setVisibility(View.GONE);
            mReducell.setVisibility(View.GONE);
            mMiles.setVisibility(View.GONE);
            ViewGroup.LayoutParams layoutParams = mHeadViewrl.getLayoutParams();
            layoutParams.height = ((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 196, getResources().getDisplayMetrics()));
            mBackimgbtn.setImageResource(R.mipmap.ic_del_wd);
        }

    }


    @Override
    protected void onStop() {
        super.onStop();
        mModifyUserNamePresenter.onStop();
        mUpLoadIconPresenter.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogoutActivityCollector.removeActivity(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_reduction:
//                modifyPetName(v);
                break;

            case R.id.rl_mysetting:
                startActivity(new Intent(MineActivity.this, SettingActivity.class));
                break;
            case R.id.rl_mineinformation:

                break;
            case R.id.ll_unlogxml:
                startActivity(new Intent(MineActivity.this, LoginActivity.class));
                break;
            case R.id.rl_equity:
                break;
            case R.id.rl_myroute:
                break;
            case R.id.rl_mypocket:
                break;
            case R.id.rl_mybankcard:
                break;
            case R.id.rl_mymsg:
                if (mShare.getBoolean("isLogin", false)) {
                    Intent intent = new Intent(MineActivity.this, MessageActivity.class);
                    startActivity(intent);
                } else {
                    startActivity(new Intent(MineActivity.this, LoginActivity.class));
                }
                break;
            case R.id.rl_invitefriends:
                break;
            case R.id.rl_updataName:
                startActivity(new Intent(this, EditUserInforActivity.class));
                break;
            case R.id.ccimg_icon:
                startActivity(new Intent(this, EditUserInforActivity.class));
                break;

            default:
                break;
        }
    }

    /**
     * All BaseView operat the data from server
     */
    UpLoadIconBaseView mUpLoadIconView = new UpLoadIconBaseView() {
        @Override
        public void onSuccess(UploadIconBean uploadIconBean) {
            if (uploadIconBean.getCode() == 0) {

            } else {
                CheckErrorCode.checkErrorCodeAndToast(MineActivity.this, uploadIconBean.getCode());
            }
        }

        @Override
        public void onError(String result) {
            Toast.makeText(MineActivity.this, result, Toast.LENGTH_SHORT).show();
        }
    };

    ModifyUserNameBaseView mModifyUserNameView = new ModifyUserNameBaseView() {
        @Override
        public void onSuccess(UpdataUserNameBean updataUserNameBean) {
            if (updataUserNameBean.getCode() == 0) {
                ToastUtils.showShort(MineActivity.this, "昵称修改成功！");
                mEditor.putString("name", mName);
                mEditor.commit();
                mPhnumtv.setText(mName);
            } else {
                CheckErrorCode.checkErrorCodeAndToast(MineActivity.this, updataUserNameBean.getCode());
            }
        }

        @Override
        public void onError(String result) {
            Toast.makeText(MineActivity.this, result, Toast.LENGTH_SHORT).show();
        }
    };

    /********************************************************************************************************************************************************/


    /**
     * make the statu bar transparent
     */
    private void ImmersiveStatusBar() {
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    /**
     * init view
     */
    private void initView() {
        mYuantv = (TextView) findViewById(R.id.tv_yuan);

        /*balance count TextView*/
        /*reduce*/
        mReducell = (LinearLayout) findViewById(R.id.ll_reduction);
        mReducell.setOnClickListener(this);

        ImageView back = (ImageView) findViewById(R.id.ivbtn_back);
        back.setOnClickListener(this);

        /*Setting*/
        RelativeLayout settingrl = (RelativeLayout) findViewById(R.id.rl_mysetting);
        settingrl.setOnClickListener(this);
        
        /*loged*/
        mMineInformation = (RelativeLayout) findViewById(R.id.rl_mineinformation);
        mMineInformation.setOnClickListener(this);
        
           /*unlog*/
        mUnlogxml = (RelativeLayout) findViewById(R.id.ll_unlogxml);
        mUnlogxml.setOnClickListener(this);

        /*back*/
        mBackimgbtn = (ImageButton) findViewById(R.id.ivbtn_back);
        mBackimgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MineActivity.this.finish();
            }
        });
        mHeadViewrl = (RelativeLayout) findViewById(R.id.rl_headview);
        /*my equity*/
        RelativeLayout myEquity = (RelativeLayout) findViewById(R.id.rl_equity);
        myEquity.setOnClickListener(this);

        /*log state*/
        mShare = getSharedPreferences("admin", MODE_PRIVATE);
        mEditor = mShare.edit();
        mPhnumtv = (TextView) findViewById(R.id.tv_phonenum);
        mCircleImg = (ImageView) findViewById(R.id.ccimg_icon);

        /*myroute */
        RelativeLayout myRoute = (RelativeLayout) findViewById(R.id.rl_myroute);
        myRoute.setOnClickListener(this);
        /*mypocket*/
        RelativeLayout myPocket = (RelativeLayout) findViewById(R.id.rl_mypocket);
        myPocket.setOnClickListener(this);
        /*mybankcard*/
        RelativeLayout myBankCard = (RelativeLayout) findViewById(R.id.rl_mybankcard);
        myBankCard.setOnClickListener(this);
        /*myMessage*/
        RelativeLayout myMessage = (RelativeLayout) findViewById(R.id.rl_mymsg);
        myMessage.setOnClickListener(this);

        /*invite friends*/
        RelativeLayout inviteFriends = (RelativeLayout) findViewById(R.id.rl_invitefriends);
        inviteFriends.setOnClickListener(this);
        mMiles = (RelativeLayout) findViewById(R.id.viewdd);


        RelativeLayout rlupdataName = (RelativeLayout) findViewById(R.id.rl_updataName);
        rlupdataName.setOnClickListener(this);

        mPerIcon = (CircleImageView) findViewById(R.id.ccimg_icon);
        mPerIcon.setOnClickListener(this);

        /*creadit number*/
        mCreditNum = (TextView) findViewById(R.id.tv_credit);
        /*mytrip*/
        mMyTrip = (TextView) findViewById(R.id.tv_myTrip);

        mRecharge = (TextView) findViewById(R.id.tv_mine_discharge);
        /*pocket*/
        mPocketnum = (TextView) findViewById(R.id.tv_mypocketmoneybalance);
    }


    private void setPhonenum() {
        String mineName = PreferenceUtil.getString(ConstantUtil.PHONENUM, "");
        if (mineName.equals("")) {
            mineName = mineName.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
        }
        mPhnumtv.setText(mineName);
    }

    /**
     * resume
     */
    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }
}
