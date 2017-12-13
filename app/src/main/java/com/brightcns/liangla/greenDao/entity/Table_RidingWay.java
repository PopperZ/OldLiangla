package com.brightcns.liangla.greenDao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by zhangfeng on 1/12/17.
 * 乘车方式映射表
 */


@Entity(nameInDb = "table_ridingway")
public class Table_RidingWay {
    //唯一索引
    @Id
    private Long id;
    //区域名称
    private String areaName;

    //区域代码
    private String areaCode;

    //业务类型
    private String businessType;

    //蓝牙通道类型
    private String bleType;

    //二维码协议类型
    private String qrType;

    @Generated(hash = 1528017933)
    public Table_RidingWay(Long id, String areaName, String areaCode,
            String businessType, String bleType, String qrType) {
        this.id = id;
        this.areaName = areaName;
        this.areaCode = areaCode;
        this.businessType = businessType;
        this.bleType = bleType;
        this.qrType = qrType;
    }

    @Generated(hash = 1320596692)
    public Table_RidingWay() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAreaName() {
        return this.areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getAreaCode() {
        return this.areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getBusinessType() {
        return this.businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getBleType() {
        return this.bleType;
    }

    public void setBleType(String bleType) {
        this.bleType = bleType;
    }

    public String getQrType() {
        return this.qrType;
    }

    public void setQrType(String qrType) {
        this.qrType = qrType;
    }



}
