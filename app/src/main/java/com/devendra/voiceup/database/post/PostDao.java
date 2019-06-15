package com.devendra.voiceup.database.post;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.devendra.voiceup.database.JoinResult;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by Devendra Mehra on 6/8/2019.
 */
@Dao
public interface PostDao {

    @Query("SELECT post_title,post_type,file_name,user_name FROM Post INNER JOIN User " +
            "ON Post.user_id = User.user_id ORDER BY post_id DESC")
    Single<List<JoinResult>> getDisplayablePost();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPost(Post post);
}
