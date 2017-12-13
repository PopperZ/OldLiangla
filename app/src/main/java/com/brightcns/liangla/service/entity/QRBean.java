package com.brightcns.liangla.service.entity;

/**
 * Created by zhangfeng on 30/11/17.
 */

public class QRBean {

    /**
     * msg : success
     * code : 0
     * data : {"userId":"06222760021200000016","userAuth":"A56396F1","bankMac":"11223344","curSite":"0241","cardType":"D2","transFlag":"81","enterSite":"00000000","enterTime":"00000000","exitSite":"00000000","exitTime":"00000000","transMoney":"0000","thisSum":"00000000","thisCount":"0000","cardCnt":"0000","rfu":"000000000000000000","expire":"593A8285","factor":"590B01EE000000F0"}
     */

    private String msg;
    private int code;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * userId : 06222760021200000016
         * userAuth : A56396F1
         * bankMac : 11223344
         * curSite : 0241
         * cardType : D2
         * transFlag : 81
         * enterSite : 00000000
         * enterTime : 00000000
         * exitSite : 00000000
         * exitTime : 00000000
         * transMoney : 0000
         * thisSum : 00000000
         * thisCount : 0000
         * cardCnt : 0000
         * rfu : 000000000000000000
         * expire : 593A8285
         * factor : 590B01EE000000F0
         */

        private String userId;
        private String userAuth;
        private String bankMac;
        private String curSite;
        private String cardType;
        private String transFlag;
        private String enterSite;
        private String enterTime;
        private String exitSite;
        private String exitTime;
        private String transMoney;
        private String thisSum;
        private String thisCount;
        private String cardCnt;
        private String rfu;
        private String expire;
        private String factor;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUserAuth() {
            return userAuth;
        }

        public void setUserAuth(String userAuth) {
            this.userAuth = userAuth;
        }

        public String getBankMac() {
            return bankMac;
        }

        public void setBankMac(String bankMac) {
            this.bankMac = bankMac;
        }

        public String getCurSite() {
            return curSite;
        }

        public void setCurSite(String curSite) {
            this.curSite = curSite;
        }

        public String getCardType() {
            return cardType;
        }

        public void setCardType(String cardType) {
            this.cardType = cardType;
        }

        public String getTransFlag() {
            return transFlag;
        }

        public void setTransFlag(String transFlag) {
            this.transFlag = transFlag;
        }

        public String getEnterSite() {
            return enterSite;
        }

        public void setEnterSite(String enterSite) {
            this.enterSite = enterSite;
        }

        public String getEnterTime() {
            return enterTime;
        }

        public void setEnterTime(String enterTime) {
            this.enterTime = enterTime;
        }

        public String getExitSite() {
            return exitSite;
        }

        public void setExitSite(String exitSite) {
            this.exitSite = exitSite;
        }

        public String getExitTime() {
            return exitTime;
        }

        public void setExitTime(String exitTime) {
            this.exitTime = exitTime;
        }

        public String getTransMoney() {
            return transMoney;
        }

        public void setTransMoney(String transMoney) {
            this.transMoney = transMoney;
        }

        public String getThisSum() {
            return thisSum;
        }

        public void setThisSum(String thisSum) {
            this.thisSum = thisSum;
        }

        public String getThisCount() {
            return thisCount;
        }

        public void setThisCount(String thisCount) {
            this.thisCount = thisCount;
        }

        public String getCardCnt() {
            return cardCnt;
        }

        public void setCardCnt(String cardCnt) {
            this.cardCnt = cardCnt;
        }

        public String getRfu() {
            return rfu;
        }

        public void setRfu(String rfu) {
            this.rfu = rfu;
        }

        public String getExpire() {
            return expire;
        }

        public void setExpire(String expire) {
            this.expire = expire;
        }

        public String getFactor() {
            return factor;
        }

        public void setFactor(String factor) {
            this.factor = factor;
        }
    }
}
