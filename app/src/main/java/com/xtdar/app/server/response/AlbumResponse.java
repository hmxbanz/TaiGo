package com.xtdar.app.server.response;


import java.util.List;

public class AlbumResponse {

    /**
     * state : 1
     * msg:成功
     * Albums:[{"AlbumID":3,"UserInfoID":2,"AlbumName":"证件照片","Type":2,"AlbumPhotoID":12,"PhotoInfo":"我的身份证","PhotoCreateDate":"\/Date(1457505993777)\/","IsDelete":null,"IsCover":true,"PhotoBig":"/Images/Album/2016/03/09/2016030914463369_b.jpg","PhotoSmall":"/Images/Album/2016/03/09/2016030914463369_s.jpg","NickName":"管理员","AlbumCreateDate":"\/Date(1457452800000)\/","Check":null,"CheckName":"未审核"}]
     */

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
    public List<ResultEntity> getAblums(){return result;}
    public void setAlbums(List<ResultEntity> result){this.result=result;}
    public class ResultEntity {
        public String AlbumID;
        public String AlbumName;
        public String Type;
        public String AlbumPhotoID;
        public String PhotoInfo;
        public String PhotoSmall;
        public String PhotoBig;

        public String getAlbumID() {
            return AlbumID;
        }

        public void setAlbumID(String albumID) {
            AlbumID = albumID;
        }

        public String getAlbumName() {
            return AlbumName;
        }

        public void setAlbumName(String albumName) {
            AlbumName = albumName;
        }

        public String getType() {
            return Type;
        }

        public void setType(String type) {
            Type = type;
        }

        public String getAlbumPhotoID() {
            return AlbumPhotoID;
        }

        public void setAlbumPhotoID(String albumPhotoID) {
            AlbumPhotoID = albumPhotoID;
        }

        public String getPhotoInfo() {
            return PhotoInfo;
        }

        public void setPhotoInfo(String photoInfo) {
            PhotoInfo = photoInfo;
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
    }
}
