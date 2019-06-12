package com.devendra.voiceup.splash_screen.di;

import android.os.Handler;

import androidx.lifecycle.MutableLiveData;

import com.devendra.voiceup.splash_screen.model.SplashScreenModel;
import com.devendra.voiceup.splash_screen.view_model.SplashScreenViewModelFactory;
import com.devendra.voiceup.utils.Preferences;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Devendra Mehra on 6/12/2019.
 */
@Module
public class SplashScreenModule {

    @Provides
    SplashScreenViewModelFactory providesSplashScreenViewModelFactory(SplashScreenModel splashScreenModel) {
        return new SplashScreenViewModelFactory(splashScreenModel);
    }

    @Provides
    SplashScreenModel providesSplashScreenModel(Preferences preferences) {
        return new SplashScreenModel(preferences, new Handler(), new MutableLiveData<>());
    }
}
