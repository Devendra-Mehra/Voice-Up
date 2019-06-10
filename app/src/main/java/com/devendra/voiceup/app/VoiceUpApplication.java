package com.devendra.voiceup.app;

import android.app.Activity;
import android.app.Application;

import com.devendra.voiceup.app.di.DaggerApplicationComponent;
import com.devendra.voiceup.uitls.Preferences;

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

    @Inject
    Preferences preferences;

    @Override
    public void onCreate() {
        super.onCreate();
        DaggerApplicationComponent
                .builder()
                .application(this)
                .build()
                .inject(this);

        preferences.loadPreferences();
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return activityDispatchingAndroidInjector;
    }
}
