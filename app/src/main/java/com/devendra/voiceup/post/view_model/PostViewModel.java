package com.devendra.voiceup.post.view_model;

import androidx.lifecycle.ViewModel;

import com.devendra.voiceup.post.model.PostModel;

/**
 * Created by Devendra Mehra on 6/13/2019.
 */
public class PostViewModel extends ViewModel {

    private PostModel postModel;

    public PostViewModel(PostModel postModel) {
        this.postModel = postModel;
    }
}
