package com.devendra.voiceup.database.post;

import androidx.room.Dao;
import androidx.room.Query;

import com.devendra.voiceup.home.view.DisplayablePost;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by Devendra Mehra on 6/8/2019.
 */
@Dao
public interface PostDao {

    @Query("SELECT post_title,post_type,post_file_path,user_name FROM Post INNER JOIN User ON Post.user_id = User.user_id")
    Single<List<DisplayablePost>> getDisplayablePost();
}
