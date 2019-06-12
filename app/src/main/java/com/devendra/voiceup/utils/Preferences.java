package com.devendra.voiceup.utils;

import android.content.SharedPreferences;

import javax.inject.Inject;


public class Preferences {

    private boolean loggedIn;
    private long userId;

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

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
        preferences.edit().putLong(Constants.getUserId(), userId).apply();
    }

    private void clearPrefs() {
        preferences.edit().clear().apply();
        loadPreferences();
    }


    public void loadPreferences() {
        loggedIn = preferences.getBoolean(Constants.getLoggedIn(), false);
        userId = preferences.getLong(Constants.getUserId(), -1);
    }
}
