package com.devendra.voiceup.post.view_model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.devendra.voiceup.post.model.PostModel;
import com.devendra.voiceup.utils.Constants;
import com.devendra.voiceup.utils.custom_exception.ImageException;
import com.devendra.voiceup.utils.out_come.Failure;
import com.devendra.voiceup.utils.out_come.OutCome;
import com.devendra.voiceup.utils.out_come.SuccessPost;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * Created by Devendra Mehra on 6/13/2019.
 */
public class PostViewModel extends ViewModel {

    private PostModel postModel;
    private MutableLiveData<Boolean> openFileBooleanMutableLiveData;
    private MutableLiveData<OutCome> validateFileOutCome;
    private LiveData<OutCome> validatePostOutCome;


    public PostViewModel(PostModel postModel,
                         MutableLiveData<Boolean> openFileBooleanMutableLiveData,
                         MutableLiveData<OutCome> validateFileOutCome) {
        this.postModel = postModel;
        this.openFileBooleanMutableLiveData = openFileBooleanMutableLiveData;
        this.validateFileOutCome = validateFileOutCome;
        validatePostOutCome = postModel.getValidatePostOutCome();
    }


    public void openFile() {
        openFileBooleanMutableLiveData.setValue(true);
    }

    public MutableLiveData<Boolean> openFileBooleanMutableLiveData() {
        return openFileBooleanMutableLiveData;
    }

    public void validatePost(String postTitle, Object imageName, Object videoName) {
        postModel.validatePost(postTitle, imageName, videoName);
    }

    public void copyFileToAnotherDirectory(String sourcePath, int fileType) {
        File sourceFile = new File(sourcePath);
        String imageName;
        if (fileType == Constants.PHOTO) {
            imageName = "/" + System.currentTimeMillis() + ".jpg";
        } else {
            imageName = "/" + System.currentTimeMillis() + ".mp4";
        }
        File dir = new File(Constants.FILE_LOCATION);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String destinationPath = dir.getPath() + imageName;
        File destinationFile = new File(destinationPath);
        try {
            FileUtils.copyFile(sourceFile, destinationFile);
            validateFileOutCome.setValue(new SuccessPost(imageName, fileType));
        } catch (IOException e) {
            e.printStackTrace();
            validateFileOutCome.setValue(new Failure(new ImageException(e.getMessage())));
        }
    }

    public MutableLiveData<OutCome> getValidateFileOutCome() {
        return validateFileOutCome;
    }

    public LiveData<OutCome> getValidatePostOutCome() {
        return validatePostOutCome;
    }
}

