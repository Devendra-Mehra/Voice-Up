package com.devendra.voiceup.post.view_model;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.devendra.voiceup.post.model.PostModel;
import com.devendra.voiceup.utils.CompressImage;


/**
 * Created by Devendra Mehra on 6/13/2019.
 */
public class PostViewModelFactory implements ViewModelProvider.Factory {

    private PostModel postModel;
    private CompressImage compressImage;

    public PostViewModelFactory(PostModel postModel, CompressImage compressImage) {
        this.postModel = postModel;
        this.compressImage = compressImage;

    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new PostViewModel(postModel, new MutableLiveData<>(),
                new MutableLiveData<>(), compressImage);
    }
}
