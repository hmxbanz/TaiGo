package com.xtdar.app.server.response;


import java.util.List;

public class SignHistoryResponse {

    /**
     * data : [{"score_tips":"签到分","score_type":"1","record_date":"2017-11-14","score":"300"},{"score_tips":"变异工厂","score_type":"2","record_date":"2017-11-14","score":"150"},{"score_tips":"奖励分","score_type":"3","record_date":"2017-11-14","score":"20"}]
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
         * score_tips : 签到分
         * score_type : 1
         * record_date : 2017-11-14
         * score : 300
         */

        private String score_tips;
        private String score_type;
        private String record_date;
        private String score;

        public String getScore_tips() {
            return score_tips;
        }

        public void setScore_tips(String score_tips) {
            this.score_tips = score_tips;
        }

        public String getScore_type() {
            return score_type;
        }

        public void setScore_type(String score_type) {
            this.score_type = score_type;
        }

        public String getRecord_date() {
            return record_date;
        }

        public void setRecord_date(String record_date) {
            this.record_date = record_date;
        }

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }
    }
}
