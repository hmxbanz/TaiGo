package com.xtdar.app.server.response;


public class CommonResponse {

    /**
     * code : 200
     * result : {"id":"t1hWCOGvX","token":"B0DA/kKanJviD5xxUzhwsEFIJad0/86YwGxBwz1417WFQi/Vr2OJay26s5IFDffGZaUYRMAkvN0ikvOcTl7RN9JilKZlosfQ"}
     */

    private int code;
    private String msg;

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

}
