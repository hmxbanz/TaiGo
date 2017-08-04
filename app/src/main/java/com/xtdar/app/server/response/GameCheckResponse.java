package com.xtdar.app.server.response;


import java.util.List;

public class GameCheckResponse {


    /**
     * data : [{"device_id":"2","device_name":"BLE","service_uuid":"ae00","read_uuid":"ae02","write_uuid":"ae01","taobao_url":"https://detail.tmall.com/item.htm?spm=a230r.1.14.6.jE1Qvq&id=549995049215&cm_id=140105335569ed55e27b&abbucket=8&sku_properties=5919063:6536025","mac_address":"CA:C1:B9:B7:90:1A"},{"device_id":"4","device_name":"BLE","service_uuid":"ae00","read_uuid":"ae02","write_uuid":"ae01","taobao_url":"asdf","mac_address":"21:E4:8D:45:CA:E7"}]
     * code : 1
     * msg : 用户可玩设备列表
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
         * device_id : 2
         * device_name : BLE
         * service_uuid : ae00
         * read_uuid : ae02
         * write_uuid : ae01
         * taobao_url : https://detail.tmall.com/item.htm?spm=a230r.1.14.6.jE1Qvq&id=549995049215&cm_id=140105335569ed55e27b&abbucket=8&sku_properties=5919063:6536025
         * mac_address : CA:C1:B9:B7:90:1A
         */

        private String device_id;
        private String device_name;
        private String service_uuid;
        private String read_uuid;
        private String write_uuid;
        private String taobao_url;
        private String mac_address;

        public String getDevice_conf() {
            return device_conf;
        }

        public void setDevice_conf(String device_conf) {
            this.device_conf = device_conf;
        }

        private String device_conf;

        public DeviceConfig getDeviceConfig() {
            return deviceConfig;
        }

        public void setDeviceConfig(DeviceConfig deviceConfig) {
            this.deviceConfig = deviceConfig;
        }

        private DeviceConfig deviceConfig;

        public Integer getStatus() {
            return status;
        }
        public void setStatus(int status) {
            this.status = status;
        }

        private int status;////////////////////////////////////////////////增加的属性和后台不一样

        public String getDevice_id() {
            return device_id;
        }

        public void setDevice_id(String device_id) {
            this.device_id = device_id;
        }

        public String getDevice_name() {
            return device_name;
        }

        public void setDevice_name(String device_name) {
            this.device_name = device_name;
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

        public String getTaobao_url() {
            return taobao_url;
        }

        public void setTaobao_url(String taobao_url) {
            this.taobao_url = taobao_url;
        }

        public String getMac_address() {
            return mac_address;
        }

        public void setMac_address(String mac_address) {
            this.mac_address = mac_address;
        }

        public static class DeviceConfig{
            public String getIsHigh() {
                return isHigh;
            }

            public void setIsHigh(String isHigh) {
                this.isHigh = isHigh;
            }

            private String isHigh;

        }
    }
}
