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
        loggedIn = preferences.getBoolean(Constants.LOGGED_IN, false);
        userId = preferences.getLong(Constants.USER_ID, -1);
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
        preferences.edit().putBoolean(Constants.LOGGED_IN, loggedIn).apply();
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
        preferences.edit().putLong(Constants.USER_ID, userId).apply();
    }

    public void clearPrefs() {
        preferences.edit().clear().apply();
    }

}
