package com.devendra.voiceup.app;

import android.app.Activity;
import android.app.Application;

import com.devendra.voiceup.BuildConfig;
import com.devendra.voiceup.app.di.DaggerApplicationComponent;
import com.devendra.voiceup.utils.Preferences;
import com.facebook.stetho.Stetho;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

/**
 * Created by Devendra Mehra on 6/8/2019.
 */
public class VoiceUpApplication extends Application implements HasActivityInjector {
    @Inject
    DispatchingAndroidInjector<Activity> activityDispatchingAndroidInjector;

    @Override
    public void onCreate() {
        super.onCreate();
        DaggerApplicationComponent
                .builder()
                .application(this)
                .build()
                .inject(this);


        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this);
        }
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return activityDispatchingAndroidInjector;
    }
}
