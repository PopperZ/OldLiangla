package com.brightcns.liangla.service.entity;

/**
 * Created by zhangfeng on 31/8/17.
 */

public class PersonBean {

    /**
     * code : 0
     * msg : success
     * timestamp : 1504161146573
     * startTimestamp : 1504161146573
     * signature :
     * flag : 0
     * data : {"credit":{"userId":"AFsPnZGU","creditScore":250},"trip":{"phone":"0","myTrip":100,"discharge":100,"status":0},"finance":{"phone":"18516518939","realName":"蒋尚伟","availableQuota":80,"status":0}}
     */

    private int code;
    private String msg;
    private long timestamp;
    private long startTimestamp;
    private String signature;
    private int flag;
    private DataBean data;

    @Override
    public String toString() {
        return "PersonBean{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", timestamp=" + timestamp +
                ", startTimestamp=" + startTimestamp +
                ", signature='" + signature + '\'' +
                ", flag=" + flag +
                ", data=" + data +
                '}';
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getStartTimestamp() {
        return startTimestamp;
    }

    public void setStartTimestamp(long startTimestamp) {
        this.startTimestamp = startTimestamp;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * credit : {"userId":"AFsPnZGU","creditScore":250}
         * trip : {"phone":"0","myTrip":100,"discharge":100,"status":0}
         * finance : {"phone":"18516518939","realName":"蒋尚伟","availableQuota":80,"status":0}
         */

        private CreditBean credit;
        private TripBean trip;
        private FinanceBean finance;

        @Override
        public String toString() {
            return "DataBean{" +
                    "credit=" + credit +
                    ", trip=" + trip +
                    ", finance=" + finance +
                    '}';
        }

        public CreditBean getCredit() {
            return credit;
        }

        public void setCredit(CreditBean credit) {
            this.credit = credit;
        }

        public TripBean getTrip() {
            return trip;
        }

        public void setTrip(TripBean trip) {
            this.trip = trip;
        }

        public FinanceBean getFinance() {
            return finance;
        }

        public void setFinance(FinanceBean finance) {
            this.finance = finance;
        }

        public static class CreditBean {
            /**
             * userId : AFsPnZGU
             * creditScore : 250
             */

            private String userId;
            private int creditScore;

            @Override
            public String toString() {
                return "CreditBean{" +
                        "userId='" + userId + '\'' +
                        ", creditScore=" + creditScore +
                        '}';
            }

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public int getCreditScore() {
                return creditScore;
            }

            public void setCreditScore(int creditScore) {
                this.creditScore = creditScore;
            }
        }

        public static class TripBean {
            /**
             * phone : 0
             * myTrip : 100
             * discharge : 100
             * status : 0
             */

            private String phone;
            private int myTrip;
            private int discharge;
            private int status;

            @Override
            public String toString() {
                return "TripBean{" +
                        "phone='" + phone + '\'' +
                        ", myTrip=" + myTrip +
                        ", discharge=" + discharge +
                        ", status=" + status +
                        '}';
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public int getMyTrip() {
                return myTrip;
            }

            public void setMyTrip(int myTrip) {
                this.myTrip = myTrip;
            }

            public int getDischarge() {
                return discharge;
            }

            public void setDischarge(int discharge) {
                this.discharge = discharge;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }
        }

        public static class FinanceBean {
            /**
             * phone : 18516518939
             * realName : 蒋尚伟
             * availableQuota : 80.0
             * status : 0
             */
            private String phone;
            private String realName;
            private double availableQuota;
            private int status;

            @Override
            public String toString() {
                return "FinanceBean{" +
                        "phone='" + phone + '\'' +
                        ", realName='" + realName + '\'' +
                        ", availableQuota=" + availableQuota +
                        ", status=" + status +
                        '}';
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getRealName() {
                return realName;
            }

            public void setRealName(String realName) {
                this.realName = realName;
            }

            public double getAvailableQuota() {
                return availableQuota;
            }

            public void setAvailableQuota(double availableQuota) {
                this.availableQuota = availableQuota;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }
        }
    }
}
