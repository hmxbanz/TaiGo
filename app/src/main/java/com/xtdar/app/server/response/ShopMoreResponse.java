package com.xtdar.app.server.response;


import java.util.List;

public class ShopMoreResponse {


    /**
     * data : [{"device_id":"6","partner_id":"2","device_type":"1","device_name":"TaiGo0713","price":"229.00","service_uuid":"asdf","read_uuid":"asf","write_uuid":"asdf","device_img":"a_device_img/d2ba523ecc60eab9c1f30ca8aeb9742a.jpg","tips":"","taobao_url":"https://www.taobao.com/","device_conf":""}]
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
         * device_id : 6
         * partner_id : 2
         * device_type : 1
         * device_name : TaiGo0713
         * price : 229.00
         * service_uuid : asdf
         * read_uuid : asf
         * write_uuid : asdf
         * device_img : a_device_img/d2ba523ecc60eab9c1f30ca8aeb9742a.jpg
         * tips :
         * taobao_url : https://www.taobao.com/
         * device_conf :
         */

        private String device_id;
        private String partner_id;
        private String device_type;
        private String device_name;
        private String price;
        private String service_uuid;
        private String read_uuid;
        private String write_uuid;
        private String device_img;
        private String tips;
        private String taobao_id;
        private String device_conf;
        private String taobao_img;
        private String taobao_name;

        public String getTaobao_img() {
            return taobao_img;
        }

        public void setTaobao_img(String taobao_img) {
            this.taobao_img = taobao_img;
        }

        public String getTaobao_name() {
            return taobao_name;
        }

        public void setTaobao_name(String taobao_name) {
            this.taobao_name = taobao_name;
        }

        public String getDevice_id() {
            return device_id;
        }

        public void setDevice_id(String device_id) {
            this.device_id = device_id;
        }

        public String getPartner_id() {
            return partner_id;
        }

        public void setPartner_id(String partner_id) {
            this.partner_id = partner_id;
        }

        public String getDevice_type() {
            return device_type;
        }

        public void setDevice_type(String device_type) {
            this.device_type = device_type;
        }

        public String getDevice_name() {
            return device_name;
        }

        public void setDevice_name(String device_name) {
            this.device_name = device_name;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
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

        public String getDevice_img() {
            return device_img;
        }

        public void setDevice_img(String device_img) {
            this.device_img = device_img;
        }

        public String getTips() {
            return tips;
        }

        public void setTips(String tips) {
            this.tips = tips;
        }

        public String getTaobao_id() {
            return taobao_id;
        }

        public void setTaobao_id(String taobao_id) {
            this.taobao_id = taobao_id;
        }

        public String getDevice_conf() {
            return device_conf;
        }

        public void setDevice_conf(String device_conf) {
            this.device_conf = device_conf;
        }
    }
}
