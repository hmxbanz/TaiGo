package com.xtdar.app.server.response;


import java.util.List;

/**
 * Created by AMing on 15/12/24.
 * Company RongCloud
 */
public class HomeDynamicResponse {

    private int state;
    private String msg;
    private int totalPages;
    private List<ResultEntity> LifeShares;

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
    public void setState(int state) {
        this.state = state;
    }
    public void setLifeShares(List<ResultEntity> lifeShares) {
        this.LifeShares = lifeShares;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getTotalPages() {
        return totalPages;
    }
    public int getState() {
        return state;
    }
    public List<ResultEntity> getLifeShares() {
        return LifeShares;
    }
    public String getMsg() {
        return msg;
    }

    public static class ResultEntity {
        private int LifeShareID;
        private int UserInfoID;
        private String NickName;
        private String IconSmall;
        private String Content;
        private String CreateDate;
        private List<PhotoEntity> LifePhotoes;

        public void setLifePhotoes(List<PhotoEntity> lifePhotoes) {
            this.LifePhotoes = lifePhotoes;
        }
        public List<PhotoEntity> getLifePhotoes() {            return LifePhotoes;        }
        public void setCreateDate(String createDate) {
            this.CreateDate = createDate;
        }
        public void setLifeShareID(int lifeShareID) {
            this.LifeShareID = lifeShareID;
        }
        public void setUserInfoID(int userInfoID) {
            this.UserInfoID = userInfoID;
        }
        public void setIconSmall(String iconSmall) {  this.IconSmall = iconSmall;
        }
        public void setNickName(String nickName) { this.NickName = nickName;     }
        public void setContent(String content) {
            this.Content = content;
        }

        public int getLifeShareID() {            return LifeShareID;        }
        public int getUserInfoID() {            return UserInfoID;        }
        public String getIconSmall() { return IconSmall;   }
        public String getNickName() { return NickName;   }
        public String getContent() { return Content;   }
        public String getCreateDate() { return CreateDate;   }

        public static class PhotoEntity {
            private int LifePhotoID;
            private int PhotoType;
            private String PhotoSmall;

            public void setLifePhotoID(int lifePhotoID) {
                this.LifePhotoID = lifePhotoID;
            }
            public int getLifePhotoID() { return LifePhotoID;}
            public void setPhotoType(int photoType) {  this.PhotoType = photoType; }
            public int getPhotoType() { return PhotoType;}
            public void setPhotoSmall(String photoSmall) { this.PhotoSmall = photoSmall;     }
            public String getPhotoSmall() { return PhotoSmall;   }


        }
    }
}
