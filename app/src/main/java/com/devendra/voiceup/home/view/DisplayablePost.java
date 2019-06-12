package com.devendra.voiceup.home.view;

import androidx.room.ColumnInfo;

import com.devendra.voiceup.database.user.User;

/**
 * Created by Devendra Mehra on 6/12/2019.
 */
public class DisplayablePost {

    @ColumnInfo(name = "post_title")
    private String postTitle;

    @ColumnInfo(name = "post_type")
    private int postType;

    @ColumnInfo(name = "post_file_path")
    private String postFilePath;

    @ColumnInfo(name = "user_name")
    private String userName;


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

    public String getPostFilePath() {
        return postFilePath;
    }

    public void setPostFilePath(String postFilePath) {
        this.postFilePath = postFilePath;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "DisplayablePost{" +
                ", postTitle='" + postTitle + '\'' +
                ", postType=" + postType +
                ", postFilePath='" + postFilePath + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }
}
