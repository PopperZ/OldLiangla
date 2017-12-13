package com.brightcns.liangla.Ble;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattServer;
import android.bluetooth.BluetoothGattServerCallback;
import android.bluetooth.BluetoothGattService;
import android.content.Intent;
import android.util.Log;

import com.brightcns.liangla.utils.ConstantUtil;
import com.brightcns.liangla.utils.LogUtil;

import java.util.UUID;
import com.brightcns.liangla.ui.activity.RidingActivity;

/**
 * Created by zhangfeng on 30/11/17.
 */

public class DoubleMockServerCallBack extends BluetoothGattServerCallback {
    private static final String TAG="DoubleGattServer";
    private Activity mActivity;
    private BluetoothGattServer mGattServer;//蓝牙通道服务
    private static final int STATE_DISCONNECTED = 0;
    private static final int STATE_CONNECTED = 2;

    public DoubleMockServerCallBack(Activity mActivity) {
        this.mActivity = mActivity;
    }

    //设置服务
    public void setupServices(BluetoothGattServer gattServer)throws InterruptedException{
        if (gattServer==null){
            LogUtil.e(TAG,"gatt is null");
            return;
        }
        mGattServer=gattServer;
        // 设置GattService以及BluetoothGattCharacteristic
        //immediate alert
        BluetoothGattService service = new BluetoothGattService( UUID.fromString(ConstantUtil.SERVICESUUID),
                BluetoothGattService.SERVICE_TYPE_PRIMARY);

        //alert read char.
        BluetoothGattCharacteristic readAlc = new BluetoothGattCharacteristic(
                UUID.fromString(ConstantUtil.READUUID),
                BluetoothGattCharacteristic.PROPERTY_NOTIFY| BluetoothGattCharacteristic.PROPERTY_READ,
                BluetoothGattCharacteristic.PERMISSION_READ);

        //alert write char.
        BluetoothGattCharacteristic writeAlc = new BluetoothGattCharacteristic(
                UUID.fromString(ConstantUtil.WRITEUUID),
                BluetoothGattCharacteristic.PROPERTY_WRITE_NO_RESPONSE,
                BluetoothGattCharacteristic.PERMISSION_WRITE);
        readAlc.setValue("try add");
        //添加进服务
        service.addCharacteristic(writeAlc);
        service.addCharacteristic(readAlc);

        //服务添加进通道服务
        if(mGattServer!=null && service!=null) {
            mGattServer.addService(service);
        }
    }

    //服务添加回调
    @Override
    public void onServiceAdded(int status, BluetoothGattService service) {
        super.onServiceAdded(status, service);
        if (status== BluetoothGatt.GATT_SUCCESS){
            LogUtil.e(TAG,"GattServer add success server:"+service.getUuid().toString());
        }else {
            LogUtil.e(TAG,"GattServer add fail");
        }
    }

    //连接状态回调
    @Override
    public void onConnectionStateChange(BluetoothDevice device, int status, int newState) {
        super.onConnectionStateChange(device, status, newState);
        switch (newState){
            case STATE_CONNECTED:
                Intent BLEConnection=new Intent(ConstantUtil.ACTION_GATT_CONNECTED);
                BLEConnection.putExtra("connectedDevice",device.getAddress());
                ((RidingActivity)mActivity).mBroadcastManager.sendBroadcast(BLEConnection);
                break;
            case STATE_DISCONNECTED:
                ((RidingActivity)mActivity).mBroadcastManager.sendBroadcast(new Intent(ConstantUtil.ACTION_GATT_DISCONNECTED));
                break;
            default:
                break;
        }
    }

    //当有客户端来读数据时回调的接口
    @Override
    public void onCharacteristicReadRequest(BluetoothDevice device, int requestId, int offset, BluetoothGattCharacteristic characteristic) {
        super.onCharacteristicReadRequest(device, requestId, offset, characteristic);
        ((RidingActivity)mActivity).mBroadcastManager.sendBroadcast(new Intent(ConstantUtil.ACTION_GATT_READDE));
    }

    //当有客户端来写数据时回调的接口
    @Override
    public void onCharacteristicWriteRequest(BluetoothDevice device, int requestId, BluetoothGattCharacteristic characteristic, boolean preparedWrite, boolean responseNeeded, int offset, byte[] value) {
        super.onCharacteristicWriteRequest(device, requestId, characteristic, preparedWrite, responseNeeded, offset, value);
        mGattServer.sendResponse(device, requestId, BluetoothGatt.GATT_SUCCESS, offset, null);
        Intent intent=new Intent(ConstantUtil.ACTION_GATT_WRITE);
        intent.putExtra("bledata",value);
        intent.putExtra("size",value.length);
        Log.e(TAG,value.length+"size");
        ((RidingActivity)mActivity).mBroadcastManager.sendBroadcast(intent);
    }

    //暂未用到的回调

    public DoubleMockServerCallBack() {
        super();
    }

    //特征值属性被读
    @Override
    public void onDescriptorReadRequest(BluetoothDevice device, int requestId, int offset, BluetoothGattDescriptor descriptor) {
        super.onDescriptorReadRequest(device, requestId, offset, descriptor);
    }

    //特征值属性被写
    @Override
    public void onDescriptorWriteRequest(BluetoothDevice device, int requestId, BluetoothGattDescriptor descriptor, boolean preparedWrite, boolean responseNeeded, int offset, byte[] value) {
        super.onDescriptorWriteRequest(device, requestId, descriptor, preparedWrite, responseNeeded, offset, value);
    }

    //
    @Override
    public void onExecuteWrite(BluetoothDevice device, int requestId, boolean execute) {
        super.onExecuteWrite(device, requestId, execute);
    }

    //
    @Override
    public void onNotificationSent(BluetoothDevice device, int status) {
        super.onNotificationSent(device, status);
    }

    //
    @Override
    public void onMtuChanged(BluetoothDevice device, int mtu) {
        super.onMtuChanged(device, mtu);
    }

    //
    @Override
    public void onPhyUpdate(BluetoothDevice device, int txPhy, int rxPhy, int status) {
        super.onPhyUpdate(device, txPhy, rxPhy, status);
    }

    //
    @Override
    public void onPhyRead(BluetoothDevice device, int txPhy, int rxPhy, int status) {
        super.onPhyRead(device, txPhy, rxPhy, status);
    }
}
