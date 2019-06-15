package com.devendra.voiceup.post.di;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.devendra.voiceup.database.AppDatabase;
import com.devendra.voiceup.post.model.PostModel;
import com.devendra.voiceup.post.view_model.PostViewModelFactory;
import com.devendra.voiceup.utils.CompressImage;
import com.devendra.voiceup.utils.Preferences;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Devendra Mehra on 6/13/2019.
 */
@Module
public class PostModule {

    @Provides
    PostViewModelFactory providePostViewModelFactory(PostModel postModel, CompressImage compressImage) {
        return new PostViewModelFactory(postModel, compressImage);
    }

    @Provides
    PostModel providePostModel(AppDatabase appDatabase, Preferences preferences) {
        return new PostModel(appDatabase, preferences, new MutableLiveData<>());
    }


    @Provides
    CompressImage provideCompressImage(Context context) {
        return new CompressImage(context);
    }


}
