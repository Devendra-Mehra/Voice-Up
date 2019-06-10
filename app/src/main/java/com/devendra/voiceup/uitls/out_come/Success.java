package com.devendra.voiceup.uitls.out_come;

/**
 * Created by Devendra Mehra on 6/10/2019.
 */
public class Success implements OutCome {
    private String success;

    public Success(String success) {
        this.success = success;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }
}
