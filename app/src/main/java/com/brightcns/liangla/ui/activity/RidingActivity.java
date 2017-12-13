package com.brightcns.liangla.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.Tag;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.brightcns.liangla.R;
import com.brightcns.liangla.greenDao.dao.RidingWayDao;
import com.brightcns.liangla.service.entity.QRBean;
import com.brightcns.liangla.service.presenter.RidingPresenter.BlePresenter;
import com.brightcns.liangla.service.presenter.RidingPresenter.QRPresenter;
import com.brightcns.liangla.service.presenter.RidingPresenter.RidingWayPresenter;
import com.brightcns.liangla.service.view.RidingView.BleView;
import com.brightcns.liangla.service.view.RidingView.QRView;
import com.brightcns.liangla.service.view.RidingView.RidingWayView;
import com.brightcns.liangla.utils.AppUtil;
import com.brightcns.liangla.utils.ConstantUtil;
import com.brightcns.liangla.utils.EncryptedUtil;
import com.brightcns.liangla.utils.LogUtil;
import com.brightcns.liangla.utils.SYSutils;
import com.brightcns.liangla.utils.ToastUtils;
import com.brightcns.liangla.weight.ChooseAgrPopupWindow;
import com.brightcns.liangla.zxing.encoding.EncodingUtils;


/*
* 业务流程
* 1.根据乘车区域显示基础界面
* 2.蓝牙模块处理
* 3.二维码模块
* 4.切换乘车业务模块
* 5.其他点击事件业务
*
* */

public class RidingActivity extends AppCompatActivity {
    @BindView(R.id.return_payraid)RelativeLayout mReturn;
    @BindView(R.id.online_recharge)TextView mRecharge;
    @BindView(R.id.rid_myRoute)TextView mMyRoute;
    @BindView(R.id.imgQR)ImageView imgQR;
    @BindView(R.id.ridpaytitle)TextView mTitle;
    @BindView(R.id.changeAgrCode) LinearLayout mChangAgrCode;

    //选择乘车区域模块
    RidingWayPresenter mRidingWayPresenter=new RidingWayPresenter(this);
    //蓝牙模块
    // TODO: 30/11/17 调整初始化位置
    private BlePresenter mBlePresenter=new BlePresenter(this);//蓝牙业务模块主持者
    //二维码模块
    private QRPresenter mQRPresentert=new QRPresenter(this);//二维码业务模块主持者

    public static LocalBroadcastManager mBroadcastManager;//本地广播对象
    private ChooseAgrPopupWindow mChooseAgrPopupWindow;//切换乘车业务（区域）对象

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riding);
        mBroadcastManager=LocalBroadcastManager.getInstance(this);//初始化本地广播
        mBroadcastManager.registerReceiver(localReceiver,makeGattUpdateIntentFilter());
        ButterKnife.bind(this);//注解绑定
        //乘车方式初始化一次
        mRidingWayPresenter.onCreat();
        mRidingWayPresenter.AttachView(mRidingWayView);
        //改变当前界面亮度为最大
        SYSutils.changeAppBrightness(RidingActivity.this);
        BleModule();//ble模块
        QRModule();//qr模块
        //区域划分
        ridingWay();
    }

    @OnClick({R.id.changeAgrCode,R.id.return_payraid})
    public void viewOnClick(View view){
        switch (view.getId()){
            case R.id.return_payraid:
                finish();
                break;
            case R.id.changeAgrCode:
                LogUtil.e("TAG","changeAgrCode");
                mChooseAgrPopupWindow=new ChooseAgrPopupWindow(RidingActivity.this);
//                mChooseAgrPopupWindow
                break;
            default:
                break;
        }
    }

    /**
     * 根据不同的乘车方式选择不同的
     * 通信方式
     * 二维码协议（后台进行具体二维码协议划分）
     * 订单处理方式
     * 是否脱机
     *
     */
    private void ridingWay() {
        mRidingWayPresenter.selectRidingWay("3610","1");
    }

    private void QRModule() {
        mQRPresentert.onCreat();
        mQRPresentert.AttachView(mQRView);
    }

    private void BleModule() {
        mBlePresenter.onCreat();
        mBlePresenter.AttachView(mBleView);
    }

    //乘车区域模块view
    RidingWayView mRidingWayView=new RidingWayView() {
        @Override
        public void go2Metro(String bleType) {
            LogUtil.e("mRidingWayView","metro");
            mBleView.onViewVisibility();
            mBlePresenter.bleInit(bleType);
        }

        @Override
        public void go2Pos(String bleType) {
            LogUtil.e("mRidingWayView","POS");
            mBleView.onViewVisibility();
            mBlePresenter.bleInit(bleType);
        }
    };

    //蓝牙业务模块view
    BleView mBleView=new BleView() {

        @Override
        public void onGattSuccess() {

        }

        @Override
        public void onGattFail() {

        }

        @Override
        public void onViewInVisibility() {
            imgQR.setVisibility(View.INVISIBLE);
        }

        @Override
        public void onViewVisibility() {
            imgQR.setVisibility(View.VISIBLE);
        }
    };


    QRView mQRView=new QRView() {
        @Override
        public void onSuccess(String qrBean) {
            imgQR.setVisibility(View.VISIBLE);
            imgQR.setImageBitmap(EncodingUtils.createQRCode(qrBean,450,450,null));
        }

        @Override
        public void onFail(String result) {

        }

    };

    //本地广播处理
    private final BroadcastReceiver localReceiver = new BroadcastReceiver() {
        String TAG = "localReceiver";
        @Override
        public void onReceive(Context context, Intent intent) {
            String action=intent.getAction();
            switch (action){
                case ConstantUtil.ACTION_BLESEND_SUCCESS:
                    // TODO: 30/11/17 广播发送成功获取QR
                    mQRPresentert.getQRMsg(AppUtil.getHeaderMap());
                    break;
                case ConstantUtil.ACTION_BLESEND_FAIL:
                    ToastUtils.showShort(RidingActivity.this,"广播发送失败，请尝试手机重启后再使用该功能！");
                    mBleView.onViewInVisibility();
                    break;
                case ConstantUtil.ACTION_QR_HIDE:
                    mBleView.onViewInVisibility();
                    break;
                case ConstantUtil.ACTION_QR_NEW:
                    LogUtil.e(TAG,"选择城市后刷新二维码（暂定此种情况）");
                    break;
                    default:
                        break;
            }
        }

    };

    //注册本地广播
    private static IntentFilter makeGattUpdateIntentFilter() {
        final IntentFilter intentFilter = new IntentFilter();
//        intentFilter.addAction(ConstantUtil.ACTION_GATT_CONNECTED);
//        intentFilter.addAction(ConstantUtil.ACTION_GATT_DISCONNECTED);
//        intentFilter.addAction(ConstantUtil.ACTION_GATT_WRITE);
//        intentFilter.addAction(ConstantUtil.ACTION_GATT_READDE);
        intentFilter.addAction(ConstantUtil.ACTION_BLESEND_FAIL);
        intentFilter.addAction(ConstantUtil.ACTION_BLESEND_SUCCESS);
//        intentFilter.addAction(ConstantUtil.ACTION_GATT_SVR_FAIL);
        intentFilter.addAction(ConstantUtil.ACTION_QR_NEW);
//        intentFilter.addAction(ConstantUtil.sACTION_QR_OPEN);
        intentFilter.addAction(ConstantUtil.ACTION_QR_HIDE);
//        intentFilter.addAction(ConstantUtil.ACTION_BLESEND_DATA);
//        intentFilter.addAction(ConstantUtil.ACTION_ENTER_STATION);
//        intentFilter.addAction(ConstantUtil.ACTION_EXIT_STATION);
//        intentFilter.addAction(ConstantUtil.ACTION_UNLAWFUL);
//        intentFilter.addAction(ConstantUtil.ACTION_SEND_ORDER);
//        intentFilter.addAction(ConstantUtil.HANDACK_THE_DEAL);
//        intentFilter.addAction(ConstantUtil.TRANSACK_THE_DEAL);
        return intentFilter;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBroadcastManager.unregisterReceiver(localReceiver);
        mBlePresenter.stopBleAdv();
    }
}
