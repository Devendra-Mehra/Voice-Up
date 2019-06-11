package com.devendra.voiceup.database.post;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

/**
 * Created by Devendra Mehra on 6/8/2019.
 */
@Dao
public interface PostDao {
    @Query("SELECT * FROM Post")
    LiveData<List<Post>> getAllPost();
}
