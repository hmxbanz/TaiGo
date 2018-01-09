package com.xtdar.app.server.response;


import java.util.List;

public class MyCommentResponse {

    /**
     * data : [{"com_id":"21","comment_tag":"t_show","item_id":"59","comment":"66","com_date":"2017-08-score5 10:39:53","reviewed_title":"汕头天坑","reviewed_img":"a_show/583a3f1bdafc03469e3fb814e0645fa2.jpg","nick_name":"幸运欣2","img_path":"a_user_img/48.jpg"},{"com_id":"20","comment_tag":"t_show","item_id":"57","comment":"好的","com_date":"2017-07-26 17:16:09","reviewed_title":"欢迎来到王者荣耀","reviewed_img":"a_show/902de8d93b753daf48be5f88ce2d09f0.jpg","nick_name":"幸运欣2","img_path":"a_user_img/48.jpg"},{"com_id":"19","comment_tag":"t_show","item_id":"59","comment":"(๑\u203e᷅^\u203e᷅๑) 嫌弃你","com_date":"2017-07-26 16:32:23","reviewed_title":"汕头天坑","reviewed_img":"a_show/583a3f1bdafc03469e3fb814e0645fa2.jpg","nick_name":"幸运欣2","img_path":"a_user_img/48.jpg"},{"com_id":"18","comment_tag":"t_show","item_id":"50","comment":"好的","com_date":"2017-07-26 16:30:score6","reviewed_title":"来一个帅的！！","reviewed_img":"a_show/18342e5fb85c3ed1722629070601e5bd.jpg","nick_name":"幸运欣2","img_path":"a_user_img/48.jpg"},{"com_id":"17","comment_tag":"t_show","item_id":"59","comment":"好的","com_date":"2017-07-26 16:28:09","reviewed_title":"汕头天坑","reviewed_img":"a_show/583a3f1bdafc03469e3fb814e0645fa2.jpg","nick_name":"幸运欣2","img_path":"a_user_img/48.jpg"},{"com_id":"14","comment_tag":"t_class_item","item_id":"49","comment":"好的","com_date":"2017-07-26 16:14:19","reviewed_title":"当世上有超过 50% 的人无法上网","reviewed_img":"a_class_item_cover/d754fac32660c29e42ab9f2f153d0dd7.jpg","nick_name":"幸运欣2","img_path":"a_user_img/48.jpg"},{"com_id":"13","comment_tag":"t_show","item_id":"59","comment":"自己发的","com_date":"2017-07-23 13:39:score5","reviewed_title":"汕头天坑","reviewed_img":"a_show/583a3f1bdafc03469e3fb814e0645fa2.jpg","nick_name":"幸运欣2","img_path":"a_user_img/48.jpg"},{"com_id":"5","comment_tag":"t_show","item_id":"58","comment":"自己来","com_date":"2017-07-22 14:56:26","reviewed_title":"小狒狒","reviewed_img":"a_show/01ca1be17164633684759dfd602c4e4c.jpg","nick_name":"幸运欣2","img_path":"a_user_img/48.jpg"}]
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
         * com_id : 21
         * comment_tag : t_show
         * item_id : 59
         * comment : 66
         * com_date : 2017-08-score5 10:39:53
         * reviewed_title : 汕头天坑
         * reviewed_img : a_show/583a3f1bdafc03469e3fb814e0645fa2.jpg
         * nick_name : 幸运欣2
         * img_path : a_user_img/48.jpg
         */

        private String com_id;
        private String comment_tag;
        private String item_id;
        private String comment;
        private String com_date;
        private String reviewed_title;
        private String reviewed_img;
        private String nick_name;
        private String img_path;

        public String getCom_id() {
            return com_id;
        }

        public void setCom_id(String com_id) {
            this.com_id = com_id;
        }

        public String getComment_tag() {
            return comment_tag;
        }

        public void setComment_tag(String comment_tag) {
            this.comment_tag = comment_tag;
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

        public String getReviewed_title() {
            return reviewed_title;
        }

        public void setReviewed_title(String reviewed_title) {
            this.reviewed_title = reviewed_title;
        }

        public String getReviewed_img() {
            return reviewed_img;
        }

        public void setReviewed_img(String reviewed_img) {
            this.reviewed_img = reviewed_img;
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
