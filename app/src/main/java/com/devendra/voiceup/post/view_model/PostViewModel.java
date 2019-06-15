package com.devendra.voiceup.post.view_model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.devendra.voiceup.post.model.PostModel;
import com.devendra.voiceup.utils.CompressImage;
import com.devendra.voiceup.utils.Constants;
import com.devendra.voiceup.utils.custom_exception.ImageException;
import com.devendra.voiceup.utils.out_come.Failure;
import com.devendra.voiceup.utils.out_come.OutCome;
import com.devendra.voiceup.utils.PostValidateSuccessResult;
import com.devendra.voiceup.utils.out_come.Success;

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
    private CompressImage compressImage;


    public PostViewModel(PostModel postModel,
                         MutableLiveData<Boolean> openFileBooleanMutableLiveData,
                         MutableLiveData<OutCome> validateFileOutCome,
                         CompressImage compressImage) {
        this.postModel = postModel;
        this.openFileBooleanMutableLiveData = openFileBooleanMutableLiveData;
        this.validateFileOutCome = validateFileOutCome;
        this.compressImage = compressImage;
        validatePostOutCome = postModel.getValidatePostOutCome();

    }


    public void openFile() {
        openFileBooleanMutableLiveData.setValue(true);
    }

    public MutableLiveData<Boolean> openFileBooleanMutableLiveData() {
        return openFileBooleanMutableLiveData;
    }

    public void validatePost(String postTitle, String imageName, String videoName) {
        postModel.validatePost(postTitle, imageName, videoName);
    }


    public void copyFileToAnotherDirectory(String sourcePath, int fileType) {
        File sourceFile = new File(sourcePath);
        File dir = new File(Constants.FILE_LOCATION);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        if (fileType == Constants.PHOTO) {
            compressImage.compressImage(sourcePath);
            validateFileOutCome.setValue(new Success<>(new
                    PostValidateSuccessResult(compressImage.getCompressedImageName(), fileType)));
        } else {
            String imageName = "/" + System.currentTimeMillis() + ".mp4";
            String destinationPath = dir.getPath() + imageName;
            File videoFile = new File(destinationPath);
            try {
                FileUtils.copyFile(sourceFile, videoFile);
                validateFileOutCome.setValue(new Success<>(new
                        PostValidateSuccessResult(imageName, fileType)));

            } catch (IOException e) {
                e.printStackTrace();
                validateFileOutCome.setValue(new Failure(new ImageException(e.getMessage())));
            }
        }
    }

    public MutableLiveData<OutCome> getValidateFileOutCome() {
        return validateFileOutCome;
    }

    public LiveData<OutCome> getValidatePostOutCome() {
        return validatePostOutCome;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        postModel.clearSubscriptions();
    }
}

