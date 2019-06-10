package com.devendra.voiceup.uitls.out_come;

/**
 * Created by Devendra Mehra on 6/10/2019.
 */

public class Failure implements OutCome {
    private Exception exception;

    public Failure(Exception exception) {
        this.exception = exception;
    }

    public Exception getException() {
        return exception;
    }
}
