package com.brightcns.liangla.service.entity;

/**
 * Created by Tech-508 on 2017/10/27.
 */

public class HomeSmallBannerDean {
    private String banneTitle;

    public String getBanneTitle() {
        return banneTitle;
    }

    public void setBanneTitle(String banneTitle) {
        this.banneTitle = banneTitle;
    }

    public int getBannerPic() {
        return bannerPic;
    }

    public void setBannerPic(int bannerPic) {
        this.bannerPic = bannerPic;
    }

    private int bannerPic;

    public HomeSmallBannerDean(String banneTitle, int bannerPic) {
        this.banneTitle = banneTitle;
        this.bannerPic = bannerPic;
    }
}
