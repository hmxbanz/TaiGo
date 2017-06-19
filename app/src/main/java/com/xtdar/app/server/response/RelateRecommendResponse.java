package com.xtdar.app.server.response;


import java.util.List;

public class RelateRecommendResponse {

    /**
     * data : [{"item_id":"25","class_id":"1","item_title":"Hoola-Hooping Song","chapter_name":"1","click_count":"2","collect_count":"0","time_spend":"1:33","resource":"a_class_item_resource/5f39097811965155277a0fcae1141c8e.mp4","m3u8_file":"a_m3u8/b4f77582a5521a6fa3fa4125db4e63ee.m3u8","save_size":"6370090","postfix_name":"mp4","item_cover":"a_class_item_cover/b7f86cd2fd2e75099127c560034b233f.png","content":""}]
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
         * item_id : 25
         * class_id : 1
         * item_title : Hoola-Hooping Song
         * chapter_name : 1
         * click_count : 2
         * collect_count : 0
         * time_spend : 1:33
         * resource : a_class_item_resource/5f39097811965155277a0fcae1141c8e.mp4
         * m3u8_file : a_m3u8/b4f77582a5521a6fa3fa4125db4e63ee.m3u8
         * save_size : 6370090
         * postfix_name : mp4
         * item_cover : a_class_item_cover/b7f86cd2fd2e75099127c560034b233f.png
         * content :
         */

        private String item_id;
        private String class_id;
        private String item_title;
        private String chapter_name;
        private String click_count;
        private String collect_count;
        private String time_spend;
        private String resource;
        private String m3u8_file;
        private String save_size;
        private String postfix_name;
        private String item_cover;
        private String content;

        public String getItem_id() {
            return item_id;
        }

        public void setItem_id(String item_id) {
            this.item_id = item_id;
        }

        public String getClass_id() {
            return class_id;
        }

        public void setClass_id(String class_id) {
            this.class_id = class_id;
        }

        public String getItem_title() {
            return item_title;
        }

        public void setItem_title(String item_title) {
            this.item_title = item_title;
        }

        public String getChapter_name() {
            return chapter_name;
        }

        public void setChapter_name(String chapter_name) {
            this.chapter_name = chapter_name;
        }

        public String getClick_count() {
            return click_count;
        }

        public void setClick_count(String click_count) {
            this.click_count = click_count;
        }

        public String getCollect_count() {
            return collect_count;
        }

        public void setCollect_count(String collect_count) {
            this.collect_count = collect_count;
        }

        public String getTime_spend() {
            return time_spend;
        }

        public void setTime_spend(String time_spend) {
            this.time_spend = time_spend;
        }

        public String getResource() {
            return resource;
        }

        public void setResource(String resource) {
            this.resource = resource;
        }

        public String getM3u8_file() {
            return m3u8_file;
        }

        public void setM3u8_file(String m3u8_file) {
            this.m3u8_file = m3u8_file;
        }

        public String getSave_size() {
            return save_size;
        }

        public void setSave_size(String save_size) {
            this.save_size = save_size;
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

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
