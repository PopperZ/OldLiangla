package com.brightcns.liangla.utils;

/**
 * 常量工具类
 */

public class ConstantUtil {
    //存储常量os
    public static final String OS = "android";


    //判断是否是第一次进入
    public static final String ISFRIST = "isfrist";
    //判断是否是第一次乘车
    public static final String ISFRISTRIDING = "isfristriding";
    //判断是否登录
    public static final String ISLOGIN = "login";
    //判断蓝牙的权限失败
    public static final String BLEBPERMISSIONSFAIL = "blebpermissionsfail";
    //判断蓝牙的是否广播成功
    public static final String BLEBORADSUCESS = "bleboradsucess";
    //获取远程设备的address值
    public static final String DEVICEADDRESS = "deviceaddress";
    //获取本地设备的address值
    public static final String BLEADDRESS = "bleaddress";
    //获取进站的信息
    public static final String BLEENTERSTATION = "bleenterstation";
    //获取出站的信息
    public static final String BLEEXITSTATION = "bleexitstation";
    //远程设备请求读取数据
    public static final String BLEREADDATA = "blereaddata";
    //远程设备请求写入数据
    public static final String BLEWRITEDATA = "blewritedata";
    //第一次与读卡器交互成功(第一次握手成功)
    public static final String BLEHANDSHAKE = "blehandshake";

    //获取手机IMEI值
    public static final String IMEI = "imei";
    //获取的用户名
    public static final String USERNAME = "username";
    //获取的用户id
    public static final String USERID = "userId";
    //获取的用户usertoken
    public static final String USERTOKEN = "userToken";
    //获取的用户logintoken
    public static final String LOGINTOKEN = "logintoken";
    //获取的用户phoneNum
    public static final String PHONENUM = "phonenum";

    //获取的tokenEndTime
    public static final String TOKENENDTIME = "tokenendtime";
    //获取的citylist
    public static final String CITYLIST = "citylist";
    //获取的citylist
    public static final String AREASPELL = "areaspell";


    //获取的活动券id
    public static final String TICKETID = "ticketid";
    //是否已领取过活动券
    public static final String FREEHASCHANGE = "freehaschange";

    //获取的协议--许可及服务协议
    public static final String LICENSEAGR = "licenseagr";
    //获取的协议--邀请好友详细规则
    public static final String INVITEAGR = "inviteagr";
    //获取的协议--隐私政策
    public static final String PRIVACYAGR = "privacyagr";
    //获取的协议--亮啦数据充值协议
    public static final String RECHARGEAGR = "rechargeagr";
    //获取的协议--用户登录注册协议
    public static final String LOGINAGR = "loginagr";
    //获取的协议--用户服务协议
    public static final String USERAGR = "useragr";


    //----------------------------用户信息-----------------------------------------
    public static final String RIDE_TIME = "rideTime";
    public static final String PHONE = "phone";
    public static final String NICKNAME = "nickname";
    public static final String AVATAR = "avatar";
    public static final String AUTOGRAPH = "autograph";
    public static final String USER_ACCOUNT = "userAccount";
    public static final String INTEGRAL = "integral";
    public static final String GRADE = "grade";
    public static final String LOW_CARBON = "lowCarbon";


    //----------------------蓝牙常量------------------------------------------------
    //ble廣播
    public final static String WRITEUUID= "00002AF1-0000-1000-8000-00805F9B34FB";
    public final static String READUUID = "00002AF0-0000-1000-8000-00805F9B34FB";
    public final static String SERVICESUUID = "000018F0-0000-1000-8000-00805F9B34FB";

    public final static String ACTION_GATT_CONNECTED = "com.liangla.ACTION_GATT_CONNECTED";
    public final static String ACTION_GATT_DISCONNECTED = "com.liangla.le.ACTION_GATT_DISCONNECTED";
    public final static String ACTION_GATT_WRITE = "com.liangla.ACTION_GATT_WRITE";
    public final static String ACTION_GATT_READDE = "com.liangla.ACTION_GATT_READE";
    public final static String ACTION_GATT_SVR_FAIL = "com.liangla.ACTION_GATT_SVR_FAIL";
    public final static String ACTION_BLESEND_SUCCESS = "com.liangla.ACTION_BLESEND_SUCCESS";
    public final static String ACTION_BLESEND_FAIL = "com.liangla.ACTION_BLESEND_FAIL";
    public final static String ACTION_BLESEND_DATA = "com.liangla.ACTION_BLESEND_DATA";
    public final static String ACTION_ENTER_STATION = "com.liangla.ACTION_ENTER_STATION";
    public final static String ACTION_EXIT_STATION = "com.liangla.ACTION_EXIT_STATION";
    public final static String ACTION_UNLAWFUL = "com.liangla.ACTION_UNLAWFUL";
    public final static String ACTION_SEND_ORDER = "com.liangla.ACTION_SEND_ORDER";
    public final static String HANDACK_THE_DEAL = "com.liangla.HANDACK_THE_DEAL";
    public final static String TRANSACK_THE_DEAL = "com.liangla.TRANSACK_THE_DEAL";
    public final static String ACTION_QR_HIDE = "com.liangla.ACTION_QR_HIDE";
    public final static String ACTION_QR_NEW = "com.liangla.ACTION_QR_NEW";
    public final static String ACTION_QR_OPEN = "com.liangla.ACTION_QR_OPEN";

    //----------------------------乘车方式常亮-----------------------------------------
    //区域代码
    public final static String AREA_CODE= "areaCode";
    //业务类型
    public final static String AREA_NAME= "modesCode";




}
