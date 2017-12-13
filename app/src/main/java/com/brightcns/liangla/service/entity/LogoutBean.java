package com.brightcns.liangla.service.entity;

/**
 * Created by luis_gong on 2017/11/17.
 */

public class LogoutBean {
    /**
     * code : 0
     * data : string
     * msg : string
     * timeStamp : 0
     * key ：string
     */
    private int code;
    private String data;
    private String msg;
    private Long timeStamp;
    private String key;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
