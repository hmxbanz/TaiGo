package com.xtdar.app.server.response;


import java.util.List;

public class TagResponse {

    /**
     * data : [{"class_id":"101","class_name":"推荐","class_show_type":"100"},{"class_id":"1","class_name":"动画","class_show_type":"1"},{"class_id":"5","class_name":"儿歌","class_show_type":"4"},{"class_id":"7","class_name":"胎教音乐","class_show_type":"4"}]
     * code : 1
     * msg : 返回数据
     */

    private int code;
    private String msg;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * class_id : 101
         * class_name : 推荐
         * class_show_type : 100
         */

        private String class_id;
        private String class_name;
        private String class_show_type;

        public String getClass_id() {
            return class_id;
        }

        public void setClass_id(String class_id) {
            this.class_id = class_id;
        }

        public String getClass_name() {
            return class_name;
        }

        public void setClass_name(String class_name) {
            this.class_name = class_name;
        }

        public String getClass_show_type() {
            return class_show_type;
        }

        public void setClass_show_type(String class_show_type) {
            this.class_show_type = class_show_type;
        }
    }
}
