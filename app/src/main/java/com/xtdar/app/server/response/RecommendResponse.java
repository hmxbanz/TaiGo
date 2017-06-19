package com.xtdar.app.server.response;


import java.util.List;

public class RecommendResponse {

    /**
     * data : {"classslideimg":["a_class_item_cover/4cab5283793f1a09d5b82daa02ded5ce.jpg","a_class_item_cover/8e5023263b4823b9fb3038a97c1c29c8.jpg"],"recommend_list":[{"class_id":"1","class_name":"动画","class_show_type":"1","data_list":[{"item_id":"19","class_id":"1","item_title":"Hoola Song","chapter_name":"1","click_count":"310","collect_count":"0","time_spend":"0:10","resource":"a_class_item_resource/bd93741aa273056d78372bc29f1e20b9.mp4","m3u8_file":"a_m3u8/56bc087de001ada5645b8eeb34aba556.m3u8","save_size":"388345","postfix_name":"mp4","item_cover":"a_class_item_cover/4c60a6e903f7029e462f4b9812df4735.png","content":""},{"item_id":"20","class_id":"1","item_title":"18 Golden Shell Awards_高清","chapter_name":"18 Golden Shell Awards_高清","click_count":"222","collect_count":"0","time_spend":"4:49","resource":"a_class_item_resource/2977ef90f3517f0e2fd9fdd49a010a4d.mp4","m3u8_file":"a_m3u8/42467be433c4a97c5a3df2492957270c.m3u8","save_size":"19791276","postfix_name":"mp4","item_cover":"a_class_item_cover/4cab5283793f1a09d5b82daa02ded5ce.jpg","content":""},{"item_id":"22","class_id":"1","item_title":"Hllow","chapter_name":"","click_count":"90","collect_count":"0","time_spend":"2:36","resource":"a_class_item_resource/d4731c5150b791425f3585d35885814b.mp4","m3u8_file":"a_m3u8/91bf04875a1a9987758ed066d4e6eb83.m3u8","save_size":"10722790","postfix_name":"mp4","item_cover":"a_class_item_cover/37ced64fa55001f39b1335fff109a890.png","content":""},{"item_id":"21","class_id":"1","item_title":"Ponytail in Hollywood","chapter_name":"","click_count":"58","collect_count":"0","time_spend":"2:09","resource":"a_class_item_resource/9dd8978f7a2b60a8645ab0165418ee0c.mp4","m3u8_file":"","save_size":"8891718","postfix_name":"mp4","item_cover":"a_class_item_cover/eeafc5a8ba57a6a0733d03f7db0de05a.png","content":""},{"item_id":"25","class_id":"1","item_title":"Hoola-Hooping Song","chapter_name":"1","click_count":"2","collect_count":"0","time_spend":"1:33","resource":"a_class_item_resource/5f39097811965155277a0fcae1141c8e.mp4","m3u8_file":"a_m3u8/b4f77582a5521a6fa3fa4125db4e63ee.m3u8","save_size":"6370090","postfix_name":"mp4","item_cover":"a_class_item_cover/b7f86cd2fd2e75099127c560034b233f.png","content":""},{"item_id":"28","class_id":"1","item_title":"Badanamu Time","chapter_name":"1","click_count":"2","collect_count":"0","time_spend":"1:51","resource":"a_class_item_resource/0685344f29c5335ce00d793f569c3b5f.mp4","m3u8_file":"a_m3u8/716db42d71146d57898ddfc98ec7ad18.m3u8","save_size":"7627412","postfix_name":"mp4","item_cover":"a_class_item_cover/61b8b67cdbc2125e8f691f9bb7429d70.png","content":""}]},{"class_id":"5","class_name":"儿歌","class_show_type":"4","data_list":[{"item_id":"15","class_id":"5","item_title":"儿歌两首","chapter_name":"","click_count":"100","collect_count":"0","time_spend":"","resource":"","m3u8_file":"","save_size":"0","postfix_name":"zj","item_cover":"a_class_item_cover/bb3eb0fe26bfb24625709fceea6277b8.jpg","content":""},{"item_id":"37","class_id":"5","item_title":"贝瓦儿歌","chapter_name":"","click_count":"1","collect_count":"0","time_spend":"","resource":"","m3u8_file":"","save_size":"0","postfix_name":"zj","item_cover":"a_class_item_cover/d9a8d32f1afd327b73fa99b249e530d4.png","content":""}]},{"class_id":"7","class_name":"胎教音乐","class_show_type":"4","data_list":[{"item_id":"12","class_id":"7","item_title":"古典音乐","chapter_name":"","click_count":"100","collect_count":"0","time_spend":"","resource":"","m3u8_file":"","save_size":"0","postfix_name":"zj","item_cover":"a_class_item_cover/d03973e0f48feb52c435fd15d616d55f.jpg","content":""},{"item_id":"39","class_id":"7","item_title":"小提琴","chapter_name":"","click_count":"1","collect_count":"0","time_spend":"","resource":"","m3u8_file":"","save_size":"0","postfix_name":"zj","item_cover":"a_class_item_cover/834c7cd5997e7ed94c911923f7175d9b.png","content":""}]}]}
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
        private List<String> classslideimg;
        private List<RecommendListBean> recommend_list;

        public List<String> getClassslideimg() {
            return classslideimg;
        }

        public void setClassslideimg(List<String> classslideimg) {
            this.classslideimg = classslideimg;
        }

        public List<RecommendListBean> getRecommend_list() {
            return recommend_list;
        }

        public void setRecommend_list(List<RecommendListBean> recommend_list) {
            this.recommend_list = recommend_list;
        }

        public static class RecommendListBean {
            /**
             * class_id : 1
             * class_name : 动画
             * class_show_type : 1
             * data_list : [{"item_id":"19","class_id":"1","item_title":"Hoola Song","chapter_name":"1","click_count":"310","collect_count":"0","time_spend":"0:10","resource":"a_class_item_resource/bd93741aa273056d78372bc29f1e20b9.mp4","m3u8_file":"a_m3u8/56bc087de001ada5645b8eeb34aba556.m3u8","save_size":"388345","postfix_name":"mp4","item_cover":"a_class_item_cover/4c60a6e903f7029e462f4b9812df4735.png","content":""},{"item_id":"20","class_id":"1","item_title":"18 Golden Shell Awards_高清","chapter_name":"18 Golden Shell Awards_高清","click_count":"222","collect_count":"0","time_spend":"4:49","resource":"a_class_item_resource/2977ef90f3517f0e2fd9fdd49a010a4d.mp4","m3u8_file":"a_m3u8/42467be433c4a97c5a3df2492957270c.m3u8","save_size":"19791276","postfix_name":"mp4","item_cover":"a_class_item_cover/4cab5283793f1a09d5b82daa02ded5ce.jpg","content":""},{"item_id":"22","class_id":"1","item_title":"Hllow","chapter_name":"","click_count":"90","collect_count":"0","time_spend":"2:36","resource":"a_class_item_resource/d4731c5150b791425f3585d35885814b.mp4","m3u8_file":"a_m3u8/91bf04875a1a9987758ed066d4e6eb83.m3u8","save_size":"10722790","postfix_name":"mp4","item_cover":"a_class_item_cover/37ced64fa55001f39b1335fff109a890.png","content":""},{"item_id":"21","class_id":"1","item_title":"Ponytail in Hollywood","chapter_name":"","click_count":"58","collect_count":"0","time_spend":"2:09","resource":"a_class_item_resource/9dd8978f7a2b60a8645ab0165418ee0c.mp4","m3u8_file":"","save_size":"8891718","postfix_name":"mp4","item_cover":"a_class_item_cover/eeafc5a8ba57a6a0733d03f7db0de05a.png","content":""},{"item_id":"25","class_id":"1","item_title":"Hoola-Hooping Song","chapter_name":"1","click_count":"2","collect_count":"0","time_spend":"1:33","resource":"a_class_item_resource/5f39097811965155277a0fcae1141c8e.mp4","m3u8_file":"a_m3u8/b4f77582a5521a6fa3fa4125db4e63ee.m3u8","save_size":"6370090","postfix_name":"mp4","item_cover":"a_class_item_cover/b7f86cd2fd2e75099127c560034b233f.png","content":""},{"item_id":"28","class_id":"1","item_title":"Badanamu Time","chapter_name":"1","click_count":"2","collect_count":"0","time_spend":"1:51","resource":"a_class_item_resource/0685344f29c5335ce00d793f569c3b5f.mp4","m3u8_file":"a_m3u8/716db42d71146d57898ddfc98ec7ad18.m3u8","save_size":"7627412","postfix_name":"mp4","item_cover":"a_class_item_cover/61b8b67cdbc2125e8f691f9bb7429d70.png","content":""}]
             */

            private String class_id;
            private String class_name;
            private String class_show_type;
            private List<DataListBean> data_list;

            public String getClass_id() {
                return class_id;
            }

            public void setClass_id(String class_id) {
                this.class_id = class_id;
            }

            public String getClass_name() {
                return class_name;
            }

            public void setClass_name(String class_name) {
                this.class_name = class_name;
            }

            public String getClass_show_type() {
                return class_show_type;
            }

            public void setClass_show_type(String class_show_type) {
                this.class_show_type = class_show_type;
            }

            public List<DataListBean> getData_list() {
                return data_list;
            }

            public void setData_list(List<DataListBean> data_list) {
                this.data_list = data_list;
            }

            public static class DataListBean {
                /**
                 * item_id : 19
                 * class_id : 1
                 * item_title : Hoola Song
                 * chapter_name : 1
                 * click_count : 310
                 * collect_count : 0
                 * time_spend : 0:10
                 * resource : a_class_item_resource/bd93741aa273056d78372bc29f1e20b9.mp4
                 * m3u8_file : a_m3u8/56bc087de001ada5645b8eeb34aba556.m3u8
                 * save_size : 388345
                 * postfix_name : mp4
                 * item_cover : a_class_item_cover/4c60a6e903f7029e462f4b9812df4735.png
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
    }
}