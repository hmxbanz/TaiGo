package com.xtdar.app.server.response;


import java.util.List;

public class FavorResponse {

    /**
     *
     *{"TotalPages":1,"state":1,"msg":"成功","Favors":[{"CreateDate":"\/Date(1495326878173)\/","FavorID":25,"ObjectUserInfoID":19,"ObjectUserID":19,"ObjectIcon":"/Images/User/2016/04/03/2016040319522837_s.jpeg","ObjectNickName":"知足常乐","OwnerUserID":2,"OwnerUserInfoID":2,"OwnerNickName":"管理员","OwnerIcon":"/Images/User/2016/11/18/2016111823255252_s.jpg"}]}
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
    public List<ResultEntity> getFavors() {
        return result;
    }
    public void setFavors(List<ResultEntity> result) {
        this.result = result;
    }
    public static class ResultEntity {
        private String FavorID;
        private String ObjectUserInfoID;
        private String ObjectIcon;
        private String ObjectNickName;
        private String CreateDate;

        public String getFavorID() {
            return FavorID;
        }

        public void setFavorID(String favorID) {
            FavorID = favorID;
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
