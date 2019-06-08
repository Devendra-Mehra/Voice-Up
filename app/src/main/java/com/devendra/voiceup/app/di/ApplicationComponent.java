package com.devendra.voiceup.app.di;

import android.app.Application;

import com.devendra.voiceup.app.VoiceUpApplication;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

/**
 * Created by Devendra Mehra on 5/6/2019.
 */
@Singleton
@Component(modules = {AndroidInjectionModule.class,
        ApplicationModule.class,
        ActivityBuilder.class})
public interface ApplicationComponent {


    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);

        ApplicationComponent build();

    }

    void inject(VoiceUpApplication voiceUpApplication);
}
