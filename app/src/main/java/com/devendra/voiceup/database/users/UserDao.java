package com.devendra.voiceup.database.users;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by Devendra Mehra on 6/8/2019.
 */
@Dao
public interface UserDao {

    @Query("SELECT * FROM Users")
    LiveData<List<Users>> getAllUsers();

    @Query("SELECT COUNT(user_name) FROM Users WHERE user_name = :userName")
    Single<Integer> getUserByUserName(String userName);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUsers(Users users);
}
