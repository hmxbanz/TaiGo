package com.xtdar.app.server.response;


import java.util.List;

public class Ad2Response {


    /**
     * data : [{"cover":"a_class_item_cover/c1270a80a450e0b92264f7fa7e65c054.jpg","ios_link":"https://itunes.apple.com/cn/app/taigo/id1258087895?mt=8","android_link":"http://android.myapp.com/myapp/detail.htm?apkName=com.xtdar.app"},{"cover":"a_class_item_cover/f26ef2ce56a8d1c74c53ac9996960c43.jpg","ios_link":"1","android_link":"1"}]
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
         * cover : a_class_item_cover/c1270a80a450e0b92264f7fa7e65c054.jpg
         * ios_link : https://itunes.apple.com/cn/app/taigo/id1258087895?mt=8
         * android_link : http://android.myapp.com/myapp/detail.htm?apkName=com.xtdar.app
         */

        private String cover;
        private String ios_link;
        private String android_link;

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public String getIos_link() {
            return ios_link;
        }

        public void setIos_link(String ios_link) {
            this.ios_link = ios_link;
        }

        public String getAndroid_link() {
            return android_link;
        }

        public void setAndroid_link(String android_link) {
            this.android_link = android_link;
        }
    }
}
