package com.devendra.voiceup.registration.sign_up.model;

import android.util.Patterns;

import androidx.lifecycle.MutableLiveData;

import com.devendra.voiceup.database.users.Users;
import com.devendra.voiceup.uitls.FieldType;
import com.devendra.voiceup.uitls.custom_exception.FieldException;
import com.devendra.voiceup.uitls.out_come.Failure;
import com.devendra.voiceup.uitls.out_come.OutCome;
import com.devendra.voiceup.uitls.out_come.Progress;
import com.devendra.voiceup.uitls.out_come.Success;

import javax.inject.Inject;

/**
 * Created by Devendra Mehra on 6/10/2019.
 */
public class SignUpModel {

    private MutableLiveData<OutCome> outComeMutableLiveData;


    @Inject
    public SignUpModel(MutableLiveData<OutCome> outComeMutableLiveData) {
        this.outComeMutableLiveData = outComeMutableLiveData;
    }

    public void validate(Users users) {
        outComeMutableLiveData.setValue(new Progress(true));
        if (users.getUserName().isEmpty()) {
            outComeMutableLiveData.setValue(new Progress(false));
            outComeMutableLiveData.setValue(new Failure(
                    new FieldException("User name cannot be empty", FieldType.USERNAME)));
        } else if (users.getUserEmail().isEmpty()) {
            outComeMutableLiveData.setValue(new Progress(false));
            outComeMutableLiveData.setValue(new Failure(
                    new FieldException("Email cannot be empty", FieldType.EMAIL)));

        } else if (!Patterns.EMAIL_ADDRESS.matcher(users.getUserEmail()).matches()) {
            outComeMutableLiveData.setValue(new Progress(false));
            outComeMutableLiveData.setValue(new Failure(
                    new FieldException("Invalid email address", FieldType.EMAIL)));

        } else if (users.getUserPassword().isEmpty()) {
            outComeMutableLiveData.setValue(new Progress(false));
            outComeMutableLiveData.setValue(new Failure(
                    new FieldException("password cannot be empty", FieldType.PASSWORD)));
        } else {
            outComeMutableLiveData.setValue(new Success("Data success"));
            outComeMutableLiveData.setValue(new Progress(false));

        }

    }

    public MutableLiveData<OutCome> getOutComeMutableLiveData() {
        return outComeMutableLiveData;
    }

}
