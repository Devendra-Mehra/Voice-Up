package com.devendra.voiceup.post.view_model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.devendra.voiceup.post.model.PostModel;

/**
 * Created by Devendra Mehra on 6/13/2019.
 */
public class PostViewModel extends ViewModel {

    private PostModel postModel;
    private MutableLiveData<Boolean> openFileBooleanMutableLiveData;

    public PostViewModel(PostModel postModel, MutableLiveData<Boolean> openFileBooleanMutableLiveData) {
        this.postModel = postModel;
        this.openFileBooleanMutableLiveData = openFileBooleanMutableLiveData;
    }


    public void openFile() {
        openFileBooleanMutableLiveData.setValue(true);
    }

    public MutableLiveData<Boolean> openFileBooleanMutableLiveData() {
        return openFileBooleanMutableLiveData;
    }
}
