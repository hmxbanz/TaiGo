package com.xtdar.app.server.response;


import java.util.List;

public class ContactsResponse {

    /**
     *{"TotalPages":1,"state":1,"msg":"成功","Friends":[{"CreateDate":"\/Date(1470153991957)\/","FriendID":38,"ObjectUserInfoID":65,"ObjectUserID":65,"ObjectIcon":"/Images/User/2016/06/23/2016062323101882_s.jpeg","ObjectNickName":"爱美的娃","OwnerUserID":2,"OwnerUserInfoID":2,"OwnerNickName":"管理员","OwnerIcon":"/Images/User/2016/11/18/2016111823255252_s.jpg"}]}
     */

    private int state;
    private String msg;
    private String totalPages;
    private List<ResultEntity> result;

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
    public String getTotalPages() {
        return totalPages;
    }
    public void setTotalPages(String totalPages) {
        this.totalPages = totalPages;
    }
    public List<ResultEntity> getFriends() {
        return result;
    }
    public void setFriends(List<ResultEntity> result) {
        this.result = result;
    }
    public static class ResultEntity{
        private String FriendID;
        private String ObjectUserInfoID;
        private String ObjectIcon;
        private String ObjectNickName;
        private String CreateDate;
        private String letters;
        private String nameSpelling;

        public String getNameSpelling() {
            return nameSpelling;
        }
        public void setNameSpelling(String nameSpelling) {
            this.nameSpelling = nameSpelling;
        }

        public String getLetters() {
            return letters;
        }
        public void setLetters(String letters) {
            this.letters = letters;
        }

        public String getFriendID() {
            return FriendID;
        }
        public void setFriendID(String friendID) {
            FriendID = friendID;
        }

        public String getObjectUserInfoID() {
            return ObjectUserInfoID;
        }

        public void setObjectUserInfoID(String objectUserInfoID) {
            ObjectUserInfoID = objectUserInfoID;
        }

        public String getObjectIcon() {
            return ObjectIcon;
        }

        public void setObjectIcon(String objectIcon) {
            ObjectIcon = objectIcon;
        }

        public String getObjectNickName() {
            return ObjectNickName;
        }

        public void setObjectNickName(String objectNickName) {
            ObjectNickName = objectNickName;
        }

        public String getCreateDate() {
            return CreateDate;
        }

        public void setCreateDate(String createDate) {
            CreateDate = createDate;
        }
    }
}
