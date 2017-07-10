package com.xtdar.app.server.response;


public class ShowDetailResponse {

    /**
     * data : {"show_id":"43","user_id":"41","show_class_id":"0","show_type":"1","title":"我来发一个～～","show_resource":"a_show/efcad2417baaec520f65d513bd70c30b.mp4","show_resource_m3u8":"a_show_m3u8/fe960ff617cdd8c71fef2a700710901c.m3u8","show_imgs":"","head_img":"a_show/efcad2417baaec520f65d513bd70c30b.jpg","file_size":"0","time_spend":"0:32","click_count":"0","post_date":"2017-07-05 18:04:05","show_class_name":null,"com_count":"1","nick_name":"insane ","img_path":"a_user_img/41.jpg"}
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
         * show_id : 43
         * user_id : 41
         * show_class_id : 0
         * show_type : 1
         * title : 我来发一个～～
         * show_resource : a_show/efcad2417baaec520f65d513bd70c30b.mp4
         * show_resource_m3u8 : a_show_m3u8/fe960ff617cdd8c71fef2a700710901c.m3u8
         * show_imgs :
         * head_img : a_show/efcad2417baaec520f65d513bd70c30b.jpg
         * file_size : 0
         * time_spend : 0:32
         * click_count : 0
         * post_date : 2017-07-05 18:04:05
         * show_class_name : null
         * com_count : 1
         * nick_name : insane
         * img_path : a_user_img/41.jpg
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
        private Object show_class_name;
        private String com_count;
        private String nick_name;
        private String img_path;

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

        public Object getShow_class_name() {
            return show_class_name;
        }

        public void setShow_class_name(Object show_class_name) {
            this.show_class_name = show_class_name;
        }

        public String getCom_count() {
            return com_count;
        }

        public void setCom_count(String com_count) {
            this.com_count = com_count;
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
