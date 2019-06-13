package com.devendra.voiceup.post.model;

import com.devendra.voiceup.database.AppDatabase;
import com.devendra.voiceup.utils.Preferences;

import javax.inject.Inject;

/**
 * Created by Devendra Mehra on 6/13/2019.
 */
public class PostModel {

    private AppDatabase appDatabase;
    private Preferences preferences;

    @Inject
    public PostModel(AppDatabase appDatabase, Preferences preferences) {
        this.appDatabase = appDatabase;
        this.preferences = preferences;
    }
}
