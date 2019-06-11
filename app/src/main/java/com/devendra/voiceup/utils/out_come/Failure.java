package com.devendra.voiceup.utils.out_come;

/**
 * Created by Devendra Mehra on 6/10/2019.
 */

public class Failure implements OutCome {
    private Throwable throwable;

    public Failure(Throwable throwable) {
        this.throwable = throwable;
    }

    public Throwable getThrowable() {
        return throwable;
    }
}
