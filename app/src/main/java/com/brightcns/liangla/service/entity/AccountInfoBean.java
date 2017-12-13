package com.brightcns.liangla.service.entity;

public class AccountInfoBean {

    /**
     * msg : success
     * code : 0
     * data : {"userId":"620810300130340615447810048","flagCode":"3001","tradeCode":"810","areaCode":"620","phone":"13626155748","equId":"shenyf","status":0}
     */

    private String msg;
    private int code;
    private DataBean data;

    @Override
    public String toString() {
        return "AccountInfoBean{" +
                "msg='" + msg + '\'' +
                ", code=" + code +
                ", data=" + data +
                '}';
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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * userId : 620810300130340615447810048
         * flagCode : 3001
         * tradeCode : 810
         * areaCode : 620
         * phone : 13626155748
         * equId : shenyf
         * status : 0
         */

        private String userId;
        private String flagCode;
        private String tradeCode;
        private String areaCode;
        private String phone;
        private String equId;
        private int status;

        @Override
        public String toString() {
            return "DataBean{" +
                    "userId='" + userId + '\'' +
                    ", flagCode='" + flagCode + '\'' +
                    ", tradeCode='" + tradeCode + '\'' +
                    ", areaCode='" + areaCode + '\'' +
                    ", phone='" + phone + '\'' +
                    ", equId='" + equId + '\'' +
                    ", status=" + status +
                    '}';
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getFlagCode() {
            return flagCode;
        }

        public void setFlagCode(String flagCode) {
            this.flagCode = flagCode;
        }

        public String getTradeCode() {
            return tradeCode;
        }

        public void setTradeCode(String tradeCode) {
            this.tradeCode = tradeCode;
        }

        public String getAreaCode() {
            return areaCode;
        }

        public void setAreaCode(String areaCode) {
            this.areaCode = areaCode;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getEquId() {
            return equId;
        }

        public void setEquId(String equId) {
            this.equId = equId;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
