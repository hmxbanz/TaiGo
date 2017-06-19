package com.xtdar.app.server.response;


import java.util.List;

public class ShowResponse {

    /**
     * data : [{"show_id":"38","user_id":"38","show_class_id":"12","show_type":"0","title":"Pony","show_resource":"a_show/4eb6fcb9e9f23d3a99490d8584619dfb.mp4","show_resource_m3u8":"a_show_m3u8/e8b54667266244a824a74626239fa922.m3u8","show_imgs":"","head_img":"a_show/4eb6fcb9e9f23d3a99490d8584619dfb.jpg","file_size":"8156214","time_spend":"1:58","click_count":"1","post_date":"2017-06-14 11:25:03","nick_name":"Dan","img_path":"a_user_img/38.jpg","com_count":"4"},{"show_id":"25","user_id":"42","show_class_id":"0","show_type":"1","title":"视频标题","show_resource":"a_show/bd93741aa273056d78372bc29f1e20b9.mp4","show_resource_m3u8":"a_show_m3u8/7eb79f0ce3a0d45aeee534831a5af966.m3u8","show_imgs":"","head_img":"a_show/bd93741aa273056d78372bc29f1e20b9.jpg","file_size":"0","time_spend":"0:10","click_count":"0","post_date":"2017-06-14 10:41:28","nick_name":"小明","img_path":"a_user_img/default.jpg","com_count":"1"},{"show_id":"22","user_id":"42","show_class_id":"0","show_type":"2","title":"标题","show_resource":"","show_resource_m3u8":"","show_imgs":"a_show/4cab5283793f1a09d5b82daa02ded5ce.jpg","head_img":"a_show/4cab5283793f1a09d5b82daa02ded5ce.jpg","file_size":"119907","time_spend":"","click_count":"0","post_date":"2017-06-14 10:06:22","nick_name":"小明","img_path":"a_user_img/default.jpg","com_count":"4"}]
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
         * show_id : 38
         * user_id : 38
         * show_class_id : 12
         * show_type : 0
         * title : Pony
         * show_resource : a_show/4eb6fcb9e9f23d3a99490d8584619dfb.mp4
         * show_resource_m3u8 : a_show_m3u8/e8b54667266244a824a74626239fa922.m3u8
         * show_imgs :
         * head_img : a_show/4eb6fcb9e9f23d3a99490d8584619dfb.jpg
         * file_size : 8156214
         * time_spend : 1:58
         * click_count : 1
         * post_date : 2017-06-14 11:25:03
         * nick_name : Dan
         * img_path : a_user_img/38.jpg
         * com_count : 4
         */

        private String show_id;
        private String user_id;
        private String show_class_id;
        private String show_type;
        private String title;
        private String show_resource;
        private String show_resource_m3u8;
        private String show_imgs;
        private String head_img;
        private String file_size;
        private String time_spend;
        private String click_count;
        private String post_date;
        private String nick_name;
        private String img_path;
        private String com_count;

        public String getShow_id() {
            return show_id;
        }

        public void setShow_id(String show_id) {
            this.show_id = show_id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getShow_class_id() {
            return show_class_id;
        }

        public void setShow_class_id(String show_class_id) {
            this.show_class_id = show_class_id;
        }

        public String getShow_type() {
            return show_type;
        }

        public void setShow_type(String show_type) {
            this.show_type = show_type;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getShow_resource() {
            return show_resource;
        }

        public void setShow_resource(String show_resource) {
            this.show_resource = show_resource;
        }

        public String getShow_resource_m3u8() {
            return show_resource_m3u8;
        }

        public void setShow_resource_m3u8(String show_resource_m3u8) {
            this.show_resource_m3u8 = show_resource_m3u8;
        }

        public String getShow_imgs() {
            return show_imgs;
        }

        public void setShow_imgs(String show_imgs) {
            this.show_imgs = show_imgs;
        }

        public String getHead_img() {
            return head_img;
        }

        public void setHead_img(String head_img) {
            this.head_img = head_img;
        }

        public String getFile_size() {
            return file_size;
        }

        public void setFile_size(String file_size) {
            this.file_size = file_size;
        }

        public String getTime_spend() {
            return time_spend;
        }

        public void setTime_spend(String time_spend) {
            this.time_spend = time_spend;
        }

        public String getClick_count() {
            return click_count;
        }

        public void setClick_count(String click_count) {
            this.click_count = click_count;
        }

        public String getPost_date() {
            return post_date;
        }

        public void setPost_date(String post_date) {
            this.post_date = post_date;
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

        public String getCom_count() {
            return com_count;
        }

        public void setCom_count(String com_count) {
            this.com_count = com_count;
        }
    }
}
