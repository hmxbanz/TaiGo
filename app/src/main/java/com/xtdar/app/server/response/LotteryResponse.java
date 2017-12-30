package com.xtdar.app.server.response;


public class LotteryResponse {

    /**
     * data : {"rolls_item_id":"4","rolls_item_tips":"100积分","degree":"108","rolls_item_type":"1","show_img":"a_img/bd088ca395f6abd2a4f46353e48a43ec.png","detail_img":"","his_id":"24"}
     * code : 1
     * msg : 恭喜中奖
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
         * rolls_item_id : 4
         * rolls_item_tips : 100积分
         * degree : 108
         * rolls_item_type : 1
         * show_img : a_img/bd088ca395f6abd2a4f46353e48a43ec.png
         * detail_img :
         * his_id : 24
         */

        private String rolls_item_id;
        private String rolls_item_tips;
        private String degree;
        private String rolls_item_type;
        private String show_img;
        private String detail_img;
        private String his_id;

        public String getRolls_item_id() {
            return rolls_item_id;
        }

        public void setRolls_item_id(String rolls_item_id) {
            this.rolls_item_id = rolls_item_id;
        }

        public String getRolls_item_tips() {
            return rolls_item_tips;
        }

        public void setRolls_item_tips(String rolls_item_tips) {
            this.rolls_item_tips = rolls_item_tips;
        }

        public String getDegree() {
            return degree;
        }

        public void setDegree(String degree) {
            this.degree = degree;
        }

        public String getRolls_item_type() {
            return rolls_item_type;
        }

        public void setRolls_item_type(String rolls_item_type) {
            this.rolls_item_type = rolls_item_type;
        }

        public String getShow_img() {
            return show_img;
        }

        public void setShow_img(String show_img) {
            this.show_img = show_img;
        }

        public String getDetail_img() {
            return detail_img;
        }

        public void setDetail_img(String detail_img) {
            this.detail_img = detail_img;
        }

        public String getHis_id() {
            return his_id;
        }

        public void setHis_id(String his_id) {
            this.his_id = his_id;
        }
    }
}
