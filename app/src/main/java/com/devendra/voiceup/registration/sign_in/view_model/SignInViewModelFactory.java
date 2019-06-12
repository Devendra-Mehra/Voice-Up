package com.devendra.voiceup.registration.sign_in.view_model;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.devendra.voiceup.registration.sign_in.model.SignInModel;
import com.devendra.voiceup.registration.sign_up.model.SignUpModel;
import com.devendra.voiceup.registration.sign_up.view_model.SignUpViewModel;


/**
 * Created by Devendra Mehra on 6/12/2019.
 */
public class SignInViewModelFactory implements ViewModelProvider.Factory {

    private SignInModel signInModel;

    public SignInViewModelFactory(SignInModel signInModel) {
        this.signInModel = signInModel;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new SignInViewModel(signInModel);
    }
}
