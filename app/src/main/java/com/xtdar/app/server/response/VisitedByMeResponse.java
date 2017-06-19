package com.xtdar.app.server.response;


import java.util.List;

public class VisitedByMeResponse {

    /**
     *
     *{"TotalPages":1,"state":1,"msg":"成功","VisitRecordsByMe":[{"CreateDate":"\/Date(1493170902237)\/","VisitRecordID":1642,"ObjectUserInfoID":501,"ObjectUserID":501,"ObjectIcon":"/Images/User/2017/04/05/2017040521420012_s.jpg","ObjectNickName":"小蔡","OwnerUserID":2,"OwnerUserInfoID":2,"OwnerNickName":"管理员","OwnerIcon":"/Images/User/2016/11/18/2016111823255252_s.jpg"}]}
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
    public List<ResultEntity> getVisitRecordsByMe() {
        return result;
    }
    public void setVisitRecordsByMe(List<ResultEntity> result) {
        this.result = result;
    }
    public static class ResultEntity {
        private String VisitRecordID;
        private String ObjectUserInfoID;
        private String ObjectIcon;
        private String ObjectNickName;
        private String CreateDate;

        public String getVisitRecordID() {
            return VisitRecordID;
        }

        public void setVisitRecordID(String visitRecordID) {
            VisitRecordID = visitRecordID;
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

