package com.brightcns.liangla.service.entity;


public class LoginAuthCodeBean {

    /**
     * code : 0
     * data : {}
     * msg : string
     * sign : string
     */

    private int code;
    private DataBean data;
    private String msg;
    private String sign;

    @Override
    public String toString() {
        return "LoginAuthCodeBean{" +
                "code=" + code +
                ", data=" + data +
                ", msg='" + msg + '\'' +
                ", sign='" + sign + '\'' +
                '}';
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

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public static class DataBean {
    }
}
