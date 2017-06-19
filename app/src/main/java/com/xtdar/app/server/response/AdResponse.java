package com.xtdar.app.server.response;


import com.xtdar.app.XtdConst;

import java.util.List;

public class AdResponse {

    /**

     {
     "data":[
     "a_class_item_cover/bb3eb0fe26bfb24625709fceea6277b8.jpg",
     "a_class_item_cover/d03973e0f48feb52c435fd15d616d55f.jpg"
     ],
     "code":1,
     "msg":"返回数据"
     }
  */

    private int code;
    private String msg;
    private List<String> result;
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

    public List<String> getData() {
        return result;
    }

    public void setData(List<String> result) {
        this.result = result;
    }


}
