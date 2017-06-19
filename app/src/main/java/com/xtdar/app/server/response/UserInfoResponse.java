package com.xtdar.app.server.response;


public class UserInfoResponse {
    private int code;
    private String msg;
    private ResultEntity result;


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
    public ResultEntity getData() {
        return result;
    }
    public void setData(ResultEntity result) {
        this.result = result;
    }

    public class ResultEntity{
        private int user_id;
        private String user_name;
        private String nick_name;
        private String cell_phone;
        private String email;
        private String img_path;
        private String sex;
        private String age;

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getNick_name() {
            return nick_name;
        }

        public void setNick_name(String nick_name) {
            this.nick_name = nick_name;
        }

        public String getCell_phone() {
            return cell_phone;
        }

        public void setCell_phone(String cell_phone) {
            this.cell_phone = cell_phone;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getImg_path() {
            return img_path;
        }

        public void setImg_path(String img_path) {
            this.img_path = img_path;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }
    }

}
