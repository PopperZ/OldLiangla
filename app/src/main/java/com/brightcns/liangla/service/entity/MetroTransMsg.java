package com.brightcns.liangla.service.entity;

import java.io.Serializable;

/**
 *
 * 上海地铁协议交易bean
 *
 * Created by zhangfeng on 7/11/17.
 */

public class MetroTransMsg implements Serializable {
    private int    intFlag;//信息标志
    private String strTransType;//交易类型 1
    private String strCurSite;//当前终端编号 4
    private String strAgmNum;//设备流水号 4
    private String strTransTime ;//交易时间
    private String strTransFlag ;//交易标志 1
    private String strEntsite;//进站站点 4
    private String strEntTime;//进站时间 4
    private String strExtSite;//出站站点 4
    private String strExtTime;//出站时间 4
    private String strTransMoney ;//交易金额 2
    private String strThisSum ;//当月le累计金额 4
    private String strThisCount ;//当月累计次数 2
    private String strMac ;//过程MAC 4
    private String strCardCnt;//二维码申请次数2
    private String strRfu;//预留 9
    private String strAppMAC;//4 appmac


    public MetroTransMsg(int intFlag, String strTransType, String strCurSite, String strAgmNum, String strTransTime, String strTransFlag, String strEntsite, String strEntTime, String strExtSite,
                         String strExtTime, String strTransMoney, String strThisSum, String strThisCount, String strMac, String strCardCnt, String strRfu, String strAppMAC) {
        this.intFlag=intFlag;
        this.strTransType = strTransType;
        this.strCurSite = strCurSite;
        this.strAgmNum = strAgmNum;
        this.strTransTime = strTransTime;
        this.strTransFlag = strTransFlag;
        this.strEntsite = strEntsite;
        this.strEntTime = strEntTime;
        this.strExtSite = strExtSite;
        this.strExtTime = strExtTime;
        this.strTransMoney = strTransMoney;
        this.strThisSum = strThisSum;
        this.strThisCount = strThisCount;
        this.strMac = strMac;
        this.strCardCnt = strCardCnt;
        this.strRfu = strRfu;
        this.strAppMAC = strAppMAC;
    }

    public MetroTransMsg(String strCurSite , String strEntsite, String strEntTime, String strExtSite, String strExtTime, String strTransMoney,
                         String strThisSum, String strThisCount, String strCardCnt) {
        this.strCurSite = strCurSite;
        this.strEntsite = strEntsite;
        this.strEntTime = strEntTime;
        this.strExtSite = strExtSite;
        this.strExtTime = strExtTime;
        this.strTransMoney = strTransMoney;
        this.strThisSum = strThisSum;
        this.strThisCount = strThisCount;
        this.strCardCnt = strCardCnt;
    }

    public int getIntFlag() {
        return intFlag;
    }

    public void setIntFlag(int intFlag) {
        this.intFlag = intFlag;
    }

    public MetroTransMsg() {
        this.strTransType = strTransType;
    }

    public String getStrTransType() {
        return strTransType;
    }

    public void setStrTransType(String strTransType) {
        this.strTransType = strTransType;
    }

    public String getStrCurSite() {
        return strCurSite;
    }

    public void setStrCurSite(String strCurSite) {
        this.strCurSite = strCurSite;
    }

    public String getStrAgmNum() {
        return strAgmNum;
    }

    public void setStrAgmNum(String strAgmNum) {
        this.strAgmNum = strAgmNum;
    }

    public String getStrTransTime() {
        return strTransTime;
    }

    public void setStrTransTime(String strTransTime) {
        this.strTransTime = strTransTime;
    }

    public String getStrTransFlag() {
        return strTransFlag;
    }

    public void setStrTransFlag(String strTransFlag) {
        this.strTransFlag = strTransFlag;
    }

    public String getStrEntsite() {
        return strEntsite;
    }

    public void setStrEntsite(String strEntsite) {
        this.strEntsite = strEntsite;
    }

    public String getStrEntTime() {
        return strEntTime;
    }

    public void setStrEntTime(String strEntTime) {
        this.strEntTime = strEntTime;
    }

    public String getStrExtSite() {
        return strExtSite;
    }

    public void setStrExtSite(String strExtSite) {
        this.strExtSite = strExtSite;
    }

    public String getStrExtTime() {
        return strExtTime;
    }

    public void setStrExtTime(String strExtTime) {
        this.strExtTime = strExtTime;
    }

    public String getStrTransMoney() {
        return strTransMoney;
    }

    public void setStrTransMoney(String strTransMoney) {
        this.strTransMoney = strTransMoney;
    }

    public String getStrThisSum() {
        return strThisSum;
    }

    public void setStrThisSum(String strThisSum) {
        this.strThisSum = strThisSum;
    }

    public String getStrThisCount() {
        return strThisCount;
    }

    public void setStrThisCount(String strThisCount) {
        this.strThisCount = strThisCount;
    }

    public String getStrMac() {
        return strMac;
    }

    public void setStrMac(String strMac) {
        this.strMac = strMac;
    }

    public String getStrCardCnt() {
        return strCardCnt;
    }

    public void setStrCardCnt(String strCardCnt) {
        this.strCardCnt = strCardCnt;
    }

    public String getStrRfu() {
        return strRfu;
    }

    public void setStrRfu(String strRfu) {
        this.strRfu = strRfu;
    }

    public String getStrAppMAC() {
        return strAppMAC;
    }

    public void setStrAppMAC(String strAppMAC) {
        this.strAppMAC = strAppMAC;
    }

    @Override
    public String toString() {
        return "MetroTransMsg{" +
                "intFlag=" + intFlag +
                ", strTransType='" + strTransType + '\'' +
                ", strCurSite='" + strCurSite + '\'' +
                ", strAgmNum='" + strAgmNum + '\'' +
                ", strTransTime='" + strTransTime + '\'' +
                ", strTransFlag='" + strTransFlag + '\'' +
                ", strEntsite='" + strEntsite + '\'' +
                ", strEntTime='" + strEntTime + '\'' +
                ", strExtSite='" + strExtSite + '\'' +
                ", strExtTime='" + strExtTime + '\'' +
                ", strTransMoney='" + strTransMoney + '\'' +
                ", strThisSum='" + strThisSum + '\'' +
                ", strThisCount='" + strThisCount + '\'' +
                ", strMac='" + strMac + '\'' +
                ", strCardCnt='" + strCardCnt + '\'' +
                ", strRfu='" + strRfu + '\'' +
                ", strAppMAC='" + strAppMAC + '\'' +
                '}';
    }
}
