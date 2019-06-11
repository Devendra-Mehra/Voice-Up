package com.devendra.voiceup.registration.sign_up.model;

import android.util.Patterns;

import androidx.lifecycle.MutableLiveData;

import com.devendra.voiceup.database.users.Users;
import com.devendra.voiceup.uitls.custom_exception.EmptyFieldException;
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
                    new EmptyFieldException("User name filed can not be empty")));
        } else if (users.getUserEmail().isEmpty()) {
            outComeMutableLiveData.setValue(new Progress(false));
            outComeMutableLiveData.setValue(new Failure(
                    new EmptyFieldException("Email address filed can not be empty")));

        } else if (!Patterns.EMAIL_ADDRESS.matcher(users.getUserEmail()).matches()) {
            outComeMutableLiveData.setValue(new Progress(false));
            outComeMutableLiveData.setValue(new Failure(
                    new EmptyFieldException("Invalid email address")));

        } else if (users.getUserPassword().isEmpty()) {
            outComeMutableLiveData.setValue(new Progress(false));
            outComeMutableLiveData.setValue(new Failure(
                    new EmptyFieldException("Email address filed can not be empty")));
        } else if (users.getUserPassword().length() < 5) {
            outComeMutableLiveData.setValue(new Progress(false));
            outComeMutableLiveData.setValue(new Failure(
                    new EmptyFieldException("Password filed should be great than 5 character")));
        } else {
            outComeMutableLiveData.setValue(new Success("Data success"));
            outComeMutableLiveData.setValue(new Progress(false));

        }

    }

    public MutableLiveData<OutCome> getOutComeMutableLiveData() {
        return outComeMutableLiveData;
    }

    private void setOutComeMutableLiveData(boolean loading, String message) {

    }


}
