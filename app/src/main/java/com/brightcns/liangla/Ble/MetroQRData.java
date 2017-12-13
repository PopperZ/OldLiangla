package com.brightcns.liangla.Ble;

import android.content.Context;

import com.brightcns.liangla.service.entity.MetroTransMsg;
import com.brightcns.liangla.service.entity.QRBean;
import com.brightcns.liangla.utils.BleUtils;
import com.brightcns.liangla.utils.GetDate;
import com.brightcns.liangla.utils.LogUtil;

/**
 *
 * 上海地铁二维码协议
 *
 * Created by zhangfeng on 1/12/17.
 */

public class MetroQRData {
    private Context mContext;
    //二维码byte数组
    public byte[] mQr = new byte[eQR.eALL.getLen()];

    private String m_StrBleMac = "";

    private String tag = "BleQr";

    public enum eQR {
        eOSType(0,1),           //手机操作系统类型
        eBleMac(1,6),           //
        eUserID(2,10) ,         //账户号码（TOKEN）
        eUserAuth(3,4),         //账户认证码
        eBankMac(4,4),          //银联认证 MAC
        eCurSite(5,2),          //当前车站位置信息
        eCardType(6,1),         //卡类型
        eTransFlag(7,1),        //交易标志
        eEntSite(8,4),          //进站站点
        eEntTime(9,4),          //进站时间
        eExtSite(10,4),         //出站站点
        eExtTime(11,4),         //出站时间
        eTransMoney(12,2),      //交易金额
        eThisSum(13,4),          //当月累计金额
        eThisCount(14,2),       //当月累计次数
        eMac(15,4),              //MAC
        eCardCnt(16,2),         //申请有效内二维码生成次数
        eRfu(17,9),              //预留
        eExpire(18,4),           //二维码有效期
        eFactor(19,8),           //分散因子
        eAppMac(20,4),           //应用认证码
        eALL(21, 0);             //注意长度要调整

        private int nSeq;//字段位置
        private int nLen;//字段长度
        private int nIdx; //字段索引

        // 构造函数，枚举类型只能为私有
        private eQR(int _nSeq, int _nLen) {
            this.nSeq = _nSeq;
            this.nLen = _nLen;
            this.nIdx = 0;
        }

        //字段位置
        public  int getSeq() {
            return  nSeq;
        }

        //长度
        public  int getLen() {
            int iRet = 0;
            if (nSeq==eALL.getSeq()) {
                eQR tmpQr[] = eQR.values();
                for (int i = 0; i < (tmpQr.length-1); i++) {
                    iRet = iRet + tmpQr[i].getLen();
                }
            } else {
                iRet =  nLen;
            }
            return iRet;
        }

        //字段索引
        public  int getIdx() {
            int nIdxRet = 0;
            if (nSeq == 0) {
                nIdxRet = 0;
            } else {
                for (eQR c : eQR.values()) {
                    if (c.getSeq() == nSeq) {
                        break;
                    }
                    nIdxRet = nIdxRet + c.getLen();
                }
            }
            return  nIdxRet;
        }

        //字段尾部索引
        public  int getEndPos() {
            return  getIdx()+getLen();
        }
    }

    //构造函数


    public MetroQRData(Context mContext) {
        this.mContext = mContext;
    }

    //初始化枚举
    public void Init() {
        int iAll = eQR.eALL.getLen();
        for (int i = 0; i < iAll; i++) {
            mQr[i] = 0x00;
        }
    }

    //得到二维码内容
    public String getQrConetent() {
        String strRet = "";
        String strLogUtil = "";
        for (eQR c : eQR.values()) {
            if (c.getSeq()!= eQR.eALL.getSeq()) {
                strLogUtil = String.format("[%d] ", c.getSeq());
                for (byte i = 0; i < c.getLen(); i++) {
                    strLogUtil = strLogUtil + String.format("%02X", mQr[c.getIdx() + i]);
                }
            }
        }

        try {
            strRet = new String(mQr, "ISO-8859-1");//"utf-8");//GB2312  // ISO-8859-1 // US-ASCII
            LogUtil.e(tag, "BleQr=======LAST RETRUN[" + mQr.length + "]==" + strRet.length());
            LogUtil.e(tag, "BleQr=======LAST RETRUN[" + Byte.toString(mQr[6]));
        } catch (Exception e) {
            LogUtil.e("getQRContent",e.toString());
        }
        return  strRet;
    }

    //得到mac内容
    public String getMACContent() {

        byte [] mac = new byte[25];
        int idx = 0;
        idx = eQR.eTransFlag.getIdx();
        for (int i = 0; i < 25; i++) {
            mac[i] = mQr[idx++];
        }
        return  BleUtils.bytes2HexString(mac);
    }

    //得到appmac内容
    public String getAppMACContent() {

        byte [] mac = new byte[80];
        int idx = 0;
        idx = eQR.eOSType.getIdx();
        for (int i = 0; i < 80; i++) {
            mac[i] = mQr[idx++];
        }
        return  BleUtils.bytes2HexString(mac);
    }

    //网络端二维码内容填充
    public void initBleContent(QRBean.DataBean dataBean, String bleMac){
        setOSType("00");
        setBleMac(bleMac);
        setUserID(dataBean.getUserId());
        setUserAuth(dataBean.getUserAuth());
        setBankMac(dataBean.getBankMac());
        setCurSite(dataBean.getCurSite());
        setCardType(BleUtils.strHex2Int(dataBean.getCardType()));
        setTransFlag(Integer.parseInt(dataBean.getTransFlag(),16));
        setEntSite(dataBean.getEnterSite());
        setEntTime(dataBean.getEnterTime());
        setExtSite(dataBean.getExitSite());
        setExtTime(dataBean.getExitTime());
        setTransMoney(dataBean.getTransMoney());
        setThisSum(dataBean.getThisSum());
        setThisCount(dataBean.getThisCount());
        setCardCnt(dataBean.getCardCnt());
        long expire=Long.parseLong(dataBean.getExpire(),16)+10800;
        setExpire(Long.toHexString(expire));
        setFactor(dataBean.getFactor());
    }


    //本地生成二维码时内容填充
    public void initBleContent(MetroTransMsg dataBean, String bleMac){
        setOSType("00");
        setBleMac(bleMac);
        setUserID("06222760021200000016");
        setUserAuth("A56396F1");
        setBankMac("11223344");
        setCurSite(dataBean.getStrCurSite().substring(0,4));
        setCardType(BleUtils.strHex2Int("D2"));
        setTransFlag(Integer.parseInt("41",16));
        setEntSite(dataBean.getStrEntsite());
        setEntTime(dataBean.getStrEntTime());
        setExtSite(dataBean.getStrExtSite());
        setExtTime(dataBean.getStrExtTime());
        setTransMoney(dataBean.getStrTransMoney());
        setThisSum(dataBean.getStrThisSum());
        setThisCount(dataBean.getStrThisCount());
        setCardCnt(dataBean.getStrCardCnt());
        setExpire(GetDate.getQRExpire());
        setFactor("590B01EE000000F0");
    }

    //手机操作系统
    public void setOSType(String pStrVal) {
        String strTemp;
        int idx = 0;
        idx = eQR.eOSType.getIdx();
        if (pStrVal.length()>2) {
            mQr[idx] = 0x00;
            LogUtil.e(tag, "BleQr======not equal error[eOSType]======[" + pStrVal + "]" + pStrVal.length());
            return;
        }
        LogUtil.e(tag, "BleQr=========[eOSType]===[" + pStrVal + "]" + pStrVal.length());
        mQr[idx] =  BleUtils.strHex2Byte(pStrVal);
    }

    //蓝牙地址
    public void setBleMac(String pStrVal) {
        String strTemp;
        int idx = 0;
        idx = eQR.eBleMac.getIdx();
        m_StrBleMac = pStrVal;
        if (pStrVal.length()!=12) {
            for (int i = 0; i < eQR.eBleMac.getLen(); i++) {
                mQr[idx++] =  0x00;
            }
            LogUtil.e(tag, "BleQr======not equal error[eBleMac]======[" + pStrVal + "]" + pStrVal.length());
            return;
        }

        LogUtil.e(tag, "BleQr=========[eBleMac]===[" + pStrVal + "]" + pStrVal.length());
        for (int i = 0; i < eQR.eBleMac.getLen(); i++) {
            strTemp = pStrVal.substring(i*2, i*2+2);
            mQr[idx++] =  BleUtils.strHex2Byte(strTemp);
        }
    }

    //账户
    public void setUserID(String pStrVal) {
        String strTemp;
        int idx = 0;
        int userIDLen = 0;
        idx = eQR.eUserID.getIdx();
        for (int i = 0; i < eQR.eUserID.getLen(); i++) {
            mQr[idx++] = BleUtils.strHex2Byte("FF");
        }
        idx = eQR.eUserID.getIdx();
        userIDLen = 0;
        if (pStrVal.length()>=(eQR.eUserID.getLen()*2)) {
            userIDLen = eQR.eUserID.getLen();
        } else {
            if (pStrVal.length()>0) {
                userIDLen = pStrVal.length()/2;
            } else {
                LogUtil.e(tag, "BleQr======strUserID length wrong======[" + pStrVal + "]" + pStrVal.length());
                return;
            }
        }
        if (userIDLen>0) {
            LogUtil.e(tag, "BleQr======strUserID length=["+ userIDLen +"]======[" + pStrVal + "]len[" + pStrVal.length()+"]");
            for (int i = 0; i < userIDLen; i++) {
                strTemp = pStrVal.substring(i * 2, i * 2 + 2);
                mQr[idx++] = BleUtils.strHex2Byte(strTemp);
            }
            if ((userIDLen*2) < pStrVal.length()) {
                strTemp = pStrVal.substring(pStrVal.length()-1, pStrVal.length()) + "F";
                mQr[idx++] = BleUtils.strHex2Byte(strTemp);
                LogUtil.e(tag, "BleQr not all" + pStrVal.substring(pStrVal.length()-1, pStrVal.length()));
            }
        }
    }

    //账户认证码
    public void setUserAuth(String pStrVal) {
        LogUtil.e(tag, "BleQr=========[eUserAuth]===[" + pStrVal + "]");
        String strTemp;
        int idx = 0;
        idx = eQR.eUserAuth.getIdx();
        if (pStrVal.length()< eQR.eUserAuth.getLen()*2){
            return;
        }
        for (int i = 0; i < eQR.eUserAuth.getLen(); i++) {
            strTemp = pStrVal.substring(i*2, i*2+2);
            mQr[idx++] =  BleUtils.strHex2Byte(strTemp);
        }
    }

    //银联认证码
    public void setBankMac(String pStrVal){
        LogUtil.e(tag, "BleQr=========[eBankMac]===[" + pStrVal + "]");
        String strTemp;
        int idx = 0;
        idx = eQR.eBankMac.getIdx();
        if (pStrVal.length()< eQR.eBankMac.getLen()*2){
            return;
        }
        if (pStrVal.length()== eQR.eBankMac.getLen()*2) {
            for (int i = 0; i < eQR.eBankMac.getLen(); i++) {
                strTemp = pStrVal.substring(i*2, i*2+2);
                mQr[idx++] =  BleUtils.strHex2Byte(strTemp);
            }
        }
    }

    //当前站点
    public void setCurSite(String pStrVal){
        LogUtil.e(tag, "BleQr=========[CurSite]===[" + pStrVal + "]");
        String strTemp;
        int idx = 0;
        idx = eQR.eCurSite.getIdx();
        if (pStrVal.length()!=4) {
            for (int i = 0; i < eQR.eCurSite.getLen(); i++) {
                mQr[idx++] =  0x00;
            }
            LogUtil.e(tag, "BleQr======not equal error[eCurSite]======[" + pStrVal + "]" + pStrVal.length());
            return;
        }
        LogUtil.e(tag, "BleQr=========[eCurSite]===[" + pStrVal + "]" + pStrVal.length());
        for (int i = 0; i < eQR.eCurSite.getLen(); i++) {
            strTemp = pStrVal.substring(i*2, i*2+2);
            mQr[idx++] =  BleUtils.strHex2Byte(strTemp);
        }
    }

    //卡类型
    public void setCardType(int pVal) {
        String strTemp;
        int idx = 0;
        idx = eQR.eCardType.getIdx();
        LogUtil.e(tag, "BleQr=========[eCardType]===[" + pVal + "]");
        mQr[idx] =  Integer.valueOf(pVal).byteValue();
    }

    //交易标志
    public void setTransFlag(int pVal) {
        String strTemp;
        int idx = 0;
        idx = eQR.eTransFlag.getIdx();
        LogUtil.e(tag, "BleQr=========[eTransFlag]===[" + pVal + "]");
        mQr[idx] =  Integer.valueOf(pVal).byteValue();
    }

    //进站站点
    public void setEntSite(String pStrVal) {
        String strTemp;
        int idx = 0;
        idx = eQR.eEntSite.getIdx();
        if (pStrVal.length()!=8) {
            for (int i = 0; i < eQR.eEntSite.getLen(); i++) {
                mQr[idx++] =  0x00;
            }
            LogUtil.e(tag, "BleQr======not equal error[eEntSite]======[" + pStrVal + "]" + pStrVal.length());
            return;
        }
        LogUtil.e(tag, "BleQr=========[eEntSite]===[" + pStrVal + "]" + pStrVal.length());
        for (int i = 0; i < eQR.eEntSite.getLen(); i++) {
            strTemp = pStrVal.substring(i*2, i*2+2);
            mQr[idx++] =  BleUtils.strHex2Byte(strTemp);
        }
    }

    //进站时间
    public void setEntTime(String pStrVal) {
        LogUtil.e(tag, "BleQr=========[eEntTime]===[" + pStrVal + "]");
        String strTemp;
        int idx = 0;
        idx = eQR.eEntTime.getIdx();

        if (pStrVal.length()< eQR.eEntTime.getLen()*2){
            return;
        }
        for (int i = 0; i < eQR.eEntTime.getLen(); i++) {
            strTemp = pStrVal.substring(i*2, i*2+2);
            mQr[idx++] =  BleUtils.strHex2Byte(strTemp);
        }
    }

    //出站站点
    public void setExtSite(String pStrVal) {
        String strTemp;
        int idx = 0;
        idx = eQR.eExtSite.getIdx();
        if (pStrVal.length()!=8) {
            for (int i = 0; i < eQR.eExtSite.getLen(); i++) {
                mQr[idx++] =  0x00;
            }
            LogUtil.e(tag, "BleQr======not equal error[eExtSite]======[" + pStrVal + "]" + pStrVal.length());
            return;
        }

        LogUtil.e(tag, "BleQr=========[eExtSite]===[" + pStrVal + "]" + pStrVal.length());
        for (int i = 0; i < eQR.eExtSite.getLen(); i++) {
            strTemp = pStrVal.substring(i*2, i*2+2);
            mQr[idx++] =  BleUtils.strHex2Byte(strTemp);
        }
    }

    //出站时间
    public void setExtTime(String pStrVal) {
        LogUtil.e(tag, "BleQr=========[eExtTime]===[" + pStrVal + "]");
        String strTemp;
        int idx = 0;
        idx = eQR.eExtTime.getIdx();
        if (pStrVal.length()< eQR.eExtTime.getLen()*2){
            return;
        }
        for (int i = 0; i < eQR.eExtTime.getLen(); i++) {
            strTemp = pStrVal.substring(i*2, i*2+2);
            mQr[idx++] =  BleUtils.strHex2Byte(strTemp);
        }
    }

    //消费金额
    public void setTransMoney(String pStrVal) {
        LogUtil.e(tag, "BleQr=========[eTransMoney]===[" + pStrVal + "]");
        String strTemp;
        int idx = 0;
        idx = eQR.eTransMoney.getIdx();
        if (pStrVal.length()< eQR.eTransMoney.getLen()*2){
            return;
        }
        for (int i = 0; i < eQR.eTransMoney.getLen(); i++) {
            strTemp = pStrVal.substring(i*2, i*2+2);
            mQr[idx++] =  BleUtils.strHex2Byte(strTemp);
        }
    }

    //当月消费总额
    public void setThisSum(String pStrVal) {
        LogUtil.e(tag, "BleQr=========[eThisSum]===[" + pStrVal + "]");
        String strTemp;
        int idx = 0;
        idx = eQR.eThisSum.getIdx();
        if (pStrVal.length()< eQR.eThisSum.getLen()*2){
            return;
        }
        for (int i = 0; i < eQR.eThisSum.getLen(); i++) {
            strTemp = pStrVal.substring(i*2, i*2+2);
            mQr[idx++] =  BleUtils.strHex2Byte(strTemp);
        }
    }

    //当月消费次数
    public void setThisCount(String pStrVal) {
        LogUtil.e(tag, "BleQr=========[eThisCount]===[" + pStrVal + "]");
        String strTemp;
        int idx = 0;
        idx = eQR.eThisCount.getIdx();
        if (pStrVal.length()< eQR.eThisCount.getLen()*2){
            return;
        }
        for (int i = 0; i < eQR.eThisCount.getLen(); i++) {
            strTemp = pStrVal.substring(i*2, i*2+2);
            mQr[idx++] =  BleUtils.strHex2Byte(strTemp);
        }
    }

    //二维码有效次数
    public void setCardCnt(String pStrVal){
        LogUtil.e(tag, "BleQr=========[eCardCnt]===[" + pStrVal + "]");
        String strTemp;
        int idx = 0;
        idx = eQR.eCardCnt.getIdx();
        if (pStrVal.length()< eQR.eCardCnt.getLen()*2){
            return;
        }
        for (int i = 0; i < eQR.eCardCnt.getLen(); i++) {
            strTemp = pStrVal.substring(i*2, i*2+2);
            mQr[idx++] =  BleUtils.strHex2Byte(strTemp);
        }
    }

    //二维码有效期
    public void setExpire(String pStrVal){
        LogUtil.e(tag, "BleQr=========[eExpire]===[" + pStrVal + "]");
        String strTemp;
        int idx = 0;
        idx = eQR.eExpire.getIdx();
        if (pStrVal.length()< eQR.eExpire.getLen()*2){
            return;
        }
        for (int i = 0; i < eQR.eExpire.getLen(); i++) {
            strTemp = pStrVal.substring(i*2, i*2+2);
            mQr[idx++] =  BleUtils.strHex2Byte(strTemp);
        }
    }

    //分散因子
    public void setFactor(String pStrVal){
        LogUtil.e(tag, "BleQr=========[eFactor]===[" + pStrVal + "]");
        String strTemp;
        int idx = 0;
        idx = eQR.eFactor.getIdx();
        LogUtil.e(tag, "BleQr=========[eFactor len]===[" + eQR.eFactor.getLen() + "]");
        if (pStrVal.length()< eQR.eFactor.getLen()*2){
            return;
        }
        for (int i = 0; i < eQR.eFactor.getLen(); i++) {
            strTemp = pStrVal.substring(i*2, i*2+2);
            mQr[idx++] =  BleUtils.strHex2Byte(strTemp);
        }
    }
    //appmac
    public void setAppMac(String pStrVal){
        LogUtil.e(tag, "BleQr=========[eAppMac]===[" + pStrVal + "]");

        String strTemp;
        int idx = 0;
        idx = eQR.eAppMac.getIdx();
        if (pStrVal.length()< eQR.eAppMac.getLen()*2){
            return;
        }
        for (int i = 0; i < eQR.eAppMac.getLen(); i++)
        {
            strTemp = pStrVal.substring(i*2, i*2+2);
            mQr[idx++] =  BleUtils.strHex2Byte(strTemp);
        }
    }
    //过程mac
    public void setMac(String pStrVal){
        LogUtil.e(tag, "BleQr=========[eMac]===[" + pStrVal + "]");

        String strTemp;
        int idx = 0;
        idx = eQR.eMac.getIdx();
        if (pStrVal.length()< eQR.eMac.getLen()*2){
            return;
        }
        for (int i = 0; i < eQR.eMac.getLen(); i++)
        {
            strTemp = pStrVal.substring(i*2, i*2+2);
            mQr[idx++] =  BleUtils.strHex2Byte(strTemp);
        }
    }


}
