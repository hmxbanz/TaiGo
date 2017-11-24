package com.xtdar.app.server.response;


import java.util.List;

public class SignCountResponse {

    /**
     * data : {"is_sign_in":"1","all_sign_in_count":"1","card_count_day":"2","sign_in_days":"2","month_sign_in_day":["2017-11-16","2017-11-17"]}
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
         * is_sign_in : 1
         * all_sign_in_count : 1
         * card_count_day : 2
         * sign_in_days : 2
         * month_sign_in_day : ["2017-11-16","2017-11-17"]
         */

        private String is_sign_in;
        private String all_sign_in_count;
        private String card_count_day;
        private String sign_in_days;
        private List<String> month_sign_in_day;

        public String getIs_sign_in() {
            return is_sign_in;
        }

        public void setIs_sign_in(String is_sign_in) {
            this.is_sign_in = is_sign_in;
        }

        public String getAll_sign_in_count() {
            return all_sign_in_count;
        }

        public void setAll_sign_in_count(String all_sign_in_count) {
            this.all_sign_in_count = all_sign_in_count;
        }

        public String getCard_count_day() {
            return card_count_day;
        }

        public void setCard_count_day(String card_count_day) {
            this.card_count_day = card_count_day;
        }

        public String getSign_in_days() {
            return sign_in_days;
        }

        public void setSign_in_days(String sign_in_days) {
            this.sign_in_days = sign_in_days;
        }

        public List<String> getMonth_sign_in_day() {
            return month_sign_in_day;
        }

        public void setMonth_sign_in_day(List<String> month_sign_in_day) {
            this.month_sign_in_day = month_sign_in_day;
        }
    }
}
