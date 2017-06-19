package com.xtdar.app.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by hmxbanz on 2017/3/8.
 */

public class User implements Parcelable {
    private String nickName;
    private String avator;
    private int ImgResource;
    private String nameSpelling;
    private String letters;

    public String getNickName() {
        return nickName;
    }
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvator() {
        return avator;
    }
    public void setAvator(String avator) {  this.avator = avator;   }

    public int getImgResource() {
        return ImgResource;
    }
    public void setImgResource(int imgResource) {
        ImgResource = imgResource;
    }

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
//        ParcelUtils.writeToParcel(dest, this.getTitle());
//        ParcelUtils.writeToParcel(dest, this.getLetters());
//        ParcelUtils.writeToParcel(dest, this.getAvator());
    }
}
