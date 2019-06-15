package com.devendra.voiceup.registration.sign_in.view_model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.devendra.voiceup.registration.sign_in.model.SignInModel;
import com.devendra.voiceup.utils.out_come.OutCome;

import javax.inject.Inject;

/**
 * Created by Devendra Mehra on 6/12/2019.
 */
public class SignInViewModel extends ViewModel {

    private SignInModel signInModel;

    private LiveData<OutCome> outComeLiveData;

    @Inject
    public SignInViewModel(SignInModel signInModel) {
        this.signInModel = signInModel;
        outComeLiveData = signInModel.getOutComeMutableLiveData();
    }


    public void validate(String emailAddress, String password) {
        signInModel.validate(emailAddress, password);
    }

    public LiveData<OutCome> getOutComeMutableLiveData() {
        return outComeLiveData;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        signInModel.clearSubscriptions();
    }
}
