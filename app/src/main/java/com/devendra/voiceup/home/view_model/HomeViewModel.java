package com.devendra.voiceup.home.view_model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.devendra.voiceup.database.JoinResult;
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
                        createDisplayablePost(((Success<List<JoinResult>>) input)
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

    private List<DisplayablePost> createDisplayablePost(List<JoinResult> joinResults) {
        List<DisplayablePost> displayablePosts = new ArrayList<>();
        for (JoinResult joinResult : joinResults) {
            DisplayablePost displayablePost = new DisplayablePost();
            displayablePost.setPostTitle(joinResult.getPostTitle());
            displayablePost.setPostType(joinResult.getPostType());
            displayablePost.setUserName("By: " + joinResult.getUserName());
            displayablePost.setFileName(joinResult.getFileName());
            if (Constants.PHOTO == joinResult.getPostType()) {
                displayablePost.setDominantColor(
                        getDominantColor(
                                BitmapFactory.decodeFile(
                                        Constants.FILE_LOCATION +
                                                joinResult.getFileName())
                        ));
            } else {
                displayablePost.setBitmapThumbnail(getBitmap(Constants.FILE_LOCATION
                        + joinResult.getFileName()));
                displayablePost.setDominantColor(
                        getDominantColor(
                                getBitmap(Constants.FILE_LOCATION
                                        + joinResult.getFileName())));
            }
            displayablePosts.add(displayablePost);
        }
        return displayablePosts;
    }

    private int getDominantColor(Bitmap bitmap) {
        Bitmap newBitmap = Bitmap.createScaledBitmap(bitmap,
                1, 1, true);
        final int color = newBitmap.getPixel(0, 0);
        newBitmap.recycle();
        return color;
    }


    private Bitmap getBitmap(String filePath) {
        return ThumbnailUtils.createVideoThumbnail(filePath,
                MediaStore.Video.Thumbnails.FULL_SCREEN_KIND);
    }
}
