package com.brightcns.liangla.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.StringUtils;
import com.brightcns.liangla.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import com.brightcns.liangla.ui.activity.LoginActivity;
import com.brightcns.liangla.ui.activity.MessageActivity;
import com.brightcns.liangla.ui.activity.SettingActivity;
import com.brightcns.liangla.utils.ConstantUtil;
import com.brightcns.liangla.utils.GlideUtil;
import com.brightcns.liangla.utils.PreferenceUtil;
import com.brightcns.liangla.weight.CircleImageView;

/**
 * Created by 巩贺 on 2017/11/7.
 */

public class EquityFragment extends Fragment {

    @BindView(R.id.icon_iv)
    ImageView mIconIv;
    @BindView(R.id.btn_logandregist)
    TextView mBtnLogandregist;
    @BindView(R.id.ll_unlogxml)
    RelativeLayout mLlUnlogxml;
    @BindView(R.id.ccimg_icon)
    CircleImageView mCcimgIcon;
    @BindView(R.id.tv_phonenum)
    TextView mTvPhonenum;
    @BindView(R.id.updataName)
    ImageView mUpdataName;
    @BindView(R.id.tv_credit)
    TextView mTvCredit;
    @BindView(R.id.rl_updataName)
    RelativeLayout mRlUpdataName;
    @BindView(R.id.rl_mineinformation)
    RelativeLayout mRlMineinformation;
    @BindView(R.id.rl_headview)
    RelativeLayout mRlHeadview;
    @BindView(R.id.iv_mypocket)
    ImageView mIvMypocket;
    @BindView(R.id.tv_mypocketmoneybalance)
    TextView mTvMypocketmoneybalance;
    @BindView(R.id.tv_yuan)
    TextView mTvYuan;
    @BindView(R.id.iv_next1)
    ImageView mIvNext1;
    @BindView(R.id.rl_mypocket)
    RelativeLayout mRlMypocket;
    @BindView(R.id.line_mine_belowmypocket)
    View mLineMineBelowmypocket;
    @BindView(R.id.line_mine_belowmybankcard)
    View mLineMineBelowmybankcard;
    @BindView(R.id.iv_cardsandtikeat)
    ImageView mIvCardsandtikeat;
    @BindView(R.id.iv_next4)
    ImageView mIvNext4;
    @BindView(R.id.rl_myroute)
    RelativeLayout mRlMyroute;
    @BindView(R.id.line_mine_belowmyrout)
    View mLineMineBelowmyrout;
    @BindView(R.id.iv_invitefrids)
    ImageView mIvInvitefrids;
    @BindView(R.id.rl_invitefriends)
    RelativeLayout mRlInvitefriends;
    @BindView(R.id.line_mine_belowinvitefriends)
    View mLineMineBelowinvitefriends;
    @BindView(R.id.iv_mymsg)
    ImageView mIvMymsg;
    @BindView(R.id.iv_next6)
    ImageView mIvNext6;
    @BindView(R.id.rl_mymsg)
    RelativeLayout mRlMymsg;
    @BindView(R.id.line_mine_belowmymsg)
    View mLineMineBelowmymsg;
    @BindView(R.id.linr_mine_topsetting)
    View mLinrMineTopsetting;
    @BindView(R.id.iv_mysetting)
    ImageView mIvMysetting;
    @BindView(R.id.rl_mysetting)
    RelativeLayout mRlMysetting;
    Unbinder unbinder;
    private View mView;
    public static final int REQUESTCODE_LOGIN = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_equity, null);
        }
        unbinder = ButterKnife.bind(this, mView);
        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    private void initData() {
        boolean isLogin = PreferenceUtil.getBoolean(ConstantUtil.ISLOGIN, false);
        if (isLogin) {
            mLlUnlogxml.setVisibility(View.GONE);
            mRlMineinformation.setVisibility(View.VISIBLE);
            ViewGroup.LayoutParams layoutParams = mRlHeadview.getLayoutParams();
            layoutParams.height = ((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 155, getResources().getDisplayMetrics()));
            setPhonenum();
            String userIconPath = PreferenceUtil.getString(ConstantUtil.AVATAR, "");
            if (StringUtils.isEmpty(userIconPath)) {
                GlideUtil.glideCircleDrawable(getActivity().getApplicationContext(), mCcimgIcon, R.mipmap.mine_oritoux);
            } else {
                GlideUtil.glideCircleAvatar(getActivity().getApplicationContext(), mCcimgIcon, userIconPath);
            }
//            mCreditNum.setText(PreferenceUtil.getString(ConstantUtil.INTEGRAL, "0"));
        } else {
            mLlUnlogxml.setVisibility(View.VISIBLE);
            mRlMineinformation.setVisibility(View.GONE);
            ViewGroup.LayoutParams layoutParams = mRlHeadview.getLayoutParams();
            layoutParams.height = ((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 196, getResources().getDisplayMetrics()));
//            mBackimgbtn.setImageResource(R.mipmap.ic_del_wd);
        }

    }

    private void setPhonenum() {
        String mineName = PreferenceUtil.getString(ConstantUtil.PHONENUM, "");
        if (mineName.equals("")) {
            mineName = mineName.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
        }
        mTvPhonenum.setText(mineName);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.ll_unlogxml, R.id.rl_mypocket, R.id.rl_myroute, R.id.rl_invitefriends, R.id.rl_mymsg, R.id.rl_mysetting})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_unlogxml:
                startActivityForResult(new Intent(getContext(), LoginActivity.class), REQUESTCODE_LOGIN);
                break;
            case R.id.rl_mypocket:
                if (PreferenceUtil.getBoolean(ConstantUtil.ISLOGIN, false)) {

                } else {
                    startActivityForResult(new Intent(getContext(), LoginActivity.class), REQUESTCODE_LOGIN);
                }
                break;
            case R.id.rl_myroute:
                if (PreferenceUtil.getBoolean(ConstantUtil.ISLOGIN, false)) {

                } else {
                    startActivityForResult(new Intent(getContext(), LoginActivity.class), REQUESTCODE_LOGIN);
                }
                break;
            case R.id.rl_invitefriends:
                break;
            case R.id.rl_mymsg:
                if (PreferenceUtil.getBoolean(ConstantUtil.ISLOGIN, false)) {
                    Intent intent = new Intent(getContext(), MessageActivity.class);
                    startActivity(intent);
                } else {
                    startActivityForResult(new Intent(getContext(), LoginActivity.class), REQUESTCODE_LOGIN);
                }
                break;
            case R.id.rl_mysetting:
                startActivityForResult(new Intent(getContext(), LoginActivity.class), REQUESTCODE_LOGIN);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUESTCODE_LOGIN:
                initData();
                break;

        }
    }
}
