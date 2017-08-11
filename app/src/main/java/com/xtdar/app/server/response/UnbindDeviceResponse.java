package com.xtdar.app.server.response;


import java.util.List;

public class UnbindDeviceResponse {


    /**
     * data : [{"com_id":"194","item_id":"43","comment":"赞一个～～","com_date":"2017-07-05 18:05:03","nick_name":"insane ","img_path":"a_user_img/41.jpg"}]
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
         * com_id : 194
         * item_id : 43
         * comment : 赞一个～～
         * com_date : 2017-07-05 18:05:03
         * nick_name : insane
         * img_path : a_user_img/41.jpg
         */

        private String com_id;
        private String item_id;
        private String comment;
        private String com_date;
        private String nick_name;
        private String img_path;

        public String getCom_id() {
            return com_id;
        }

        public void setCom_id(String com_id) {
            this.com_id = com_id;
        }

        public String getItem_id() {
            return item_id;
        }

        public void setItem_id(String item_id) {
            this.item_id = item_id;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public String getCom_date() {
            return com_date;
        }

        public void setCom_date(String com_date) {
            this.com_date = com_date;
        }

        public String getNick_name() {
            return nick_name;
        }

        public void setNick_name(String nick_name) {
            this.nick_name = nick_name;
        }

        public String getImg_path() {
            return img_path;
        }

        public void setImg_path(String img_path) {
            this.img_path = img_path;
        }
    }
}
