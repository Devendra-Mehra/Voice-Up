package com.devendra.voiceup.home.model;

import androidx.lifecycle.MutableLiveData;

import com.devendra.voiceup.utils.Preferences;

import javax.inject.Inject;

/**
 * Created by Devendra Mehra on 6/12/2019.
 */
public class HomeModel {

    private Preferences preferences;
    private MutableLiveData<Boolean> logoutMutableLiveData;


    @Inject
    public HomeModel(Preferences preferences,
                     MutableLiveData<Boolean> logoutMutableLiveData) {
        this.preferences = preferences;
        this.logoutMutableLiveData = logoutMutableLiveData;
    }

    public void logout() {
        preferences.clearPrefs();
        logoutMutableLiveData.setValue(true);
    }

    public MutableLiveData<Boolean> getLogoutMutableLiveData() {
        return logoutMutableLiveData;
    }
}
