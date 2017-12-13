package com.brightcns.liangla.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.brightcns.liangla.R;

import java.util.ArrayList;
import java.util.List;

import com.brightcns.liangla.greenDao.dao.RidingWayDao;
import com.brightcns.liangla.greenDao.entity.Table_RidingWay;
import com.brightcns.liangla.greenDao.entity.Table_RidingWayDao;
import com.brightcns.liangla.ui.adapter.SplashViewAdapter;

public class SplashActivity extends BaseActivity implements View.OnClickListener,ViewPager.OnPageChangeListener{
    private static final int GETAPPVERSIONREQUESTPERMISSION = 1;
    /*引导页
        *
        * 使用viewpage来实现首次安装app时的引导页面；
        */
    private ViewPager splashView;
    private List<View> mViews;
    private SplashViewAdapter splashViewAdapter;
    //引导图片
    private static final int [] mPics={R.mipmap.img_load1, R.mipmap.img_load2,R.mipmap.img_load3};
    //底部小点
    private ImageButton[] mPoints;
    // 记录当前选择位置
    private int mCurrentIndex;
    private View mSplash;
    private TextView mGoExperience;
    //一次申请多个权限
    private String[] permissions = {Manifest.permission.READ_SMS, Manifest.permission
    .READ_PHONE_STATE};
    private LinearLayout mJump;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        init();//初始化控件
        ininPoints();//初始化底部小点
        splashViewAdapter=new SplashViewAdapter(mViews);
        splashView.setAdapter(splashViewAdapter);
        splashView.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //选中最后一页时显示按钮立即体验
                if (position == mViews.size() - 1) {
                    mGoExperience.setVisibility(View.VISIBLE);
                    mJump.setVisibility(View.GONE);
                } else {
                    mGoExperience.setVisibility(View.GONE);
                    mJump.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        splashView.setOnPageChangeListener(this);
        //初始化ridingway的表数据
        initRidingWayTable();
    }

    //初始化ridingway的表数据
    private void initRidingWayTable() {
        List<Table_RidingWay>list=new ArrayList<Table_RidingWay>();
        String []areaName={"松江pos","厦门BRT","松江有轨","上海地铁"};
        String []areaCode={"2016","3610","2016","2000"};
        String []businessType={"2","1","1","1"};
        String []bleType={"pos","metro","metro","metro"};
        String []QRType={"2","1","1","1"};
        for (int i=0;i<areaName.length;i++){
            Table_RidingWay ridingWay=new Table_RidingWay(Long.valueOf(i),areaName[i],areaCode[i],businessType[i],bleType[i],QRType[i]);
            list.add(ridingWay);
        }
        RidingWayDao.insertRidingway(list);
    }

    private void init(){
        mViews= new ArrayList<View>();
        mGoExperience= (TextView) findViewById(R.id.goexperience);
        mGoExperience.setOnClickListener(this);
        mJump = (LinearLayout) findViewById(R.id.ll_jump);
        mJump.setOnClickListener(this);
        splashView   = (ViewPager) findViewById(R.id.splashView);
        LayoutInflater inflater=getLayoutInflater();
        mSplash=inflater.inflate(R.layout.splashview,null);
        for (int i=0;i<mPics.length;i++){
            mSplash=inflater.inflate(R.layout.splashview,null);
            mSplash.setBackgroundResource(mPics[i]);
            mViews.add(mSplash);
        }
    }
    /**
     * 初始化底部小点
     * */

    private void ininPoints(){
        LinearLayout ll= (LinearLayout) findViewById(R.id.pointView);
        mPoints=new ImageButton[mPics.length];
        for (int i=0;i<mPoints.length;i++){
            mPoints[i]= (ImageButton) ll.getChildAt(i);
            mPoints[i].setTag(i);
            mPoints[i].setEnabled(false);//选中状态
        }
        mCurrentIndex=0;
        mPoints[mCurrentIndex].setEnabled(true);
    }
    /*
    * 设置当前的引导页
    * */
    private  void setCurView(int position ){
        if (position<0||position>=mPics.length){
            return;
        }
        mSplash.setBackgroundResource(mPics[position]);
    }
    /*
    * 引导小点的选中
    * */
    private void setCurPoint(int position){
        if (position<0||position>mPics.length||mCurrentIndex==position){
            return;
        }
        mPoints[mCurrentIndex].setImageResource(R.mipmap.point_normal);
        mPoints[position].setEnabled(true);
        mPoints[position].setImageResource(R.mipmap.point_click);
        mPoints[mCurrentIndex].setEnabled(false);
        mCurrentIndex=position;

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.goexperience:
//                    mGoExperience.setBackgroundResource(R.mipmap.splash_btn_b);
                startActivity(new Intent(SplashActivity.this,MainActivity.class));
                SplashActivity.this.finish();
                break;
            case R.id.ll_jump:
                startActivity(new Intent(SplashActivity.this,MainActivity.class));
                SplashActivity.this.finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }
    //在选择后的业务
    @Override
    public void onPageSelected(int position) {
        setCurPoint(position);
        setCurView(position);
    }


    @Override
    public void onPageScrollStateChanged(int state) {

    }

}
