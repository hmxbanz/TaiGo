package com.xtdar.app.server.response;


import java.util.List;

/**
 * Created by AMing on 15/12/24.
 * Company RongCloud
 */
public class HomeResponse {

    private int state;
    private String msg;
    private List<ResultEntity> UserList;

    public void setState(int state) {
        this.state = state;
    }
    public void setUserList(List<ResultEntity> UserList) {
        this.UserList = UserList;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }
    public int getState() {
        return state;
    }
    public List<ResultEntity> getUserList() {
        return UserList;
    }
    public String getMsg() {
        return msg;
    }

    public static class ResultEntity {
        private int UserInfoID;
        private String NickName;
        private String IconSmall;

        public void setUserInfoID(int userInfoID) {
            this.UserInfoID = userInfoID;
        }
        public void setIconSmall(String iconSmall) {  this.IconSmall = iconSmall;
        }
        public void setNickName(String nickName) { this.NickName = nickName;     }

        public int getUserInfoID() {            return UserInfoID;        }
        public String getIconSmall() { return IconSmall;   }
        public String getNickName() { return NickName;   }
    }
}
