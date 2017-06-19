package com.xtdar.app.server.response;


import java.util.List;

public class PhotoResponse {

    private int state;
    private String msg;
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
    public List<ResultEntity> getAlbumPhotoes() {
        return result;
    }
    public void setAlbumPhotoes(List<ResultEntity> result) {
        this.result = result;
    }
    public static class ResultEntity {
        private String PhotoInfo;
        private String AlbumID;
        private String AlbumPhotoID;
        private String PhotoSmall;
        private String PhotoBig;
        private String IsDelete;
        private boolean IsCover;

        public String getPhotoInfo() {
            return PhotoInfo;
        }

        public void setPhotoInfo(String photoInfo) {
            PhotoInfo = photoInfo;
        }

        public String getAlbumID() {
            return AlbumID;
        }

        public void setAlbumID(String albumID) {
            AlbumID = albumID;
        }

        public String getAlbumPhotoID() {
            return AlbumPhotoID;
        }

        public void setAlbumPhotoID(String albumPhotoID) {
            AlbumPhotoID = albumPhotoID;
        }

        public String getPhotoSmall() {
            return PhotoSmall;
        }

        public void setPhotoSmall(String photoSmall) {
            PhotoSmall = photoSmall;
        }

        public String getPhotoBig() {
            return PhotoBig;
        }

        public void setPhotoBig(String photoBig) {
            PhotoBig = photoBig;
        }

        public String getIsDelete() {
            return IsDelete;
        }

        public void setIsDelete(String isDelete) {
            IsDelete = isDelete;
        }

        public boolean isCover() {
            return IsCover;
        }

        public void setCover(boolean cover) {
            IsCover = cover;
        }
    }
}
