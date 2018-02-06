package com.xtdar.app.server.response;


import java.util.List;

public class CommentResponse {

    /**
     * data : [{"com_id":"48","user_id":"131","item_id":"23","comment":"居然没有评论。。。","com_date":"2018-01-26 15:28:24","like_count":"0","reply_count":"2","nick_name":"Danplin","img_path":"a_user_img/131.jpg","is_like":null,"replyList":[{"user_id":"169","reply":"我","post_date":"2018-02-03 09:11:32","nick_name":"用户1516347617386","at_nick_name":null},{"user_id":"169","reply":"我","post_date":"2018-02-03 09:12:08","nick_name":"用户1516347617386","at_nick_name":"用户1516347617386"}]}]
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
         * com_id : 48
         * user_id : 131
         * item_id : 23
         * comment : 居然没有评论。。。
         * com_date : 2018-01-26 15:28:24
         * like_count : 0
         * reply_count : 2
         * nick_name : Danplin
         * img_path : a_user_img/131.jpg
         * is_like : null
         * replyList : [{"user_id":"169","reply":"我","post_date":"2018-02-03 09:11:32","nick_name":"用户1516347617386","at_nick_name":null},{"user_id":"169","reply":"我","post_date":"2018-02-03 09:12:08","nick_name":"用户1516347617386","at_nick_name":"用户1516347617386"}]
         */

        private String com_id;
        private String user_id;
        private String item_id;
        private String comment;
        private String com_date;
        private String like_count;
        private String reply_count;
        private String nick_name;
        private String img_path;
        private Object is_like;
        private List<ReplyListBean> replyList;

        public String getCom_id() {
            return com_id;
        }

        public void setCom_id(String com_id) {
            this.com_id = com_id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
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

        public String getLike_count() {
            return like_count;
        }

        public void setLike_count(String like_count) {
            this.like_count = like_count;
        }

        public String getReply_count() {
            return reply_count;
        }

        public void setReply_count(String reply_count) {
            this.reply_count = reply_count;
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

        public Object getIs_like() {
            return is_like;
        }

        public void setIs_like(Object is_like) {
            this.is_like = is_like;
        }

        public List<ReplyListBean> getReplyList() {
            return replyList;
        }

        public void setReplyList(List<ReplyListBean> replyList) {
            this.replyList = replyList;
        }

        public static class ReplyListBean {
            /**
             * user_id : 169
             * reply : 我
             * post_date : 2018-02-03 09:11:32
             * nick_name : 用户1516347617386
             * at_nick_name : null
             */

            private String user_id;
            private String reply;
            private String post_date;
            private String nick_name;
            private Object at_nick_name;

            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }

            public String getReply() {
                return reply;
            }

            public void setReply(String reply) {
                this.reply = reply;
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

            public Object getAt_nick_name() {
                return at_nick_name;
            }

            public void setAt_nick_name(Object at_nick_name) {
                this.at_nick_name = at_nick_name;
            }
        }
    }
}
