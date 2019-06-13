package com.devendra.voiceup.registration.sign_up.model;

import android.util.Patterns;

import androidx.lifecycle.MutableLiveData;

import com.devendra.voiceup.database.AppDatabase;
import com.devendra.voiceup.database.user.User;
import com.devendra.voiceup.utils.FieldType;
import com.devendra.voiceup.utils.Preferences;
import com.devendra.voiceup.utils.custom_exception.FieldException;
import com.devendra.voiceup.utils.out_come.Failure;
import com.devendra.voiceup.utils.out_come.OutCome;
import com.devendra.voiceup.utils.out_come.Progress;
import com.devendra.voiceup.utils.out_come.Success;

import javax.inject.Inject;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Devendra Mehra on 6/10/2019.
 */
public class SignUpModel {

    private MutableLiveData<OutCome> outComeMutableLiveData;
    private AppDatabase appDatabase;
    private Preferences preferences;

    @Inject
    public SignUpModel(MutableLiveData<OutCome> outComeMutableLiveData,
                       AppDatabase appDatabase, Preferences preferences) {
        this.outComeMutableLiveData = outComeMutableLiveData;
        this.appDatabase = appDatabase;
        this.preferences = preferences;
    }

    public void validate(User user) {

        outComeMutableLiveData.setValue(new Progress(true));
        if (user.getUserName().isEmpty()) {
            outComeMutableLiveData.setValue(new Progress(false));
            outComeMutableLiveData.setValue(new Failure(
                    new FieldException("User name cannot be empty", FieldType.USERNAME)));
        } else if (user.getUserEmail().isEmpty()) {
            outComeMutableLiveData.setValue(new Progress(false));
            outComeMutableLiveData.setValue(new Failure(
                    new FieldException("Email cannot be empty", FieldType.EMAIL)));

        } else if (!Patterns.EMAIL_ADDRESS.matcher(user.getUserEmail()).matches()) {
            outComeMutableLiveData.setValue(new Progress(false));
            outComeMutableLiveData.setValue(new Failure(
                    new FieldException("Invalid email address", FieldType.EMAIL)));

        } else if (user.getUserPassword().isEmpty()) {
            outComeMutableLiveData.setValue(new Progress(false));
            outComeMutableLiveData.setValue(new Failure(
                    new FieldException("password cannot be empty", FieldType.PASSWORD)));
        } else {
            insertUser(user);
        }

    }

    public MutableLiveData<OutCome> getOutComeMutableLiveData() {
        return outComeMutableLiveData;
    }

    private void insertUser(User user) {

        appDatabase.getUserDao()
                .getUserByUserName(user.getUserName())
                .map(integer -> {
                    if (integer <= 0) {
                        preferences.setUserId(appDatabase.getUserDao().insertUser(user));
                        return true;
                    } else {
                        return false;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(Boolean isSuccessful) {
                        if (isSuccessful) {
                            outComeMutableLiveData.setValue(new Progress(false));
                            outComeMutableLiveData.setValue(new Success<>("User created successfully"));

                        } else {
                            outComeMutableLiveData.setValue(new Progress(false));
                            outComeMutableLiveData.setValue(new Failure(
                                    new FieldException("Username already exists", FieldType.USERNAME)));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        outComeMutableLiveData.setValue(new Progress(false));
                        outComeMutableLiveData.setValue(new Failure(
                                new FieldException(e.getMessage(), FieldType.GENERAL)));

                    }
                });

    }

}


