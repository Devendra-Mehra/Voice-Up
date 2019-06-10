package com.devendra.voiceup.registration.sign_up.model;

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
            outComeMutableLiveData.setValue(new Failure(
                    new EmptyFieldException("Empty field")));
            outComeMutableLiveData.setValue(new Progress(false));

        } else {
            outComeMutableLiveData.setValue(new Success("Data success"));
            outComeMutableLiveData.setValue(new Progress(false));

        }

    }

    public MutableLiveData<OutCome> getOutComeMutableLiveData() {
        return outComeMutableLiveData;
    }
}
