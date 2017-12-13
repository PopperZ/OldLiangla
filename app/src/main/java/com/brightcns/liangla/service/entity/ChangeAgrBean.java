package com.brightcns.liangla.service.entity;

/**
 * Created by zhangfeng on 4/12/17.
 */

public class ChangeAgrBean {
    private int mAreaIcon;
    private String mAreaName;

    public ChangeAgrBean(int mAreaIcon, String mAreaName) {
        this.mAreaIcon = mAreaIcon;
        this.mAreaName = mAreaName;
    }

    public int getmAreaIcon() {
        return mAreaIcon;
    }

    public void setmAreaIcon(int mAreaIcon) {
        this.mAreaIcon = mAreaIcon;
    }

    public String getmAreaName() {
        return mAreaName;
    }

    public void setmAreaName(String mAreaName) {
        this.mAreaName = mAreaName;
    }
}
