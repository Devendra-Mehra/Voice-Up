package com.devendra.voiceup.database.user;

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

    @Query("SELECT * FROM User")
    LiveData<List<User>> getAllUsers();

    @Query("SELECT COUNT(user_name) FROM User WHERE user_name = :userName")
    Single<Integer> getUserByUserName(String userName);


    @Query("SELECT * FROM User WHERE user_name = :email AND user_password=:password")
    Single<User> getUserByCredentials(String email, String password);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUser(User user);
}
