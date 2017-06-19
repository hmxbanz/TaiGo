package com.xtdar.app.server.response;


/**
 * Created by AMing on 15/12/24.
 * Company RongCloud
 */
public class LoginResponse {
    private int code;
    private String msg;
    private ResultEntity result;

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
    public ResultEntity getData() {
        return result;
    }
    public void setData(ResultEntity result) {
        this.result = result;
    }

    public static class ResultEntity {
        private String access_key;

        public String getAccess_key() {
            return access_key;
        }

        public void setAccess_key(String access_key) {
            this.access_key = access_key;
        }
    }
}


//{
//        "data":{
//        "access_key":"l4r4u0l91lrd0qv227c3i39v51"       //用于访问api的临时访问键
//        "code":1,
//        "msg":"登录成功"
//        }