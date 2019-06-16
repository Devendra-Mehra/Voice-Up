package com.devendra.voiceup.home.view;

import android.graphics.Bitmap;

/**
 * Created by Devendra Mehra on 6/15/2019.
 */
public class DisplayablePost {


    private String postTitle;
    private int postType;
    private String fileName;
    private String userName;
    private int dominantColor;
    private Bitmap bitmapThumbnail;

    public DisplayablePost() {
    }


    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public int getPostType() {
        return postType;
    }

    public void setPostType(int postType) {
        this.postType = postType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getDominantColor() {
        return dominantColor;
    }

    public void setDominantColor(int dominantColor) {
        this.dominantColor = dominantColor;
    }

    public Bitmap getBitmapThumbnail() {
        return bitmapThumbnail;
    }

    public void setBitmapThumbnail(Bitmap bitmapThumbnail) {
        this.bitmapThumbnail = bitmapThumbnail;
    }
}
