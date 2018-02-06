package com.xtdar.app.server.response;


import java.util.List;

public class CommentReplyResponse {

    /**
     * data : [{"reply_id":"6","user_id":"133","at_user_id":"0","com_id":"8","reply":"试一下","like_count":"0","post_date":"2018-01-23 14:17:29","nick_name":"小明","img_path":"a_user_img/133.jpg","at_nick_name":null,"is_like":null},{"reply_id":"5","user_id":"65","at_user_id":"133","com_id":"8","reply":"at 133测试","like_count":"0","post_date":"2018-01-23 11:19:46","nick_name":"等等","img_path":"a_user_img/65.jpg","at_nick_name":"小明","is_like":null},{"reply_id":"4","user_id":"133","at_user_id":"0","com_id":"8","reply":"回复测试","like_count":"1","post_date":"2018-01-23 11:18:24","nick_name":"小明","img_path":"a_user_img/133.jpg","at_nick_name":null,"is_like":null}]
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
         * reply_id : 6
         * user_id : 133
         * at_user_id : 0
         * com_id : 8
         * reply : 试一下
         * like_count : 0
         * post_date : 2018-01-23 14:17:29
         * nick_name : 小明
         * img_path : a_user_img/133.jpg
         * at_nick_name : null
         * is_like : null
         */

        private String reply_id;
        private String user_id;
        private String at_user_id;
        private String com_id;
        private String reply;
        private String like_count;
        private String post_date;
        private String nick_name;
        private String img_path;
        private Object at_nick_name;
        private Object is_like;

        public String getReply_id() {
            return reply_id;
        }

        public void setReply_id(String reply_id) {
            this.reply_id = reply_id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getAt_user_id() {
            return at_user_id;
        }

        public void setAt_user_id(String at_user_id) {
            this.at_user_id = at_user_id;
        }

        public String getCom_id() {
            return com_id;
        }

        public void setCom_id(String com_id) {
            this.com_id = com_id;
        }

        public String getReply() {
            return reply;
        }

        public void setReply(String reply) {
            this.reply = reply;
        }

        public String getLike_count() {
            return like_count;
        }

        public void setLike_count(String like_count) {
            this.like_count = like_count;
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

        public Object getAt_nick_name() {
            return at_nick_name;
        }

        public void setAt_nick_name(Object at_nick_name) {
            this.at_nick_name = at_nick_name;
        }

        public Object getIs_like() {
            return is_like;
        }

        public void setIs_like(Object is_like) {
            this.is_like = is_like;
        }
    }
}
