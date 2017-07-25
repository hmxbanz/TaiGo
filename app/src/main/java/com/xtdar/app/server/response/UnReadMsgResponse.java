package com.xtdar.app.server.response;


public class UnReadMsgResponse {


    /**
     * data : {"to_user_id":"51","msg_count":0}
     * code : 1
     * msg : 返回数据
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
         * to_user_id : 51
         * msg_count : 0
         */

        private String to_user_id;
        private int msg_count;

        public String getTo_user_id() {
            return to_user_id;
        }

        public void setTo_user_id(String to_user_id) {
            this.to_user_id = to_user_id;
        }

        public int getMsg_count() {
            return msg_count;
        }

        public void setMsg_count(int msg_count) {
            this.msg_count = msg_count;
        }
    }
}
