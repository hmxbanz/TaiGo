package com.xtdar.app.server.response;


import java.util.List;

public class FavorResponse {

    /**
     * data : [{"collect_id":"59","item_id":"48","item_title":"今天给你介绍一款玩碳纤维无人机。它通过蓝牙与智能手机连接","postfix_name":"mp4","item_cover":"a_class_item_cover/6673c4752278a38417ea1ca45ccfa277.jpg"}]
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
         * collect_id : 59
         * item_id : 48
         * item_title : 今天给你介绍一款玩碳纤维无人机。它通过蓝牙与智能手机连接
         * postfix_name : mp4
         * item_cover : a_class_item_cover/6673c4752278a38417ea1ca45ccfa277.jpg
         */

        private String collect_id;
        private String item_id;
        private String item_title;
        private String postfix_name;
        private String item_cover;

        public String getCollect_id() {
            return collect_id;
        }

        public void setCollect_id(String collect_id) {
            this.collect_id = collect_id;
        }

        public String getItem_id() {
            return item_id;
        }

        public void setItem_id(String item_id) {
            this.item_id = item_id;
        }

        public String getItem_title() {
            return item_title;
        }

        public void setItem_title(String item_title) {
            this.item_title = item_title;
        }

        public String getPostfix_name() {
            return postfix_name;
        }

        public void setPostfix_name(String postfix_name) {
            this.postfix_name = postfix_name;
        }

        public String getItem_cover() {
            return item_cover;
        }

        public void setItem_cover(String item_cover) {
            this.item_cover = item_cover;
        }
    }
}
