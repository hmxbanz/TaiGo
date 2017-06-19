package com.xtdar.app.server.response;


import java.util.List;

public class SongDetailResponse {


    /**
     * data : {"album_info":{"item_id":"12","class_id":"7","item_title":"古典音乐","chapter_name":"","click_count":"102","collect_count":"0","time_spend":"","age_group":"","resource":"","m3u8_file":"","save_size":"0","postfix_name":"zj","item_cover":"a_class_item_cover/d03973e0f48feb52c435fd15d616d55f.jpg","content":""},"song_list":[{"album_id":"12","item_id":"13","item_title":"卡农","click_count":"117","time_spend":"5:24","resource":"a_class_item_resource/ce378bced32826369c68cf8fb2d6d802.mp3","save_size":"1297489","item_cover":"a_class_item_cover/d03973e0f48feb52c435fd15d616d55f.jpg"}],"song_num":1}
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
         * album_info : {"item_id":"12","class_id":"7","item_title":"古典音乐","chapter_name":"","click_count":"102","collect_count":"0","time_spend":"","age_group":"","resource":"","m3u8_file":"","save_size":"0","postfix_name":"zj","item_cover":"a_class_item_cover/d03973e0f48feb52c435fd15d616d55f.jpg","content":""}
         * song_list : [{"album_id":"12","item_id":"13","item_title":"卡农","click_count":"117","time_spend":"5:24","resource":"a_class_item_resource/ce378bced32826369c68cf8fb2d6d802.mp3","save_size":"1297489","item_cover":"a_class_item_cover/d03973e0f48feb52c435fd15d616d55f.jpg"}]
         * song_num : 1
         */

        private AlbumInfoBean album_info;
        private int song_num;
        private List<SongListBean> song_list;

        public AlbumInfoBean getAlbum_info() {
            return album_info;
        }

        public void setAlbum_info(AlbumInfoBean album_info) {
            this.album_info = album_info;
        }

        public int getSong_num() {
            return song_num;
        }

        public void setSong_num(int song_num) {
            this.song_num = song_num;
        }

        public List<SongListBean> getSong_list() {
            return song_list;
        }

        public void setSong_list(List<SongListBean> song_list) {
            this.song_list = song_list;
        }

        public static class AlbumInfoBean {
            /**
             * item_id : 12
             * class_id : 7
             * item_title : 古典音乐
             * chapter_name :
             * click_count : 102
             * collect_count : 0
             * time_spend :
             * age_group :
             * resource :
             * m3u8_file :
             * save_size : 0
             * postfix_name : zj
             * item_cover : a_class_item_cover/d03973e0f48feb52c435fd15d616d55f.jpg
             * content :
             */

            private String item_id;
            private String class_id;
            private String item_title;
            private String chapter_name;
            private String click_count;
            private String collect_count;
            private String time_spend;
            private String age_group;
            private String resource;
            private String m3u8_file;
            private String save_size;
            private String postfix_name;
            private String item_cover;
            private String content;
            private String class_name;

            public String getClass_name() {
                return class_name;
            }

            public void setClass_name(String class_name) {
                this.class_name = class_name;
            }

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

            public String getAge_group() {
                return age_group;
            }

            public void setAge_group(String age_group) {
                this.age_group = age_group;
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

        public static class SongListBean {
            /**
             * album_id : 12
             * item_id : 13
             * item_title : 卡农
             * click_count : 117
             * time_spend : 5:24
             * resource : a_class_item_resource/ce378bced32826369c68cf8fb2d6d802.mp3
             * save_size : 1297489
             * item_cover : a_class_item_cover/d03973e0f48feb52c435fd15d616d55f.jpg
             */

            private String album_id;
            private String item_id;
            private String item_title;
            private String click_count;
            private String time_spend;
            private String resource;
            private String save_size;
            private String item_cover;

            public String getAlbum_id() {
                return album_id;
            }

            public void setAlbum_id(String album_id) {
                this.album_id = album_id;
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

            public String getClick_count() {
                return click_count;
            }

            public void setClick_count(String click_count) {
                this.click_count = click_count;
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

            public String getSave_size() {
                return save_size;
            }

            public void setSave_size(String save_size) {
                this.save_size = save_size;
            }

            public String getItem_cover() {
                return item_cover;
            }

            public void setItem_cover(String item_cover) {
                this.item_cover = item_cover;
            }
        }
    }
}
