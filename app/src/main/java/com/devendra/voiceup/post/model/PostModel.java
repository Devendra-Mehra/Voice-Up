package com.devendra.voiceup.post.model;

import androidx.lifecycle.MutableLiveData;

import com.devendra.voiceup.database.AppDatabase;
import com.devendra.voiceup.database.post.Post;
import com.devendra.voiceup.utils.CompressImage;
import com.devendra.voiceup.utils.Constants;
import com.devendra.voiceup.utils.FieldType;
import com.devendra.voiceup.utils.Preferences;
import com.devendra.voiceup.utils.custom_exception.FieldException;
import com.devendra.voiceup.utils.custom_exception.GeneralException;
import com.devendra.voiceup.utils.out_come.Failure;
import com.devendra.voiceup.utils.out_come.OutCome;
import com.devendra.voiceup.utils.out_come.Progress;
import com.devendra.voiceup.utils.out_come.Success;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Devendra Mehra on 6/13/2019.
 */
public class PostModel {

    private AppDatabase appDatabase;
    private Preferences preferences;
    private MutableLiveData<OutCome> validatePostOutCome;


    @Inject
    public PostModel(AppDatabase appDatabase, Preferences preferences,
                     MutableLiveData<OutCome> validatePostOutCome) {
        this.appDatabase = appDatabase;
        this.preferences = preferences;
        this.validatePostOutCome = validatePostOutCome;
    }


    public void validatePost(String postTitle, Object imageName, Object videoName) {

        validatePostOutCome.setValue(new Progress(true));
        if (postTitle.isEmpty()) {
            validatePostOutCome.setValue(new Progress(false));
            validatePostOutCome.setValue(new Failure(new FieldException("Title is mandatory",
                    FieldType.TITLE)));
        } else if (imageName == null && videoName == null) {
            validatePostOutCome.setValue(new Progress(false));
            validatePostOutCome.setValue(new Failure(new
                    GeneralException("You have not provided video or photo to the post",
                    FieldType.GENERAL)));
        } else {
            String fileName;
            int fileType;
            if (imageName != null) {
                fileType = Constants.PHOTO;
                fileName = imageName.toString();
            } else {
                fileType = Constants.VIDEO;
                fileName = videoName.toString();
            }
            insertPost(postTitle, fileName, fileType);
        }
    }


    public MutableLiveData<OutCome> getValidatePostOutCome() {
        return validatePostOutCome;
    }


    private void insertPost(String postTitle, String fileName, int postType) {
        Post post = new Post();
        post.setPostTitle(postTitle);
        post.setFileName(fileName);
        post.setPostType(postType);
        post.setUserId((int) preferences.getUserId());

        Completable.fromAction(() -> appDatabase
                .getPostDao()
                .insertPost(post))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        validatePostOutCome.setValue(new Progress(false));
                        validatePostOutCome.setValue(new Success<>("Post inserted successfully"));
                    }

                    @Override
                    public void onError(Throwable e) {
                        validatePostOutCome.setValue(new Progress(false));
                        validatePostOutCome.setValue(new Failure(new GeneralException(e.getMessage(),
                                FieldType.GENERAL)));
                    }
                });
    }


}
