package com.devendra.voiceup.utils.out_come;

/**
 * Created by Devendra Mehra on 6/15/2019.
 */
public class SuccessPost implements OutCome {
    private String fileName;
    private int imageType;

    public SuccessPost(String fileName, int imageType) {
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
