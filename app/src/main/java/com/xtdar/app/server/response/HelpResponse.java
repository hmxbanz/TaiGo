package com.xtdar.app.server.response;


import java.util.List;

public class HelpResponse {


    /**
     * data : [{"article_id":"3","type_id":"2","title":"如何添加新设备？","content":"测试 测试 ","post_date":"2017-06-29 04:20:22"},{"article_id":"4","type_id":"2","title":"找不到设备，自私也连接不上设备自私办？","content":"ffff","post_date":"2017-06-29 08:22:18"}]
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
         * article_id : 3
         * type_id : 2
         * title : 如何添加新设备？
         * content : 测试 测试
         * post_date : 2017-06-29 04:20:22
         */

        private String article_id;
        private String type_id;
        private String title;
        private String content;
        private String post_date;

        public String getArticle_id() {
            return article_id;
        }

        public void setArticle_id(String article_id) {
            this.article_id = article_id;
        }

        public String getType_id() {
            return type_id;
        }

        public void setType_id(String type_id) {
            this.type_id = type_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getPost_date() {
            return post_date;
        }

        public void setPost_date(String post_date) {
            this.post_date = post_date;
        }
    }
}
