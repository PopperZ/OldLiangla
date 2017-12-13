package com.brightcns.liangla.utils;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;

import net.vidageek.mirror.dsl.Mirror;

/**
 * Created by zhangfeng on 30/11/17.
 */

public class BleUtils {
    //通道参数处理
    public static byte[] bleGetMac(String bleMacNoSplit) {
        //通信通道的参数
        String strTemp = "";
        byte[] ble = new byte[6];
        for (int i = 0; i < 6; i++) {
            strTemp = bleMacNoSplit.substring(i * 2, i * 2 + 2);
            ble[i] = Integer.valueOf(strTemp, 16).byteValue();
        }
        return ble;
    }

    //获取蓝牙地址
    public static String getBleMac(Context context) {
        String tag="getBleMac";
        Object bluetoothManagerService = new Mirror().on(BluetoothAdapter.getDefaultAdapter()).get().field("mService");
        if (bluetoothManagerService == null) {
            LogUtil.e(tag, "---------------couldn't find bluetoothManagerService");
            return null;
        } else {
            Object address = new Mirror().on(bluetoothManagerService).invoke().method("getAddress").withoutArgs();
            if (address != null && address instanceof String) {
                String bleMac = address.toString();
                String bleMacNoSplit = bleMac.replaceAll(":", "");
                LogUtil.e(tag, "真实的蓝牙地址：" + address);
                return bleMacNoSplit;
            } else {
                LogUtil.e(tag, "不能得到真实的蓝牙地址 ");
                ToastUtils.showShort(context, "不能得到手机真实的蓝牙地址");
                return null;
            }
        }
    }

        //byte数组转16进制字符串
    public static String bytes2HexString(byte[] b)
    {
        String ret = "";
        for (int i = 0; i < b.length; i++)
        {
            String hex = Integer.toHexString(b[ i ] & 0xFF);
            if (hex.length() == 1)
            {
                hex = '0' + hex;
            }
            ret += hex.toUpperCase();
        }
        return ret;
    }

    //十六进制字符转换为byte
    public static byte strHex2Byte(String pStrValue)
    {
        byte bRet = 0x00;
        bRet = Integer.valueOf(pStrValue, 16).byteValue();
        //bRet = Integer.getInteger(pStrValue, 16).byteValue();
        return  bRet;
    }

    //十六进制字符转换为int
    public static int strHex2Int(String pStrValue)
    {
        int nRet = 0x00;
        if (pStrValue.equals(""))
        {
            return 0;
        }
        if (pStrValue.length()>7)
        {
            nRet = Integer.valueOf(pStrValue.substring(0, 7), 16);
        }
        else
        {
            nRet = Integer.valueOf(pStrValue, 16);
        }
        return  nRet;
    }
}
