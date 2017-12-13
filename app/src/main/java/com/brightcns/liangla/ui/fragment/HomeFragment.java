package com.brightcns.liangla.ui.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.brightcns.liangla.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import com.brightcns.liangla.service.entity.HomeMsgBean;
import com.brightcns.liangla.service.entity.HomeSmallBannerDean;
import com.brightcns.liangla.ui.activity.FreeEquityActivity;
import com.brightcns.liangla.ui.activity.MineActivity;
import com.brightcns.liangla.ui.adapter.HomeSmallBannerAdapter;


/**
 * Created by 巩贺 on 2017/11/7.
 */

public class HomeFragment extends Fragment implements View.OnClickListener {

    private View mView;

    private SharedPreferences mShare;
    //定位间隔时间
    private static final int LOCATION_TIME = 2 * 60 * 1000;
    private String mCurrentLocation;
    private String TAG = "TAG";
    private ImageView mineiv;
    private ImageView mMore;
    private TextView mCitynametv;
    private LinearLayout mChooseCity;
    private ListView mMsgView;
    private SharedPreferences.Editor mEditor;
    private List<HomeMsgBean.DataBean> mHomemsgList;
    private ViewFlipper mMViewFlipper;
    private RecyclerView mMenusView;
    private RecyclerView ticketsrv;
    private List<HomeSmallBannerDean> mSmallBannerList = new ArrayList<>();
    //smallBanner图片
    private static final int[] mSmallBannerPics = {R.mipmap.home_smallban, R.mipmap.home_smallban2};
    private static final String[] mSmallBannerTitle = {"乘车积分兑换", "免费乘车活动"};


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_home, null);
            initView(mView);
            ImmersiveStatuBar();
        }
        return mView;
    }

    private void initView(View view) {
        mineiv = (ImageView) view.findViewById(R.id.iv_mine);
//        mineiv.setOnClickListener(this);
        mMViewFlipper = (ViewFlipper) view.findViewById(R.id.viewFlipper);
        mMore = (ImageView) view.findViewById(R.id.more);
        mMore.setOnClickListener(this);
        mMenusView = (RecyclerView) view.findViewById(R.id.menusView);
        mCitynametv = (TextView) view.findViewById(R.id.citynametv);
        mChooseCity = (LinearLayout) view.findViewById(R.id.choosecitylll);
        mChooseCity.setOnClickListener(this);
        ticketsrv = (RecyclerView) view.findViewById(R.id.rv_action);

        RelativeLayout eventrecomment = (RelativeLayout) view.findViewById(R.id.homefrag_eventrecom);
        eventrecomment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getContext(), "模块开发中...", Toast.LENGTH_SHORT).show();
            }
        });
        initBottomTickets();
    }

    private void initBottomTickets() {
        //设置底部活动部分数据
        for (int i = 0; i < mSmallBannerPics.length; i++) {
            HomeSmallBannerDean homeSmallBannerDean = new HomeSmallBannerDean(mSmallBannerTitle[i], mSmallBannerPics[i]);
            mSmallBannerList.add(homeSmallBannerDean);
        }
        //设置底部活动部分

        HomeSmallBannerAdapter homeSmallBannerAdapter = new HomeSmallBannerAdapter();

        ticketsrv.setLayoutManager(new GridLayoutManager(getActivity(), 2));
//        ticketsrv.addItemDecoration(new SpaceItemDecoration(ConvertUtils.dp2px(5)));
        ticketsrv.setAdapter(homeSmallBannerAdapter);
        homeSmallBannerAdapter.setNewData(mSmallBannerList);
        ticketsrv.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                // TODO: 2017/11/3
                if (position == 0) {
                } else if (position == 1) {
                    startActivity(new Intent(getActivity(), FreeEquityActivity.class));
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_mine:
                Intent intent = new Intent(getContext(), MineActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }


    /**
     * 沉浸式状态栏
     */
    private void ImmersiveStatuBar() {
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getActivity().getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getActivity().getWindow().setStatusBarColor(Color.TRANSPARENT);
            //透明状态栏
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

    }

}
