package com.devendra.voiceup.app.di;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.MediaController;

import androidx.room.Room;

import com.devendra.voiceup.database.AppDatabase;
import com.devendra.voiceup.utils.Constants;
import com.devendra.voiceup.utils.Preferences;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

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
                Constants.DATABASE_NAME).build();
    }


    @Provides
    @Singleton
    SharedPreferences getSharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }


    @Provides
    @Singleton
    Preferences getPreferences(SharedPreferences sharedPreferences) {
        return new Preferences(sharedPreferences);
    }

    @Provides
    @Singleton
    MediaController provideMediaController(Application application) {
        return new MediaController(application);
    }


    @Provides
    @Singleton
    Disposable provideDisposable() {
        return new CompositeDisposable();
    }

}
