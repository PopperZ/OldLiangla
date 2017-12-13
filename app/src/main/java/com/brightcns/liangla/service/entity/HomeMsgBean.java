package com.brightcns.liangla.service.entity;

import java.util.List;

/**
 * Created by zhangfeng on 30/8/17.
 */

public class HomeMsgBean {

    /**
     * code : 0
     * msg : success
     * timestamp : 1504087144524
     * startTimestamp : 1
     * signature :
     * flag : 0
     * data : [{"userId":"666","title":"低碳出行","orderTime":1504060960000,"content":"减排2g","detailOne":"低碳生活   绿色出行","detailTwo":"","titleUrl":"https://106.14.98.214/images/message/android/xhdpi/ic_zfzs.png","contentUrl":null,"status":0},{"userId":"666","title":"支付助手","orderTime":1503997594000,"content":"6.6元","detailOne":"付款成功","detailTwo":"买家订单6666","titleUrl":"https://106.14.98.214/images/message/android/xhdpi/ic_lowcarbon.png","contentUrl":null,"status":0}]
     */

    private int code;
    private String msg;
    private long timestamp;
    private long startTimestamp;
    private String signature;
    private int flag;
    private List<DataBean> data;

    @Override
    public String toString() {
        return "HomeMsgBean{" +
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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * userId : 666
         * title : 低碳出行
         * orderTime : 1504060960000
         * content : 减排2g
         * detailOne : 低碳生活   绿色出行
         * detailTwo :
         * titleUrl : https://106.14.98.214/images/message/android/xhdpi/ic_zfzs.png
         * contentUrl : null
         * status : 0
         */

        private String userId;
        private String title;
        private long orderTime;
        private String content;
        private String detailOne;
        private String detailTwo;
        private String titleUrl;
        private Object contentUrl;
        private int status;

        @Override
        public String toString() {
            return "DataBean{" +
                    "userId='" + userId + '\'' +
                    ", title='" + title + '\'' +
                    ", orderTime=" + orderTime +
                    ", content='" + content + '\'' +
                    ", detailOne='" + detailOne + '\'' +
                    ", detailTwo='" + detailTwo + '\'' +
                    ", titleUrl='" + titleUrl + '\'' +
                    ", contentUrl=" + contentUrl +
                    ", status=" + status +
                    '}';
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public long getOrderTime() {
            return orderTime;
        }

        public void setOrderTime(long orderTime) {
            this.orderTime = orderTime;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getDetailOne() {
            return detailOne;
        }

        public void setDetailOne(String detailOne) {
            this.detailOne = detailOne;
        }

        public String getDetailTwo() {
            return detailTwo;
        }

        public void setDetailTwo(String detailTwo) {
            this.detailTwo = detailTwo;
        }

        public String getTitleUrl() {
            return titleUrl;
        }

        public void setTitleUrl(String titleUrl) {
            this.titleUrl = titleUrl;
        }

        public Object getContentUrl() {
            return contentUrl;
        }

        public void setContentUrl(Object contentUrl) {
            this.contentUrl = contentUrl;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
