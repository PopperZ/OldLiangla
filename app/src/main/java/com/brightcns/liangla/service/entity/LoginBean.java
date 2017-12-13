package com.brightcns.liangla.service.entity;


public class LoginBean {

    /**
     * msg : success
     * code : 0
     * data : {"userId":"620810300130678169632149504","userToken":"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOiI2MjA4MTAzMDAxMzA2NzgxNjk2MzIxNDk1MDQiLCJwaG9uZSI6IjE4ODYwNDc3NDYxIiwidG9rZW5FbmRUaW1lIjoxNTEzMzg5MzQ1NTA1LCJleHAiOjE1MTMzODkzNDUsIm5iZiI6MTUxMDc5NzM0NX0.l0ze-xjt0B9hSstL2UrTTuST-2mc4gBfwe141lq4tpQ","loginToken":"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOiI2MjA4MTAzMDAxMzA2NzgxNjk2MzIxNDk1MDQiLCJ0b2tlbkVuZFRpbWUiOjE1MTMzODkzNDU1MDUsImV4cCI6MTUxMzM4OTM0NSwibmJmIjoxNTEwNzk3MzQ1fQ.cgYnQEvlt0_iXmWfC8GEyRapQN4LL15w9SdM9SCEfOY","tokenEndTime":1513389345505}
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
         * userId : 620810300130678169632149504
         * userToken : eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOiI2MjA4MTAzMDAxMzA2NzgxNjk2MzIxNDk1MDQiLCJwaG9uZSI6IjE4ODYwNDc3NDYxIiwidG9rZW5FbmRUaW1lIjoxNTEzMzg5MzQ1NTA1LCJleHAiOjE1MTMzODkzNDUsIm5iZiI6MTUxMDc5NzM0NX0.l0ze-xjt0B9hSstL2UrTTuST-2mc4gBfwe141lq4tpQ
         * loginToken : eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOiI2MjA4MTAzMDAxMzA2NzgxNjk2MzIxNDk1MDQiLCJ0b2tlbkVuZFRpbWUiOjE1MTMzODkzNDU1MDUsImV4cCI6MTUxMzM4OTM0NSwibmJmIjoxNTEwNzk3MzQ1fQ.cgYnQEvlt0_iXmWfC8GEyRapQN4LL15w9SdM9SCEfOY
         * tokenEndTime : 1513389345505
         */

        private String userId;
        private String userToken;
        private String loginToken;
        private long tokenEndTime;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUserToken() {
            return userToken;
        }

        public void setUserToken(String userToken) {
            this.userToken = userToken;
        }

        public String getLoginToken() {
            return loginToken;
        }

        public void setLoginToken(String loginToken) {
            this.loginToken = loginToken;
        }

        public long getTokenEndTime() {
            return tokenEndTime;
        }

        public void setTokenEndTime(long tokenEndTime) {
            this.tokenEndTime = tokenEndTime;
        }
    }
}
