package com.user.hilo.entitys;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/3/17.
 */
public class MainEntity implements Serializable {

    private String titleSubjectName;
    public int likesCount;
    private boolean isLiked;
    private int imageMipmap;

    public MainEntity() {
    }

    public MainEntity(String titleSubjectName, int likesCount, boolean isLiked, int imageMipmap) {
        this.titleSubjectName = titleSubjectName;
        this.likesCount = likesCount;
        this.isLiked = isLiked;
        this.imageMipmap = imageMipmap;
    }

    public int getImageMipmap() {
        return imageMipmap;
    }

    public void setImageUrl(int imageMipmap) {
        this.imageMipmap = imageMipmap;
    }

    public String getTitleSubjectName() {
        return titleSubjectName;
    }

    public void setTitleSubjectName(String titleSubjectName) {
        this.titleSubjectName = titleSubjectName;
    }

    public boolean isLiked() {
        return isLiked;
    }

    public void setLiked(boolean liked) {
        isLiked = liked;
    }
}
