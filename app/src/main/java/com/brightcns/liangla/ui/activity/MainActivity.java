package com.brightcns.liangla.ui.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.brightcns.liangla.R;
import com.brightcns.liangla.service.entity.Book;
import com.brightcns.liangla.service.presenter.AppVertionPresenter;
import com.brightcns.liangla.service.view.BookBaseView;
import com.brightcns.liangla.ui.adapter.FrageStatePagerAdapter;
import com.brightcns.liangla.ui.fragment.EquityFragment;
import com.brightcns.liangla.ui.fragment.HomeFragment;
import com.brightcns.liangla.utils.SYSutils;
import com.brightcns.liangla.utils.ViewAnimatorUtil;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private static final int GETAPPVERSIONREQUESTPERMISSION = 1;
    private ArrayList<Fragment> mFragmentList;
    private LinearLayout mHome, mEquity;
    private RelativeLayout mBottom, mRectView, mCircleView, mPullView;
    private TextView mHomeName, mEquityName;
    private ImageView mHomeIcon, mEquityIcon, mRect, mCircle;
    private ImageButton mPull;
    private ViewPager mViewPager;
    private long mExitTime;
    private SharedPreferences.Editor mEdited = null;
    private SharedPreferences mShare = null;
    private SharedPreferences mShareAdmin = null;

    //定位城市
    public static String mCityName;
    private boolean isopen = true;
    private float density = 0;//像素密度
    float DownY = 0;
    float moveY;
    private String TAG = "MainActivity";
    private static final String PACKAGE_URL_SCHEME = "package:"; // 方案
    private HomeFragment mHomeFragment;
    private EquityFragment mEquityFragment;
    private AppVertionPresenter mAppVertionPresenter;
    //    private GetAppverSionSuccessListaner mGetAppversionListener;

    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initLocation();
        initView();
        density = SYSutils.getDensity(MainActivity.this);
        initFragment();
        onClick(findViewById(R.id.home));
        mShare = getSharedPreferences("Initialize", MODE_PRIVATE);
        mShareAdmin = getSharedPreferences("admin", Context.MODE_PRIVATE);
        mEdited = mShare.edit();
        //动态获取权限
        getRuntimePermissionFromBaseActivity();
    }

    private void initLocation() {

//初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
//设置定位回调监听
        mLocationClient.setLocationListener(mAMapLocationListener);
//初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);

        //获取一次定位结果：
//该方法默认为false。
        mLocationOption.setOnceLocation(true);

//获取最近3s内精度最高的一次定位结果：
//设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
        mLocationOption.setOnceLocationLatest(true);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
//启动定位
        mLocationClient.startLocation();

    }

    //可以通过类implement方式实现AMapLocationListener接口，也可以通过创造接口类对象的方法实现
//以下为后者的举例：
    AMapLocationListener mAMapLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation amapLocation) {
            if (amapLocation != null) {
                if (amapLocation.getErrorCode() == 0) {
                    //可在其中解析amapLocation获取相应内容。
                    Log.e(TAG, "onLocationChanged: 当前 城市" + amapLocation.getCity());
                    // TODO: 2017/12/4 停止定位的时机是在使用完定位获取的信息后的地方
                    mLocationClient.stopLocation();
                } else {
                    //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                    Log.e("AmapError", "location Error, ErrCode:"
                            + amapLocation.getErrorCode() + ", errInfo:"
                            + amapLocation.getErrorInfo());
                    mLocationClient.stopLocation();
                }
            }
        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    BookBaseView mBookView = new BookBaseView() {
        @Override
        public void onSuccess(Book mBook) {
            Toast.makeText(MainActivity.this, mBook.toString(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(String result) {
            Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();
        }
    };

    private void getRuntimePermissionFromBaseActivity() {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(aBoolean -> {
                    if (aBoolean) {

                    } else {

                    }
                });

    }


    private void initFragment() {
        mFragmentList = new ArrayList<Fragment>();
        mHomeFragment = new HomeFragment();
        mEquityFragment = new EquityFragment();
        mFragmentList.add(mHomeFragment);
        mFragmentList.add(mEquityFragment);
        mViewPager.setAdapter(new FrageStatePagerAdapter(getSupportFragmentManager(), mFragmentList));
    }

    //初始化控件
    private void initView() {
        // 底部导航栏
        mBottom = (RelativeLayout) findViewById(R.id.bottom_view);
        mRectView = (RelativeLayout) findViewById(R.id.rect);
        mRectView.setOnClickListener(this);
        mCircleView = (RelativeLayout) findViewById(R.id.circle);
        mCircleView.setOnClickListener(this);
        mPullView = (RelativeLayout) findViewById(R.id.pull_view);
        mPullView.setOnClickListener(this);
        mPull = (ImageButton) findViewById(R.id.pull);

        //首页
        mHome = (LinearLayout) findViewById(R.id.home);
        mHomeName = (TextView) findViewById(R.id.homeName);
        mHomeIcon = (ImageView) findViewById(R.id.homeIcon);
        //权益
        mEquity = (LinearLayout) findViewById(R.id.equity);
        mEquityName = (TextView) findViewById(R.id.equityName);
        mEquityIcon = (ImageView) findViewById(R.id.equityIcon);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);

        mHome.setOnClickListener(this);
        mEquity.setOnClickListener(this);
        btnTouch();
        onClick(findViewById(R.id.home));


        setmViewPagerLister();
    }

    //触摸事件
    private void btnTouch() {
        mBottom.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Log.e("TAG", "ACTION_DOWN");
                        DownY = event.getY();//float DownY
                        break;
                    case MotionEvent.ACTION_MOVE:
                        Log.e("TAG", "ACTION_MOVE");
                        moveY = event.getY() - DownY;//y轴距离
                        break;
                    case MotionEvent.ACTION_UP:
                        Log.e("TAG", "ACTION_UP");
                        if (moveY >= 10) {
                            Log.e("TAG", "ACTION_MOVE   >=10");
                            changeView();
                        } else if (moveY <= -10) {
                            changeView();
                        }
                        break;
                }
                return true;
            }
        });
    }

    //底部导航栏效果
    private void changeView() {
        if (isopen == false) {
            isopen = true;
            int height = mBottom.getMeasuredHeight();
            ViewAnimatorUtil.animHeightToView(mBottom, height, (int) (188 * density), true, 300);
            mPull.setBackground(getResources().getDrawable(R.mipmap.bottom_pull_down));
            mBottom.setBackground(getResources().getDrawable(R.mipmap.bottom_bg_big));

            mCircleView.setVisibility(View.VISIBLE);
            mRectView.setVisibility(View.GONE);


        } else {
            int height = mBottom.getMeasuredHeight();
            ViewAnimatorUtil.animHeightToView(mBottom, height, (int) (83 * density), true, 300);
            mPull.setBackground(getResources().getDrawable(R.mipmap.bottom_pull_up));
            mBottom.setBackground(getResources().getDrawable(R.mipmap.bottom_bg_big));
            isopen = false;
            //                    button.rectToCirWidthChange();
            Timer timer = new Timer();
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mCircleView.setVisibility(View.GONE);
                            mRectView.setVisibility(View.VISIBLE);
                        }
                    });
                }
            };
            timer.schedule(task, 300);

        }
    }


    //点击事件处理
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.home:
                Log.e(TAG, "onClick: Home");
                mViewPager.setCurrentItem(0);
                mHomeName.setTextColor(getResources().getColor(R.color.PrimaryDark));
                mHomeIcon.setImageResource(R.mipmap.home_click);
                mEquityIcon.setImageResource(R.mipmap.equity_normal);
                mEquityName.setTextColor(getResources().getColor(R.color.homewordnormal));
                break;
            case R.id.equity:
                Log.e(TAG, "onClick: Equity");
                mViewPager.setCurrentItem(1);
                break;
            case R.id.rect:
                go2Riding();
                break;
            case R.id.circle:
                Log.e(TAG, "onClick: dd");
                go2Riding();
                break;
            case R.id.pull_view:
                break;
            default:
                break;
        }
    }

    //跳转到乘车界面
    private void go2Riding() {
        startActivity(new Intent(MainActivity.this, RidingActivity.class));
    }

    //viewpager的监听事件
    private void setmViewPagerLister() {
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setMenuStyle(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    //设置menu样式
    private void setMenuStyle(int vID) {
        if (vID == 0) {
            mHomeIcon.setImageResource(R.mipmap.home_click);
            mHomeName.setTextColor(getResources().getColor(R.color.PrimaryDark));
        } else {
            mHomeIcon.setImageResource(R.mipmap.home_normal);
            mHomeName.setTextColor(getResources().getColor(R.color.homewordnormal));
        }
        if (vID == 1) {
            mEquityIcon.setImageResource(R.mipmap.equity_click);
            mEquityName.setTextColor(getResources().getColor(R.color.PrimaryDark));
        } else {
            mEquityIcon.setImageResource(R.mipmap.equity_normal);
            mEquityName.setTextColor(getResources().getColor(R.color.homewordnormal));
        }
    }

    //防止UI重叠
    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        if (mHomeFragment == null && fragment instanceof HomeFragment) {
            mHomeFragment = (HomeFragment) fragment;
        } else if (mEquityFragment == null && fragment instanceof EquityFragment) {
            mEquityFragment = (EquityFragment) fragment;
        }
    }

    //两次返回才退出程序
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
            } else {
                mEdited.remove("latestVersion");
                mEdited.remove("status");
                mEdited.commit();
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
