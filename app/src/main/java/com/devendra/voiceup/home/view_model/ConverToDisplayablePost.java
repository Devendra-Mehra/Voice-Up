package com.devendra.voiceup.home.view_model;

import android.graphics.BitmapFactory;

import com.devendra.voiceup.database.post_and_user.PostAndUser;
import com.devendra.voiceup.home.view.DisplayablePost;
import com.devendra.voiceup.utils.BitmapHelper;
import com.devendra.voiceup.utils.Constants;

import static com.devendra.voiceup.utils.BitmapHelper.getBitmap;

/**
 * Created by Devendra Mehra on 6/16/2019.
 */
public class ConverToDisplayablePost {

    public static DisplayablePost getDisplayablePost(PostAndUser postAndUser) {
        DisplayablePost displayablePost = new DisplayablePost();
        displayablePost.setPostTitle(postAndUser.getPostTitle());
        displayablePost.setPostType(postAndUser.getPostType());
        displayablePost.setUserName("By: " + postAndUser.getUserName());
        displayablePost.setFileName(postAndUser.getFileName());
        if (Constants.PHOTO == postAndUser.getPostType()) {
            displayablePost.setDominantColor(
                    BitmapHelper.getDominantColor(
                            BitmapFactory.decodeFile(
                                    Constants.FILE_LOCATION +
                                            postAndUser.getFileName())
                    ));
        } else {
            displayablePost.setBitmapThumbnail(getBitmap(Constants.FILE_LOCATION
                    + postAndUser.getFileName()));
            displayablePost.setDominantColor(
                    BitmapHelper.getDominantColor(
                            getBitmap(Constants.FILE_LOCATION
                                    + postAndUser.getFileName())));
        }
        return displayablePost;
    }

}
