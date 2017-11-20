package com.xtdar.app.server.response;

import java.util.List;

//分类项列表返回
public class GameListResponse {


    /**
     * data : [{"game_id":"1","game_type_id":"1","game_name":"反恐精英","game_title":"半条命的游戏 等你挑战","game_img":"a_game_img/d9a663d76190b72f6a4a4f9e592fbb1b.jpg","game_config":"","min_an_ver":"1.0","min_ios_ver":"1.0"},{"game_id":"2","game_type_id":"2","game_name":"切水果","game_title":"切了个西瓜","game_img":"a_game_img/609f7e34d8c3825eb69d3f9b818ff8b4.jpg","game_config":"","min_an_ver":"1.0","min_ios_ver":"1.0"}]
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
         * game_id : 1
         * game_type_id : 1
         * game_name : 反恐精英
         * game_title : 半条命的游戏 等你挑战
         * game_img : a_game_img/d9a663d76190b72f6a4a4f9e592fbb1b.jpg
         * game_config :
         * min_an_ver : 1.0
         * min_ios_ver : 1.0
         */

        private String game_id;
        private String game_type_id;
        private String game_name;
        private String game_title;
        private String game_img;
        private String game_config;
        private int min_an_ver;
        private String min_ios_ver;
        private String load_size;
        private String ios_show;
        private String android_show;

        public String getLoad_size() {
            return load_size;
        }

        public void setLoad_size(String load_size) {
            this.load_size = load_size;
        }

        public String getIos_show() {
            return ios_show;
        }

        public void setIos_show(String ios_show) {
            this.ios_show = ios_show;
        }

        public String getAndroid_show() {
            return android_show;
        }

        public void setAndroid_show(String android_show) {
            this.android_show = android_show;
        }

        public String getGame_zip_ver() {
            return game_zip_ver;
        }

        public void setGame_zip_ver(String game_zip_ver) {
            this.game_zip_ver = game_zip_ver;
        }

        private String game_zip_ver;

        public boolean is_download() {
            return is_download;
        }

        public void setIs_download(boolean is_download) {
            this.is_download = is_download;
        }

        private boolean is_download;

        public GameConfig getGameConfig() {
            return gameConfig;
        }

        public void setGameConfig(GameConfig gameConfig) {
            this.gameConfig = gameConfig;
        }

        public GameConfig gameConfig;

        public String getGame_id() {
            return game_id;
        }

        public void setGame_id(String game_id) {
            this.game_id = game_id;
        }

        public String getGame_type_id() {
            return game_type_id;
        }

        public void setGame_type_id(String game_type_id) {
            this.game_type_id = game_type_id;
        }

        public String getGame_name() {
            return game_name;
        }

        public void setGame_name(String game_name) {
            this.game_name = game_name;
        }

        public String getGame_title() {
            return game_title;
        }

        public void setGame_title(String game_title) {
            this.game_title = game_title;
        }

        public String getGame_img() {
            return game_img;
        }

        public void setGame_img(String game_img) {
            this.game_img = game_img;
        }

        public String getGame_config() {
            return game_config;
        }

        public void setGame_config(String game_config) {
            this.game_config = game_config;
        }

        public int getMin_an_ver() {
            return min_an_ver;
        }

        public void setMin_an_ver(int min_an_ver) {
            this.min_an_ver = min_an_ver;
        }

        public String getMin_ios_ver() {
            return min_ios_ver;
        }

        public void setMin_ios_ver(String min_ios_ver) {
            this.min_ios_ver = min_ios_ver;
        }

        public static class GameConfig {

            /**
             * unity_game_id : 2
             * download_url : "http://"
             */

            private int unity_game_id;

            private String download_url;

            public String getDownload_url() {
                return download_url;
            }

            public void setDownload_url(String download_url) {
                this.download_url = download_url;
            }


            public int getUnity_game_id() {
                return unity_game_id;
            }

            public void setUnity_game_id(int unity_game_id) {
                this.unity_game_id = unity_game_id;
            }
        }
    }
}




