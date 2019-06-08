package com.devendra.voiceup.database.post;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Created by Devendra Mehra on 6/8/2019.
 */
@Entity
public class Post {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "post_id")
    private int postId;

    @ColumnInfo(name = "post_title")
    private String postTitle;

    @ColumnInfo(name = "user_id")
    private int userId;

    @ColumnInfo(name = "post_type")
    private int postType;

    @ColumnInfo(name = "post_file_path")
    private String postFilePath;

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    @Override
    public String toString() {
        return "Post{" +
                "postId=" + postId +
                ", postTitle='" + postTitle + '\'' +
                ", userId=" + userId +
                ", postType=" + postType +
                ", postFilePath='" + postFilePath + '\'' +
                '}';
    }
}
