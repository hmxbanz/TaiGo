package com.xtdar.app.server.response;


public class VersionResponse {

    /**
     * state : 1
     * msg:"成功"
     * result : {"Android":{"VersionCode":20170526,"VersionName":"第二版本","DownloadUrl":"/tae-demo.apk"},"state":1,"msg":"成功"}
     */

    private int state;
    private String msg;

    public int getState() {
        return state;
    }
    public void setState(int state) {
        this.state = state;
    }
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }
    private ResultEntity result;
    public ResultEntity getAndroid() {
        return result;
    }
    public void setAndroid(ResultEntity result) {
        this.result = result;
    }
    public class ResultEntity {
        private int VersionCode;
        private String VersionName;
        private String DownloadUrl;

        public int getVersionCode() {
            return VersionCode;
        }

        public void setVersionCode(int versionCode) {
            VersionCode = versionCode;
        }

        public String getVersionName() {
            return VersionName;
        }

        public void setVersionName(String versionName) {
            VersionName = versionName;
        }

        public String getDownloadUrl() {
            return DownloadUrl;
        }

        public void setDownloadUrl(String downloadUrl) {
            DownloadUrl = downloadUrl;
        }
    }
}
