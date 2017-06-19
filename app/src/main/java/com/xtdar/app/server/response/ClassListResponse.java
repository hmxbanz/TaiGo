package com.xtdar.app.server.response;

import java.util.List;

//分类项列表返回
public class ClassListResponse {


    /**
     * data : [{"item_id":"36","class_id":"1","item_title":" Mimi Country Song (p-t-s-n-i-a-","chapter_name":"1","click_count":"2","collect_count":"0","time_spend":"1:58","resource":"a_class_item_resource/83e3f94d23acabc8c00d1b7ccda62658.mp4","m3u8_file":"a_m3u8/d6c46f97fc2405ff5d534b5fbfca0f26.m3u8","save_size":"8108284","postfix_name":"mp4","item_cover":"a_class_item_cover/d43d9f9a9f464910d829ed76b186c6b3.png","content":""},{"item_id":"35","class_id":"1","item_title":"Badanamu School","chapter_name":"1","click_count":"1","collect_count":"0","time_spend":"4:35","resource":"a_class_item_resource/5b2f4c17afff2d8971a020b9e31106a2.mp4","m3u8_file":"a_m3u8/63761886f916900ff900e4b49397bf48.m3u8","save_size":"18310642","postfix_name":"mp4","item_cover":"a_class_item_cover/bea765f4c3eccd9208f63f52bb3a2be4.png","content":""},{"item_id":"34","class_id":"1","item_title":"Alphabet Song","chapter_name":"1","click_count":"1","collect_count":"0","time_spend":"3:45","resource":"a_class_item_resource/60b2732f8db144b6ef9da8ad83b16f85.mp4","m3u8_file":"a_m3u8/3223353e3cd0fddb0db2ede75dd6fdad.m3u8","save_size":"15482608","postfix_name":"mp4","item_cover":"a_class_item_cover/9d2b0a649a23baa2f7739692edd39164.png","content":""},{"item_id":"33","class_id":"1","item_title":"Christmas Song","chapter_name":"15","click_count":"1","collect_count":"0","time_spend":"2:13","resource":"a_class_item_resource/59fa1ce8f54df5f7276efe25bd76fae6.mp4","m3u8_file":"a_m3u8/01ecda233801df68aba7497b757f4753.m3u8","save_size":"9129104","postfix_name":"mp4","item_cover":"a_class_item_cover/77066fa1bb204d4a022c419eb8833d79.png","content":""},{"item_id":"32","class_id":"1","item_title":"Five Little Friends","chapter_name":"1","click_count":"1","collect_count":"0","time_spend":"2:09","resource":"a_class_item_resource/9fd1b1a0ea1d5a3e69386879a3a0368c.mp4","m3u8_file":"a_m3u8/3b17381e41fe13f06a3ab8a24cac3a12.m3u8","save_size":"8455908","postfix_name":"mp4","item_cover":"a_class_item_cover/f939ad77c0239f9cd0f5a6cbe1afbc1a.png","content":""},{"item_id":"31","class_id":"1","item_title":"Zoo Party","chapter_name":"1","click_count":"1","collect_count":"0","time_spend":"2:00","resource":"a_class_item_resource/6f31c4d83ad04c4d7aaca958b5902852.mp4","m3u8_file":"a_m3u8/92e47b6604f849c10d6e6793d2de911c.m3u8","save_size":"8212192","postfix_name":"mp4","item_cover":"a_class_item_cover/d64b3535fc1c6420e7ed42f4e5f35fbf.png","content":""},{"item_id":"30","class_id":"1","item_title":"Fat Cat Mat","chapter_name":"1","click_count":"2","collect_count":"0","time_spend":"2:05","resource":"a_class_item_resource/c2173675dba8e70aef92ccf6ff7b4acf.mp4","m3u8_file":"a_m3u8/34482c7406a3230cddb9c0bc9aa327cf.m3u8","save_size":"8601451","postfix_name":"mp4","item_cover":"a_class_item_cover/be4b26e7f282a9d19320f5aafe87ef17.png","content":""},{"item_id":"29","class_id":"1","item_title":" Color Song","chapter_name":"11","click_count":"2","collect_count":"0","time_spend":"1:56","resource":"a_class_item_resource/742a46c7f3fb787037948499c34e2be5.mp4","m3u8_file":"a_m3u8/7bf61452c7bbeca1478bda9da3d80d0c.m3u8","save_size":"7987492","postfix_name":"mp4","item_cover":"a_class_item_cover/88738ef99539453fd4236efe037ee76f.png","content":""},{"item_id":"28","class_id":"1","item_title":"Badanamu Time","chapter_name":"1","click_count":"3","collect_count":"0","time_spend":"1:51","resource":"a_class_item_resource/0685344f29c5335ce00d793f569c3b5f.mp4","m3u8_file":"a_m3u8/716db42d71146d57898ddfc98ec7ad18.m3u8","save_size":"7627412","postfix_name":"mp4","item_cover":"a_class_item_cover/61b8b67cdbc2125e8f691f9bb7429d70.png","content":""},{"item_id":"27","class_id":"1","item_title":"Merry Christmas","chapter_name":"9","click_count":"2","collect_count":"0","time_spend":"1:02","resource":"a_class_item_resource/bf17ec76f7945721fe7cc8cfba7ee476.mp4","m3u8_file":"a_m3u8/55d5a0d190f8f7b5b55188903b6f7ec4.m3u8","save_size":"4231961","postfix_name":"mp4","item_cover":"a_class_item_cover/cb2e282efb87b4b32de78e1e6f2d3136.png","content":""},{"item_id":"26","class_id":"1","item_title":"Mimi's Song","chapter_name":"8","click_count":"1","collect_count":"0","time_spend":"2:18","resource":"a_class_item_resource/f0c00abb872319cb9f967ddf90a71e22.mp4","m3u8_file":"a_m3u8/68ff56b6b099ca34f33443884a111959.m3u8","save_size":"9533354","postfix_name":"mp4","item_cover":"a_class_item_cover/1387e25f86dcd44a7c8fc8e4fee4a174.png","content":""},{"item_id":"25","class_id":"1","item_title":"Hoola-Hooping Song","chapter_name":"1","click_count":"4","collect_count":"0","time_spend":"1:33","resource":"a_class_item_resource/5f39097811965155277a0fcae1141c8e.mp4","m3u8_file":"a_m3u8/b4f77582a5521a6fa3fa4125db4e63ee.m3u8","save_size":"6370090","postfix_name":"mp4","item_cover":"a_class_item_cover/b7f86cd2fd2e75099127c560034b233f.png","content":""}]
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
         * item_id : 36
         * class_id : 1
         * item_title :  Mimi Country Song (p-t-s-n-i-a-
         * chapter_name : 1
         * click_count : 2
         * collect_count : 0
         * time_spend : 1:58
         * resource : a_class_item_resource/83e3f94d23acabc8c00d1b7ccda62658.mp4
         * m3u8_file : a_m3u8/d6c46f97fc2405ff5d534b5fbfca0f26.m3u8
         * save_size : 8108284
         * postfix_name : mp4
         * item_cover : a_class_item_cover/d43d9f9a9f464910d829ed76b186c6b3.png
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
