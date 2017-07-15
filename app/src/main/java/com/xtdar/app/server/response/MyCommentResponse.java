package com.xtdar.app.server.response;


import java.util.List;

public class MyCommentResponse {

    /**
     * data : [{"com_id":"5","item_id":"43","comment":"好吧，再试下","com_date":"2017-07-12 15:59:06","title":"我来发一个～～","head_img":"a_show/efcad2417baaec520f65d513bd70c30b.jpg","nick_name":"幸运欣","img_path":"a_user_img/51.jpg"},{"com_id":"4","item_id":"43","comment":"评论zzz测试","com_date":"2017-07-12 15:57:48","title":"我来发一个～～","head_img":"a_show/efcad2417baaec520f65d513bd70c30b.jpg","nick_name":"幸运欣","img_path":"a_user_img/51.jpg"},{"com_id":"1","item_id":"43","comment":"评论测试","com_date":"2017-07-12 15:54:40","title":"我来发一个～～","head_img":"a_show/efcad2417baaec520f65d513bd70c30b.jpg","nick_name":"幸运欣","img_path":"a_user_img/51.jpg"}]
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
         * com_id : 5
         * item_id : 43
         * comment : 好吧，再试下
         * com_date : 2017-07-12 15:59:06
         * title : 我来发一个～～
         * head_img : a_show/efcad2417baaec520f65d513bd70c30b.jpg
         * nick_name : 幸运欣
         * img_path : a_user_img/51.jpg
         */

        private String com_id;
        private String item_id;
        private String comment;
        private String com_date;
        private String title;
        private String head_img;
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

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getHead_img() {
            return head_img;
        }

        public void setHead_img(String head_img) {
            this.head_img = head_img;
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
