package com.devendra.voiceup.utils;

/**
 * Created by Devendra Mehra on 6/15/2019.
 */
public class PostValidateSuccessResult {
    private String fileName;
    private int imageType;


    public PostValidateSuccessResult(String fileName, int imageType) {
        this.fileName = fileName;
        this.imageType = imageType;
    }

    public String getFileName() {
        return fileName;
    }

    public int getImageType() {
        return imageType;
    }

}
