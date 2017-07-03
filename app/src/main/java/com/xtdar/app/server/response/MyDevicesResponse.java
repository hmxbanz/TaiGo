package com.xtdar.app.server.response;


import java.util.List;

public class MyDevicesResponse {


    /**
     * data : [{"bind_device_id":"8","user_id":"39","device_item_id":"27","device_id":"2","device_item_name":"TAv22u-B903","device_name":"MG237 AR魔力枪","device_img":"a_device_img/dc39cafaa413cde60e72478ae5b05b29.jpg","service_uuid":"FFE5","read_uuid":"FFE4","write_uuid":"FFE9"}]
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
         * bind_device_id : 8
         * user_id : 39
         * device_item_id : 27
         * device_id : 2
         * device_item_name : TAv22u-B903
         * device_name : MG237 AR魔力枪
         * device_img : a_device_img/dc39cafaa413cde60e72478ae5b05b29.jpg
         * service_uuid : FFE5
         * read_uuid : FFE4
         * write_uuid : FFE9
         */

        private String bind_device_id;
        private String user_id;
        private String device_item_id;
        private String device_id;
        private String device_item_name;
        private String device_name;
        private String device_img;
        private String service_uuid;
        private String read_uuid;
        private String write_uuid;

        public String getBind_device_id() {
            return bind_device_id;
        }

        public void setBind_device_id(String bind_device_id) {
            this.bind_device_id = bind_device_id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getDevice_item_id() {
            return device_item_id;
        }

        public void setDevice_item_id(String device_item_id) {
            this.device_item_id = device_item_id;
        }

        public String getDevice_id() {
            return device_id;
        }

        public void setDevice_id(String device_id) {
            this.device_id = device_id;
        }

        public String getDevice_item_name() {
            return device_item_name;
        }

        public void setDevice_item_name(String device_item_name) {
            this.device_item_name = device_item_name;
        }

        public String getDevice_name() {
            return device_name;
        }

        public void setDevice_name(String device_name) {
            this.device_name = device_name;
        }

        public String getDevice_img() {
            return device_img;
        }

        public void setDevice_img(String device_img) {
            this.device_img = device_img;
        }

        public String getService_uuid() {
            return service_uuid;
        }

        public void setService_uuid(String service_uuid) {
            this.service_uuid = service_uuid;
        }

        public String getRead_uuid() {
            return read_uuid;
        }

        public void setRead_uuid(String read_uuid) {
            this.read_uuid = read_uuid;
        }

        public String getWrite_uuid() {
            return write_uuid;
        }

        public void setWrite_uuid(String write_uuid) {
            this.write_uuid = write_uuid;
        }
    }
}