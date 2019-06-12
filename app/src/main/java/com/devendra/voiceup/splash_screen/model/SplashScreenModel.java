package com.devendra.voiceup.splash_screen.model;

import android.os.Handler;

import androidx.lifecycle.MutableLiveData;

import com.devendra.voiceup.utils.Preferences;

import javax.inject.Inject;

/**
 * Created by Devendra Mehra on 6/12/2019.
 */
public class SplashScreenModel {

    private Preferences preferences;
    private MutableLiveData<Boolean> booleanMutableLiveData;
    private Handler handler;

    @Inject
    public SplashScreenModel(Preferences preferences, Handler handler,
                             MutableLiveData<Boolean> booleanMutableLiveData) {
        this.preferences = preferences;
        this.booleanMutableLiveData = booleanMutableLiveData;
        this.handler = handler;
    }

    public MutableLiveData<Boolean> getBooleanMutableLiveData() {
        return booleanMutableLiveData;
    }


    public void whichActivityToView() {
        handler.postDelayed(() ->
                        booleanMutableLiveData
                                .setValue(preferences.isLoggedIn()),
                2000);
    }

}
