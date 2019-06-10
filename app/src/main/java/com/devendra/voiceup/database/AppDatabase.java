package com.devendra.voiceup.database;


import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.devendra.voiceup.database.post.Post;
import com.devendra.voiceup.database.post.PostDao;
import com.devendra.voiceup.database.users.UserDao;
import com.devendra.voiceup.database.users.Users;


@Database(entities = {Users.class, Post.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract UserDao getUserDao();

    public abstract PostDao getPostDao();

}
