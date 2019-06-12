package com.devendra.voiceup.splash_screen.view_model;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.devendra.voiceup.splash_screen.model.SplashScreenModel;

/**
 * Created by Devendra Mehra on 6/12/2019.
 */
public class SplashScreenViewModelFactory implements ViewModelProvider.Factory {

    private SplashScreenModel splashScreenModel;

    public SplashScreenViewModelFactory(SplashScreenModel splashScreenModel) {
        this.splashScreenModel = splashScreenModel;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {

        return (T) new SplashScreenViewModel(splashScreenModel);
    }
}
