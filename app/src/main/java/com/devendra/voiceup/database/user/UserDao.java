package com.devendra.voiceup.database.user;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import io.reactivex.Single;

/**
 * Created by Devendra Mehra on 6/8/2019.
 */
@Dao
public interface UserDao {


    @Query("SELECT COUNT(user_name) FROM User WHERE user_name = :userName")
    Single<Integer> getUserByUserName(String userName);


    @Query("SELECT * FROM User WHERE user_email = :email")
    Single<User> getUserByEmail(String email);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUser(User user);
}
