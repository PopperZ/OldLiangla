package com.brightcns.liangla.service.entity;

/**
 * Created by 巩贺 on 2017/11/12.
 */

public class UserInfo {



    /**
     * timestamp : 1510448930
     * msg : string
     * code : 0
     * status : 0
     * signs : string
     * data : {"rideTime":0.6,"phone":"18516533677","nickname":"我的★小号","avatar":"https://106.14.98.214/images/default/liangla.png","autograph":"№①☆全力奋战☆","userAccount":5,"integral":108,"grade":1,"lowCarbon":19}
     */

    private int timestamp;
    private String msg;
    private int code;
    private int status;
    private String signs;
    private DataBean data;

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getSigns() {
        return signs;
    }

    public void setSigns(String signs) {
        this.signs = signs;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * rideTime : 0.6
         * phone : 18516533677
         * nickname : 我的★小号
         * avatar : https://106.14.98.214/images/default/liangla.png
         * autograph : №①☆全力奋战☆
         * userAccount : 5
         * integral : 108
         * grade : 1
         * lowCarbon : 19
         */

        private double rideTime;
        private String phone;
        private String nickname;
        private String avatar;
        private String autograph;
        private double userAccount;
        private int integral;
        private int grade;
        private double lowCarbon;

        public double getRideTime() {
            return rideTime;
        }

        public void setRideTime(double rideTime) {
            this.rideTime = rideTime;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getAutograph() {
            return autograph;
        }

        public void setAutograph(String autograph) {
            this.autograph = autograph;
        }

        public double getUserAccount() {
            return userAccount;
        }

        public void setUserAccount(double userAccount) {
            this.userAccount = userAccount;
        }

        public int getIntegral() {
            return integral;
        }

        public void setIntegral(int integral) {
            this.integral = integral;
        }

        public int getGrade() {
            return grade;
        }

        public void setGrade(int grade) {
            this.grade = grade;
        }

        public double getLowCarbon() {
            return lowCarbon;
        }

        public void setLowCarbon(double lowCarbon) {
            this.lowCarbon = lowCarbon;
        }
    }
}
