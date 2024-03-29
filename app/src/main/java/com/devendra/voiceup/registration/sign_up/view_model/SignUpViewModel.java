package com.devendra.voiceup.registration.sign_up.view_model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.devendra.voiceup.database.user.User;
import com.devendra.voiceup.registration.sign_up.model.SignUpModel;
import com.devendra.voiceup.utils.out_come.OutCome;

import javax.inject.Inject;

/**
 * Created by Devendra Mehra on 6/10/2019.
 */
public class SignUpViewModel extends ViewModel {

    private SignUpModel signUpModel;

    private LiveData<OutCome> outComeLiveData;

    @Inject
    public SignUpViewModel(SignUpModel signUpModel) {
        this.signUpModel = signUpModel;
        outComeLiveData = signUpModel.getOutComeMutableLiveData();
    }


    public void validate(String userName, String emailAddress, String password) {
        User user = new User();
        user.setUserName(userName);
        user.setUserEmail(emailAddress);
        user.setUserPassword(password);
        signUpModel.validate(user);
    }

    public LiveData<OutCome> getOutComeMutableLiveData() {
        return outComeLiveData;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        signUpModel.clearSubscriptions();
    }
}
