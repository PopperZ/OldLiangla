package com.brightcns.liangla.service.presenter.RidingPresenter;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeAdvertiser;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.brightcns.liangla.service.presenter.Presenter;
import com.brightcns.liangla.service.view.BaseView;
import com.brightcns.liangla.utils.LogUtil;
import com.brightcns.liangla.utils.ToastUtils;

import com.brightcns.liangla.service.view.RidingView.BleView;
import com.brightcns.liangla.utils.BleUtils;
import com.brightcns.liangla.utils.SendBleAbvUtils;
import static android.content.Context.BLUETOOTH_SERVICE;

/**
 * Created by zhangfeng on 30/11/17.
 */

public class BlePresenter implements Presenter {
    private String TAG="BlePresenter";
    private BleView mBleView;
    private Context mContext;
    private int isSvrBroastcast = 0xFF;

    //ble
    private BluetoothAdapter mBleAdapter;//蓝牙适配器对象
    private BluetoothManager mBleManager;//蓝牙管理器对象
    private BluetoothLeAdvertiser mbleAdvertiser;//蓝牙广播对象
    private String mBleMacNoSplit;//蓝牙地址

    private SendBleAbvUtils mSendBleUtils;


    public BlePresenter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void onCreat() {
        mBleManager = (BluetoothManager) mContext.getSystemService(BLUETOOTH_SERVICE);//初始化蓝牙管理器的对象
        mBleAdapter = BluetoothAdapter.getDefaultAdapter();//初始化蓝牙适配器
        if (mBleManager==null){
            LogUtil.e(TAG,"mBleManager IS NULL");
        }else {
            LogUtil.e(TAG,"mBleManager not NULL");
        }
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }
    @Override
    public void AttachView(BaseView baseView) {
        mBleView= (BleView) baseView;
    }

    @Override
    public void attachIncomingIntent(Intent intent) {

    }

    //蓝牙状态判定
    public void bleInit(String bleType){
        LogUtil.e(TAG, "bleInit ");
        if (null==mBleManager){
            LogUtil.e(TAG, "该设备无法启用蓝牙");
            ToastUtils.showShort(mContext, "该设备无法启用蓝牙");
        }else {
            if (null==mBleAdapter){
                LogUtil.e(TAG, "该设备没有蓝牙设备");
                ToastUtils.showShort(mContext, "该设备没有蓝牙设备");
            }else {//有蓝牙设备
                //判断打开状态
                if (!mBleAdapter.isEnabled()){
                    LogUtil.e(TAG, "ble not is isEnabled");
                    mBleAdapter.enable();//打开蓝牙
                }else {
                    //判断蓝牙连接状态
                    LogUtil.e(TAG, "ble is isEnabled");
                    bleIsConnect(bleType);
                }
            }
        }
    }

    //判断蓝牙连接状态
    private void bleIsConnect(String bleType) {
        mbleAdvertiser = mBleAdapter.getBluetoothLeAdvertiser();//得到广播对象
        //判断是否支持ble
        if (null == mbleAdvertiser) {
            ToastUtils.showShort(mContext, "该设备不支持BLE");
        } else {
            // TODO: 2017/11/22 需要查阅 状态参数意义
            if (mBleAdapter.isOffloadedFilteringSupported()) {
                LogUtil.e(TAG, "isOffloadedFilteringSupported=true");
            } else {
                LogUtil.e(TAG, "isOffloadedFilteringSupported=false");
            }
            if (mBleAdapter.isOffloadedScanBatchingSupported()) {
                LogUtil.e(TAG, "isOffloadedScanBatchingSupported=true");
            } else {
                LogUtil.e(TAG, "isOffloadedScanBatchingSupported=false");
            }
            // 判断蓝牙的连接状态
            if (mBleAdapter.isDiscovering()) {
                ToastUtils.showShort(mContext, "蓝牙处于连接状态或未开启状态");
                LogUtil.e(TAG, "蓝牙处于连接状态");
                // TODO: 2017/11/22 测试连接状态下是否还可连
            } else {
                LogUtil.e(TAG, "蓝牙处于未连接状态");
                //获取蓝牙地址
                if (null== BleUtils.getBleMac(mContext)){
                    ToastUtils.showShort(mContext, "获取蓝牙地址失败！");
                }else {
                    mBleMacNoSplit=BleUtils.getBleMac(mContext);
                    sendBleAbv(bleType);
                }
            }
        }
    }


    //发送广播
    private void sendBleAbv(String bleType) {
        // TODO: 2017/11/22 广播是否发送判断测试
        Log.e(TAG, "发送广播操作");
        mSendBleUtils = new SendBleAbvUtils(mbleAdvertiser, mContext, BleUtils.bleGetMac(mBleMacNoSplit));
        if (isSvrBroastcast != 1) {
            Log.e(TAG, "蓝牙广播未开启");
            if (mbleAdvertiser != null) {
                //根据协议发送不同的广播（生成不同的广播通道）
                    if (bleType.equals("metro")){
                        mSendBleUtils.sendDoubleBleAbv(mBleManager);
                    }else if (bleType.equals("pos")){
                        // TODO: 30/11/17 松江pos协议暂未拿到
                    }
            } else {
                //广播对象为null
                ToastUtils.showShort(mContext, "蓝牙广播发送异常！");
            }
        } else {
            Log.e(TAG, "广播已开启");
            return;
        }
    }
    public void stopBleAdv(){
        mSendBleUtils.stopBleAbv();
    }
}
