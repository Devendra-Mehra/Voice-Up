package com.devendra.voiceup.post.view_model;

import android.os.Environment;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.devendra.voiceup.post.model.PostModel;
import com.devendra.voiceup.utils.Constants;
import com.devendra.voiceup.utils.custom_exception.ImageException;
import com.devendra.voiceup.utils.out_come.Failure;
import com.devendra.voiceup.utils.out_come.OutCome;
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
    private MutableLiveData<OutCome> outComeMutableLiveData;


    public PostViewModel(PostModel postModel,
                         MutableLiveData<Boolean> openFileBooleanMutableLiveData,
                         MutableLiveData<OutCome> outComeMutableLiveData) {
        this.postModel = postModel;
        this.openFileBooleanMutableLiveData = openFileBooleanMutableLiveData;
        this.outComeMutableLiveData = outComeMutableLiveData;
    }


    public void openFile() {
        openFileBooleanMutableLiveData.setValue(true);
    }

    public MutableLiveData<Boolean> openFileBooleanMutableLiveData() {
        return openFileBooleanMutableLiveData;
    }

    public void copyFileToAnotherDirectory(String sourcePath) {
        File sourceFile = new File(sourcePath);
        String imageName = System.currentTimeMillis() + ".jpg";
        File dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                + Constants.FILE_LOCATION);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String destinationPath = dir.getPath() + "/" + imageName;
        File destinationFile = new File(destinationPath);
        try {
            FileUtils.copyFile(sourceFile, destinationFile);
            outComeMutableLiveData.setValue(new Success<>(destinationPath));
        } catch (IOException e) {
            e.printStackTrace();
            outComeMutableLiveData.setValue(new Failure(new ImageException(e.getMessage())));
        }
    }

    public MutableLiveData<OutCome> getOutComeMutableLiveData() {
        return outComeMutableLiveData;
    }
}

