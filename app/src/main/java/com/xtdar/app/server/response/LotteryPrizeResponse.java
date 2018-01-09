package com.xtdar.app.server.response;


import java.util.List;

public class LotteryPrizeResponse {

    /**
     * data : [{"his_id":"39","user_id":"134","draw_rolls_tips":"300积分","draw_rolls_date":"2017-12-19 14:32:23","draw_rolls_type":"1","is_handler":"1","his_img":"a_img/f017956f1d6df7f303ad78e166e79a23.png","his_img_2":"a_img/a71947fe041ccc2c157ca5614044c988.jpg","detail_img":""},{"his_id":"38","user_id":"134","draw_rolls_tips":"100积分","draw_rolls_date":"2017-12-19 14:score5:24","draw_rolls_type":"1","is_handler":"1","his_img":"a_img/9cef99572269aae6581f1a677cb7787a.png","his_img_2":"a_img/4b5404ef988e616733306dd44e4ba714.jpg","detail_img":""},{"his_id":"37","user_id":"134","draw_rolls_tips":"100积分","draw_rolls_date":"2017-12-19 14:score5:00","draw_rolls_type":"1","is_handler":"1","his_img":"a_img/9cef99572269aae6581f1a677cb7787a.png","his_img_2":"a_img/4b5404ef988e616733306dd44e4ba714.jpg","detail_img":""},{"his_id":"36","user_id":"134","draw_rolls_tips":"100积分","draw_rolls_date":"2017-12-19 11:53:32","draw_rolls_type":"1","is_handler":"1","his_img":"a_img/9cef99572269aae6581f1a677cb7787a.png","his_img_2":"a_img/4b5404ef988e616733306dd44e4ba714.jpg","detail_img":""},{"his_id":"32","user_id":"134","draw_rolls_tips":"100积分","draw_rolls_date":"2017-12-18 17:score6:44","draw_rolls_type":"1","is_handler":"1","his_img":"a_img/9cef99572269aae6581f1a677cb7787a.png","his_img_2":"a_img/4b5404ef988e616733306dd44e4ba714.jpg","detail_img":""},{"his_id":"31","user_id":"134","draw_rolls_tips":"100积分","draw_rolls_date":"2017-12-18 15:40:17","draw_rolls_type":"1","is_handler":"1","his_img":"a_img/9cef99572269aae6581f1a677cb7787a.png","his_img_2":"a_img/4b5404ef988e616733306dd44e4ba714.jpg","detail_img":""},{"his_id":"30","user_id":"134","draw_rolls_tips":"100积分","draw_rolls_date":"2017-12-18 15:38:08","draw_rolls_type":"1","is_handler":"1","his_img":"a_img/9cef99572269aae6581f1a677cb7787a.png","his_img_2":"a_img/4b5404ef988e616733306dd44e4ba714.jpg","detail_img":""}]
     * code : 1
     * msg : 数据返回
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
         * his_id : 39
         * user_id : 134
         * draw_rolls_tips : 300积分
         * draw_rolls_date : 2017-12-19 14:32:23
         * draw_rolls_type : 1
         * is_handler : 1
         * his_img : a_img/f017956f1d6df7f303ad78e166e79a23.png
         * his_img_2 : a_img/a71947fe041ccc2c157ca5614044c988.jpg
         * detail_img :
         */

        private String his_id;
        private String user_id;
        private String draw_rolls_tips;
        private String draw_rolls_date;
        private String draw_rolls_type;
        private String is_handler;
        private String his_img;
        private String his_img_2;
        private String detail_img;

        public String getHis_id() {
            return his_id;
        }

        public void setHis_id(String his_id) {
            this.his_id = his_id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getDraw_rolls_tips() {
            return draw_rolls_tips;
        }

        public void setDraw_rolls_tips(String draw_rolls_tips) {
            this.draw_rolls_tips = draw_rolls_tips;
        }

        public String getDraw_rolls_date() {
            return draw_rolls_date;
        }

        public void setDraw_rolls_date(String draw_rolls_date) {
            this.draw_rolls_date = draw_rolls_date;
        }

        public String getDraw_rolls_type() {
            return draw_rolls_type;
        }

        public void setDraw_rolls_type(String draw_rolls_type) {
            this.draw_rolls_type = draw_rolls_type;
        }

        public String getIs_handler() {
            return is_handler;
        }

        public void setIs_handler(String is_handler) {
            this.is_handler = is_handler;
        }

        public String getHis_img() {
            return his_img;
        }

        public void setHis_img(String his_img) {
            this.his_img = his_img;
        }

        public String getHis_img_2() {
            return his_img_2;
        }

        public void setHis_img_2(String his_img_2) {
            this.his_img_2 = his_img_2;
        }

        public String getDetail_img() {
            return detail_img;
        }

        public void setDetail_img(String detail_img) {
            this.detail_img = detail_img;
        }
    }
}
