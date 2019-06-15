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

    @ColumnInfo(name = "file_name")
    private String fileName;

    @Override
    public String toString() {
        return "Post{" +
                "postId=" + postId +
                ", postTitle='" + postTitle + '\'' +
                ", userId=" + userId +
                ", postType=" + postType +
                ", fileName='" + fileName + '\'' +
                '}';
    }

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

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
