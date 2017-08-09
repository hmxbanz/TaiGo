package com.xtdar.app.server.response;


import java.util.List;

public class TaobaoResponse {

    /**
     * data : {"img_list":["a_class_item_cover/f9325ab63afa2df41ffc973ea75caa5c.jpg","a_class_item_cover/b2e92a8d9e90cd27b87d733ff57dbc9f.jpg"],"device_type_list":[{"device_type_id":"1","device_type_name":"AR枪","device_list":[{"device_id":"2","partner_id":"2","device_type":"1","device_name":"MG237 AR魔力枪","price":"220.00","service_uuid":"FFE5","read_uuid":"FFE4","write_uuid":"FFE9","device_img":"a_device_img/dc39cafaa413cde60e72478ae5b05b29.jpg","taobao_id":"https://detail.tmall.com/item.htm?spm=a230r.1.14.6.jE1Qvq&id=549995049215&cm_id=140105335569ed55e27b&abbucket=8&sku_properties=5919063:6536025"},{"device_id":"4","partner_id":"3","device_type":"1","device_name":"Taigo-1 AR智能枪","price":"523.00","service_uuid":"aaa","read_uuid":"aaa","write_uuid":"aaa","device_img":"a_device_img/736c58e02740d9addd315bae6909c160.png","taobao_id":"asdf"}]},{"device_type_id":"2","device_type_name":"智能车","device_list":[{"device_id":"3","partner_id":"3","device_type":"2","device_name":"可编程智能车","price":"220.00","service_uuid":"11223344","read_uuid":"sdf","write_uuid":"qwe","device_img":"a_device_img/bc5678f7e2c00ef31a2d2db781614379.jpg","taobao_id":"https://detail.tmall.com/item.htm?id=538708752187&ali_refid=a3_430583_1006:1121790994:N:%E6%99%BA%E8%83%BD%E8%BD%A6:4e2f5e94c7f640aeab58b6328c21ab68&ali_trackid=1_4e2f5e94c7f640aeab58b6328c21ab68&spm=a230r.1.14.1.WUOdCV"}]}]}
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
        private List<String> img_list;
        private List<DeviceTypeListBean> device_type_list;

        public List<String> getImg_list() {
            return img_list;
        }

        public void setImg_list(List<String> img_list) {
            this.img_list = img_list;
        }

        public List<DeviceTypeListBean> getDevice_type_list() {
            return device_type_list;
        }

        public void setDevice_type_list(List<DeviceTypeListBean> device_type_list) {
            this.device_type_list = device_type_list;
        }

        public static class DeviceTypeListBean {
            /**
             * device_type_id : 1
             * device_type_name : AR枪
             * device_list : [{"device_id":"2","partner_id":"2","device_type":"1","device_name":"MG237 AR魔力枪","price":"220.00","service_uuid":"FFE5","read_uuid":"FFE4","write_uuid":"FFE9","device_img":"a_device_img/dc39cafaa413cde60e72478ae5b05b29.jpg","taobao_id":"https://detail.tmall.com/item.htm?spm=a230r.1.14.6.jE1Qvq&id=549995049215&cm_id=140105335569ed55e27b&abbucket=8&sku_properties=5919063:6536025"},{"device_id":"4","partner_id":"3","device_type":"1","device_name":"Taigo-1 AR智能枪","price":"523.00","service_uuid":"aaa","read_uuid":"aaa","write_uuid":"aaa","device_img":"a_device_img/736c58e02740d9addd315bae6909c160.png","taobao_id":"asdf"}]
             */

            private String device_type_id;
            private String device_type_name;
            private List<DeviceListBean> device_list;

            public String getDevice_type_id() {
                return device_type_id;
            }

            public void setDevice_type_id(String device_type_id) {
                this.device_type_id = device_type_id;
            }

            public String getDevice_type_name() {
                return device_type_name;
            }

            public void setDevice_type_name(String device_type_name) {
                this.device_type_name = device_type_name;
            }

            public List<DeviceListBean> getDevice_list() {
                return device_list;
            }

            public void setDevice_list(List<DeviceListBean> device_list) {
                this.device_list = device_list;
            }

            public static class DeviceListBean {
                /**
                 * device_id : 2
                 * partner_id : 2
                 * device_type : 1
                 * device_name : MG237 AR魔力枪
                 * price : 220.00
                 * service_uuid : FFE5
                 * read_uuid : FFE4
                 * write_uuid : FFE9
                 * device_img : a_device_img/dc39cafaa413cde60e72478ae5b05b29.jpg
                 * taobao_id : 549995049215
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
                private String taobao_id;

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

                public String getTaobao_id() {
                    return taobao_id;
                }

                public void setTaobao_id(String taobao_id) {
                    this.taobao_id = taobao_id;
                }
            }
        }
    }
}