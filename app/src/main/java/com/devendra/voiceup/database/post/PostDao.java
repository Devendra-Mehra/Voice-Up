package com.devendra.voiceup.database.post;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

/**
 * Created by Devendra Mehra on 6/8/2019.
 */
@Dao
public interface PostDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPost(Post post);
}
