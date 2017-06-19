package com.xtdar.app.server.response;


public class RegisterResponse {

    /**
     * code : 200
     * result : {"id":"t1hWCOGvX","token":"B0DA/kKanJviD5xxUzhwsEFIJad0/86YwGxBwz1417WFQi/Vr2OJay26s5IFDffGZaUYRMAkvN0ikvOcTl7RN9JilKZlosfQ"}
     */
    /**
     * id : t1hWCOGvX
     * token : B0DA/kKanJviD5xxUzhwsEFIJad0/86YwGxBwz1417WFQi/Vr2OJay26s5IFDffGZaUYRMAkvN0ikvOcTl7RN9JilKZlosfQ
     */
    private int state;
    private String msg;
    private ResultEntity result;

    public int getState() {
        return state;
    }
    public void setState(int state) {
        this.state = state;
    }
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }
    public ResultEntity getResult() {
        return result;
    }
    public void setResult(ResultEntity result) {
        this.result = result;
    }

    public static class ResultEntity {
        private String id;
        private String token;

        public String getId() {
            return id;
        }
        public void setId(String id) {
            this.id = id;
        }
        public String getToken() {
            return token;
        }
        public void setToken(String token) {
            this.token = token;
        }
    }
}
