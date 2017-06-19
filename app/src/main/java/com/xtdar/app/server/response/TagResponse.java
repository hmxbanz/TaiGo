package com.xtdar.app.server.response;


import java.util.List;

public class TagResponse {

    /**

     {
     "data":[
     {
     "class_id":"101",
     "class_name":"推荐",
     "class_show_type":"100"
     }
     ],
     "code":1,
     "msg":"返回数据"
     }

  */

    private int code;
    private String msg;
    private List<ResultEntity> result;
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

    public List<ResultEntity> getData() {
        return result;
    }

    public void setData(List<ResultEntity> result) {
        this.result = result;
    }

    public class ResultEntity{
        public int class_id;
        public String class_name;
        public int class_show_type;

        public int getClass_id() {
            return class_id;
        }

        public void setClass_id(int class_id) {
            this.class_id = class_id;
        }

        public String getClass_name() {
            return class_name;
        }

        public void setClass_name(String class_name) {
            this.class_name = class_name;
        }

        public int getClass_show_type() {
            return class_show_type;
        }

        public void setClass_show_type(int class_show_type) {
            this.class_show_type = class_show_type;
        }
    }

}
