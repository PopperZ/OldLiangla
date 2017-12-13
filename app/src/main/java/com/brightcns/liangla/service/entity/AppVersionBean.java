package com.brightcns.liangla.service.entity;


public class AppVersionBean {

    /**
     * code : 0
     * msg : success
     * data : {"version":"v1.0","versionCode":1,"brief":"上线版本","apkUri":"https:/106.14.98.214/default/apk/ios/app-release.apk","status":1}
     */

    private int code;
    private String msg;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * version : v1.0
         * versionCode : 1
         * brief : 上线版本
         * apkUri : https:/106.14.98.214/default/apk/ios/app-release.apk
         * status : 1
         */

        private String version;
        private int versionCode;
        private String brief;
        private String apkUri;
        private int status;

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public int getVersionCode() {
            return versionCode;
        }

        public void setVersionCode(int versionCode) {
            this.versionCode = versionCode;
        }

        public String getBrief() {
            return brief;
        }

        public void setBrief(String brief) {
            this.brief = brief;
        }

        public String getApkUri() {
            return apkUri;
        }

        public void setApkUri(String apkUri) {
            this.apkUri = apkUri;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
