package com.devendra.voiceup.registration.sign_up.view_model;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.devendra.voiceup.registration.sign_up.model.SignUpModel;


/**
 * Created by Devendra Mehra on 6/10/2019.
 */
public class SignUpViewModelFactory implements ViewModelProvider.Factory {

    private SignUpModel signUpModel;

    public SignUpViewModelFactory(SignUpModel signUpModel) {
        this.signUpModel = signUpModel;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new SignUpViewModel(signUpModel);
    }
}
