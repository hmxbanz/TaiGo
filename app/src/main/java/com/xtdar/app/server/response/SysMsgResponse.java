package com.xtdar.app.server.response;


import java.util.List;

public class SysMsgResponse {


    /**
     * data : [{"msg_id":"1","msg":"消息内容","link":"","post_time":"2017-07-11 16:03:18"}]
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
         * msg_id : 1
         * msg : 消息内容
         * link :
         * post_time : 2017-07-11 16:03:18
         */

        private String msg_id;
        private String msg;
        private String link;
        private String post_time;

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

        public String getPost_time() {
            return post_time;
        }

        public void setPost_time(String post_time) {
            this.post_time = post_time;
        }
    }
}
