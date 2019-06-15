package com.devendra.voiceup.home.view_model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.devendra.voiceup.database.post_and_user.PostAndUser;
import com.devendra.voiceup.home.model.HomeModel;
import com.devendra.voiceup.home.view.DisplayablePost;
import com.devendra.voiceup.utils.Constants;
import com.devendra.voiceup.utils.out_come.OutCome;
import com.devendra.voiceup.utils.out_come.Success;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Devendra Mehra on 6/12/2019.
 */
public class HomeViewModel extends ViewModel {

    private HomeModel homeModel;
    private LiveData<Boolean> logOutLiveData;
    private LiveData<OutCome> outComeLiveData;


    public HomeViewModel(HomeModel homeModel) {
        this.homeModel = homeModel;
        logOutLiveData = homeModel.getLogoutMutableLiveData();
        outComeLiveData = Transformations.map(homeModel.getOutComeMutableLiveData(), input -> {
            if (input instanceof Success) {
                return new Success<>(
                        createDisplayablePost(((Success<List<PostAndUser>>) input)
                                .getData()));
            }
            return input;
        });

    }

    public LiveData<Boolean> getLogOutLiveData() {
        return logOutLiveData;
    }

    public void getPost() {
        homeModel.getPost();
    }

    public void logout() {
        homeModel.logout();
    }

    public LiveData<OutCome> getOutComeLiveData() {
        return outComeLiveData;
    }

    private List<DisplayablePost> createDisplayablePost(List<PostAndUser> postAndUsers) {
        List<DisplayablePost> displayablePosts = new ArrayList<>();
        for (PostAndUser postAndUser : postAndUsers) {
            displayablePosts.add(new DisplayablePost(postAndUser));
        }
        return displayablePosts;
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        homeModel.clearSubscriptions();
    }
}
