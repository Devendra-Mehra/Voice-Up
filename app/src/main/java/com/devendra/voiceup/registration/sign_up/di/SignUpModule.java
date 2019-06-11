package com.devendra.voiceup.registration.sign_up.di;


import androidx.lifecycle.MutableLiveData;

import com.devendra.voiceup.database.AppDatabase;
import com.devendra.voiceup.registration.sign_up.model.SignUpModel;
import com.devendra.voiceup.registration.sign_up.view_model.SignUpViewModelFactory;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Devendra Mehra on 6/10/2019.
 */

@Module
public class SignUpModule {


    @Provides
    SignUpViewModelFactory providesSignInViewModel(SignUpModel signUpModel) {
        return new SignUpViewModelFactory(signUpModel);
    }

    @Provides
    SignUpModel providesSignInModel(AppDatabase appDatabase) {
        return new SignUpModel(new MutableLiveData<>(), appDatabase);
    }


}
