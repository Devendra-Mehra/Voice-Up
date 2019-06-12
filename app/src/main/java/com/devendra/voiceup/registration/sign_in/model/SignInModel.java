package com.devendra.voiceup.registration.sign_in.model;

import android.util.Patterns;

import androidx.lifecycle.MutableLiveData;

import com.devendra.voiceup.database.AppDatabase;
import com.devendra.voiceup.utils.FieldType;
import com.devendra.voiceup.utils.custom_exception.FieldException;
import com.devendra.voiceup.utils.out_come.Failure;
import com.devendra.voiceup.utils.out_come.OutCome;
import com.devendra.voiceup.utils.out_come.Progress;

import javax.inject.Inject;

/**
 * Created by Devendra Mehra on 6/12/2019.
 */
public class SignInModel {

    private MutableLiveData<OutCome> outComeMutableLiveData;
    private AppDatabase appDatabase;


    @Inject
    public SignInModel(MutableLiveData<OutCome> outComeMutableLiveData,
                       AppDatabase appDatabase) {
        this.outComeMutableLiveData = outComeMutableLiveData;
        this.appDatabase = appDatabase;
    }

    public void validate(String emailAddress, String password) {

        outComeMutableLiveData.setValue(new Progress(true));
        if (emailAddress.isEmpty()) {
            outComeMutableLiveData.setValue(new Progress(false));
            outComeMutableLiveData.setValue(new Failure(
                    new FieldException("Email cannot be empty", FieldType.EMAIL)));

        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()) {
            outComeMutableLiveData.setValue(new Progress(false));
            outComeMutableLiveData.setValue(new Failure(
                    new FieldException("Invalid email address", FieldType.EMAIL)));

        } else if (password.isEmpty()) {
            outComeMutableLiveData.setValue(new Progress(false));
            outComeMutableLiveData.setValue(new Failure(
                    new FieldException("password cannot be empty", FieldType.PASSWORD)));
        } else {

        }

    }

    public MutableLiveData<OutCome> getOutComeMutableLiveData() {
        return outComeMutableLiveData;
    }
}
