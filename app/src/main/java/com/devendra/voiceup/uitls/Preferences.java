package com.devendra.voiceup.uitls;

import android.content.SharedPreferences;
import android.util.Log;

import javax.inject.Inject;


public class Preferences {

    private boolean loggedIn;

    private SharedPreferences preferences;

    @Inject
    public Preferences(SharedPreferences preferences) {
        this.preferences = preferences;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
        preferences.edit().putBoolean(Constants.getLoggedIn(), loggedIn).apply();
    }

    private void clearPrefs() {
        preferences.edit().clear().apply();
        loadPreferences();
    }


    public void loadPreferences() {
        Log.d("LOG10", "" + preferences);
        Log.d("LOG10", "" + loggedIn);
        loggedIn = preferences.getBoolean(Constants.getLoggedIn(), false);
    }
}
