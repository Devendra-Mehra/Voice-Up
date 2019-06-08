package com.devendra.voiceup.app.di;

import android.app.Application;
import android.content.Context;
import android.provider.SyncStateContract;

import androidx.room.Room;

import com.devendra.voiceup.database.AppDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Devendra Mehra on 5/6/2019.
 */
@Module
public class ApplicationModule {

    @Provides
    @Singleton
    Context providesContext(Application application) {
        return application;
    }

    @Provides
    @Singleton
    AppDatabase appDatabase(Context context) {
        return Room.databaseBuilder(context, AppDatabase.class,
                "Voice up").build();
    }


}
