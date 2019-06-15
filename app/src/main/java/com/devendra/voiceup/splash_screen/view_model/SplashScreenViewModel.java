package com.devendra.voiceup.splash_screen.view_model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.devendra.voiceup.splash_screen.model.SplashScreenModel;

/**
 * Created by Devendra Mehra on 6/12/2019.
 */
public class SplashScreenViewModel extends ViewModel {

    private LiveData<Boolean> booleanLiveData;

    public SplashScreenViewModel(SplashScreenModel splashScreenModel) {
        booleanLiveData = splashScreenModel.getBooleanMutableLiveData();
        splashScreenModel.whichActivityToShow();
    }

    public LiveData<Boolean> getBooleanLiveData() {
        return booleanLiveData;
    }
}
