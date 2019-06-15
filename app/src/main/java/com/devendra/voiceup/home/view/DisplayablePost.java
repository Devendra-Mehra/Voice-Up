package com.devendra.voiceup.home.view;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.devendra.voiceup.database.post_and_user.PostAndUser;
import com.devendra.voiceup.utils.BitmapHelper;
import com.devendra.voiceup.utils.Constants;

import static com.devendra.voiceup.utils.BitmapHelper.getBitmap;

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

    public DisplayablePost(String postTitle, int postType, String fileName, String userName, int dominantColor, Bitmap bitmapThumbnail) {
        this.postTitle = postTitle;
        this.postType = postType;
        this.fileName = fileName;
        this.userName = userName;
        this.dominantColor = dominantColor;
        this.bitmapThumbnail = bitmapThumbnail;
    }

    public DisplayablePost() {
    }

    public DisplayablePost(PostAndUser postAndUser){
        DisplayablePost displayablePost = new DisplayablePost();
        displayablePost.setPostTitle(postAndUser.getPostTitle());
        displayablePost.setPostType(postAndUser.getPostType());
        displayablePost.setUserName("By: " + postAndUser.getUserName());
        displayablePost.setFileName(postAndUser.getFileName());
        if (Constants.PHOTO == postAndUser.getPostType()) {
            Log.d("Log15", "" + Constants.FILE_LOCATION +
                    postAndUser.getFileName());
            displayablePost.setDominantColor(
                    BitmapHelper.getDominantColor(
                            BitmapFactory.decodeFile(
                                    Constants.FILE_LOCATION +
                                            postAndUser.getFileName())
                    ));
        } else {
            displayablePost.setBitmapThumbnail(getBitmap(Constants.FILE_LOCATION
                    + postAndUser.getFileName()));
            displayablePost.setDominantColor(
                    BitmapHelper.getDominantColor(
                            getBitmap(Constants.FILE_LOCATION
                                    + postAndUser.getFileName())));
        }
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

    @Override
    public String toString() {
        return "DisplayablePost{" +
                "postTitle='" + postTitle + '\'' +
                ", postType=" + postType +
                ", fileName='" + fileName + '\'' +
                ", userName='" + userName + '\'' +
                ", cominantColor=" + dominantColor +
                ", bitmapThumbnail=" + bitmapThumbnail +
                '}';
    }
}
