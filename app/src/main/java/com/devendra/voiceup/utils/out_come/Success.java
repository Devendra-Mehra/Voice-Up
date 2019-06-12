package com.devendra.voiceup.utils.out_come;

/**
 * Created by Devendra Mehra on 6/10/2019.
 */
public class Success<T> implements OutCome {
    private T data;

    public Success(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
