package com.devendra.voiceup.uitls.out_come;

/**
 * Created by Devendra Mehra on 6/10/2019.
 */
public class Progress implements OutCome {

    private boolean isLoading;

    public Progress(boolean isLoading) {
        this.isLoading = isLoading;
    }

    public boolean isLoading() {
        return isLoading;
    }
}
