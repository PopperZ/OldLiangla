package com.brightcns.liangla.service.entity;

/**
 * Created by zhangfeng on 5/5/17.
 */

public class UploadIconBean {

    /**
     * code : 0
     * data : {}
     * key : string
     * msg : string
     * timeStamp : 0
     */

    private int code;
    private String data;
    private String key;
    private String msg;
    private Long timeStamp;

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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
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
}
