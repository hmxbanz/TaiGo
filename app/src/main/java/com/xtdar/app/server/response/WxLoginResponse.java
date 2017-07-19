package com.xtdar.app.server.response;


public class WxLoginResponse {

    /**
     * data : {"access_key":"psae3b8audjda69aneu6jruic3"}
     * code : 1
     * msg : 登录成功
     */

    private DataBean data;
    private int code;
    private String msg;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
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

    public static class DataBean {
        /**
         * access_key : psae3b8audjda69aneu6jruic3
         */

        private String access_key;

        public String getAccess_key() {
            return access_key;
        }

        public void setAccess_key(String access_key) {
            this.access_key = access_key;
        }
    }
}
