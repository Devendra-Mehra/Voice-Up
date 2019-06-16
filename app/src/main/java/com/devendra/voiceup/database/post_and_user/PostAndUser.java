package com.devendra.voiceup.database.post_and_user;

import androidx.room.ColumnInfo;

/**
 * Created by Devendra Mehra on 6/12/2019.
 */


public class PostAndUser {

    @ColumnInfo(name = "post_title")
    private String postTitle;

    @ColumnInfo(name = "post_type")
    private int postType;

    @ColumnInfo(name = "file_name")
    private String fileName;

    @ColumnInfo(name = "user_name")
    private String userName;

    @Override
    public String toString() {
        return "PostAndUser{" +
                "postTitle='" + postTitle + '\'' +
                ", postType=" + postType +
                ", fileName='" + fileName + '\'' +
                ", userName='" + userName + '\'' +
                '}';
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

}
