package com.devendra.voiceup.database.users;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by Devendra Mehra on 6/8/2019.
 */
@Dao
public interface UserDao {

    @Query("SELECT * FROM Users")
    Single<List<Users>> getAllUsers();
}
