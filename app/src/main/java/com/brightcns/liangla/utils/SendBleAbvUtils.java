package com.brightcns.liangla.utils;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattServer;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.AdvertiseCallback;
import android.bluetooth.le.AdvertiseData;
import android.bluetooth.le.AdvertiseSettings;
import android.bluetooth.le.BluetoothLeAdvertiser;
import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.os.ParcelUuid;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import com.brightcns.liangla.Ble.DoubleMockServerCallBack;
import com.brightcns.liangla.ui.activity.RidingActivity;
/**
 * Created by zhangfeng on 7/9/17.
 */

public class SendBleAbvUtils {
    private static BluetoothLeAdvertiser mbleAdvertiser;//蓝牙广播对象
    private static BluetoothGattServer mBleGattServer;//蓝牙通信通道服务对象
    private static BluetoothGattCharacteristic mBleGattChar;//蓝牙通信通道特征值对象

    private static Context mContext;
    private static byte [] mData;
    private static Timer timerBleSvrInit;
    private static TimerTask task;

    public SendBleAbvUtils(BluetoothLeAdvertiser mbleAdvertiser, Context mContext, byte[] mData) {
        this.mbleAdvertiser = mbleAdvertiser;
        this.mContext = mContext;
        this.mData = mData;
    }


    //双通道广播
    public static void sendDoubleBleAbv(BluetoothManager mBleManager){
        String TAG="doubleBleAbv";
        DoubleMockServerCallBack mBleSvrMscCallBack;
        mBleSvrMscCallBack = new DoubleMockServerCallBack((Activity) mContext);
        mBleGattServer     = mBleManager.openGattServer(mContext, mBleSvrMscCallBack);
        if (mBleGattServer == null) {
            ToastUtils.showShort(mContext, "蓝牙服务开启失败");
            Log.e(TAG, "蓝牙服务开启失败");
            stopBleAbv();
            return;
        }
        try {
            mBleSvrMscCallBack.setupServices(mBleGattServer);
            //广播参数设定
            AdvertiseSettings.Builder localBuilder = new AdvertiseSettings.Builder();
            localBuilder.setAdvertiseMode(AdvertiseSettings.ADVERTISE_MODE_LOW_LATENCY);
            localBuilder.setConnectable(true);
            localBuilder.setTimeout(0);
            localBuilder.setTxPowerLevel(AdvertiseSettings.ADVERTISE_TX_POWER_HIGH);
            AdvertiseSettings svtAdvParm = localBuilder.setConnectable(true).build();
            //广播数据设定
            AdvertiseData.Builder svtAdvDataBuild = new AdvertiseData.Builder();
            svtAdvDataBuild.addServiceUuid(ParcelUuid.fromString(ConstantUtil.SERVICESUUID));
            svtAdvDataBuild.setIncludeDeviceName(false);
            svtAdvDataBuild.addManufacturerData(59, mData);
            AdvertiseData svtAdvData = svtAdvDataBuild.build();
            mbleAdvertiser.startAdvertising(svtAdvParm, svtAdvData, blsAdvSvrCallBack);//开始广播
            timerBleSvrInit = new Timer();
            task = new TimerTask() {//定时器2分钟后二维码失效

                @Override
                public void run() {
                    stopBleAbv();
                    Looper.prepare();
                    ToastUtils.showShort(mContext,"二维码失效");
                    Log.e("TAG","二维码失效");
                    Looper.loop();
                }
            };
            timerBleSvrInit.schedule(task, 2*60*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void stopBleAbv(){
        String TAG="bleSvrAdvStop";
        if (timerBleSvrInit != null) {
            timerBleSvrInit.cancel();
        }
        Log.e(TAG, "isSvrBroastcast============2 ");
        Log.e(TAG, "广播关闭操作");
        if (mBleGattServer == null) {
            Log.e(TAG, "广播已关闭1");
            return;
        }
        mBleGattServer.clearServices();
        mBleGattServer.close();
        mBleGattServer = null;//add by david 是否需要加？
        if (mbleAdvertiser == null) {
            Log.e(TAG, "广播已关闭2");
                return;
        }
        mbleAdvertiser.stopAdvertising(blsAdvSvrCallBack);
        mbleAdvertiser = null;
        Log.e(TAG, "关闭广播");
        ((RidingActivity)mContext).mBroadcastManager.sendBroadcast(new Intent(ConstantUtil.ACTION_QR_HIDE));
    }

    //广播回调
    private static AdvertiseCallback blsAdvSvrCallBack = new AdvertiseCallback() {
        String TAG="blsAdvSvrCallBack";
        @Override
        public void onStartSuccess(AdvertiseSettings settingsInEffect) {
            super.onStartSuccess(settingsInEffect);
            Log.e(TAG, "广播发送成功 " + settingsInEffect.toString());
            ((RidingActivity)mContext).mBroadcastManager.sendBroadcast(new Intent(ConstantUtil.ACTION_BLESEND_SUCCESS));
        }

        @Override
        public void onStartFailure(int errorCode) {
            super.onStartFailure(errorCode);
            Log.e(TAG, "广播发送失败:+错误码：" + errorCode);
            ((RidingActivity)mContext).mBroadcastManager.sendBroadcast(new Intent(ConstantUtil.ACTION_BLESEND_FAIL));
            switch (errorCode) {
                case ADVERTISE_FAILED_INTERNAL_ERROR:
                case ADVERTISE_FAILED_TOO_MANY_ADVERTISERS:
                    ToastUtils.showShort(mContext, "蓝牙开启广播失败，需要重启手机！！");
                    break;
                default:
                    break;
            }
        }
    };

    public  static  void sendData2Ble(byte[]data,BluetoothDevice mBleDevice){
        Log.e("sendData2Ble", BleUtils.bytes2HexString(data));
        mBleGattChar = mBleGattServer.getService(UUID.fromString(ConstantUtil.SERVICESUUID)).getCharacteristic(UUID.fromString(ConstantUtil.READUUID));
        mBleGattChar.setValue(data);
        mBleGattServer.notifyCharacteristicChanged(mBleDevice, mBleGattChar, true);
    }
}
