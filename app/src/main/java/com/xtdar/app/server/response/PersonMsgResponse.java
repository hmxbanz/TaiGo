package com.xtdar.app.server.response;


import java.util.List;

public class PersonMsgResponse {


    /**
     * data : [{"msg_id":"11","form_user_id":"67","to_user_id":"64","msg":"Ar","link":"","is_read":"0","post_time":"2017-07-19 15:08:12","form_user_name":"Fruit","form_img_path":"a_user_img/67.jpg","to_nick_name":"青玄"}]
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
         * msg_id : 11
         * form_user_id : 67
         * to_user_id : 64
         * msg : Ar
         * link :
         * is_read : 0
         * post_time : 2017-07-19 15:08:12
         * form_user_name : Fruit
         * form_img_path : a_user_img/67.jpg
         * to_nick_name : 青玄
         */

        private String msg_id;
        private String form_user_id;
        private String to_user_id;
        private String msg;
        private String link;
        private String is_read;
        private String post_time;
        private String form_user_name;
        private String form_img_path;
        private String to_nick_name;

        public LinkBean getLinkObj() {
            return linkObj;
        }

        public void setLinkObj(LinkBean linkObj) {
            this.linkObj = linkObj;
        }

        public LinkBean linkObj;

        public static class LinkBean {
            /**
             * title : 全民魔兽
             * to : gamedetail
             * key_val : 3
             * img : a_game_img/aad7cf7a308b5eb7a6d94ef8073fc0bd.jpg
             */

            private String title;
            private String to;
            private String key_val;
            private String img;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getTo() {
                return to;
            }

            public void setTo(String to) {
                this.to = to;
            }

            public String getKey_val() {
                return key_val;
            }

            public void setKey_val(String key_val) {
                this.key_val = key_val;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }
        }

        public String getMsg_id() {
            return msg_id;
        }

        public void setMsg_id(String msg_id) {
            this.msg_id = msg_id;
        }

        public String getForm_user_id() {
            return form_user_id;
        }

        public void setForm_user_id(String form_user_id) {
            this.form_user_id = form_user_id;
        }

        public String getTo_user_id() {
            return to_user_id;
        }

        public void setTo_user_id(String to_user_id) {
            this.to_user_id = to_user_id;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getIs_read() {
            return is_read;
        }

        public void setIs_read(String is_read) {
            this.is_read = is_read;
        }

        public String getPost_time() {
            return post_time;
        }

        public void setPost_time(String post_time) {
            this.post_time = post_time;
        }

        public String getForm_user_name() {
            return form_user_name;
        }

        public void setForm_user_name(String form_user_name) {
            this.form_user_name = form_user_name;
        }

        public String getForm_img_path() {
            return form_img_path;
        }

        public void setForm_img_path(String form_img_path) {
            this.form_img_path = form_img_path;
        }

        public String getTo_nick_name() {
            return to_nick_name;
        }

        public void setTo_nick_name(String to_nick_name) {
            this.to_nick_name = to_nick_name;
        }
    }
}
