package com.devendra.voiceup.database.post_and_user;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by Devendra Mehra on 6/8/2019.
 */
@Dao
public interface PostAndUserDao {

    @Query("SELECT post_title,post_type,file_name,user_name FROM Post INNER JOIN User " +
            "ON Post.user_id = User.user_id ORDER BY post_id DESC")
    Single<List<PostAndUser>> getDisplayablePost();
}
