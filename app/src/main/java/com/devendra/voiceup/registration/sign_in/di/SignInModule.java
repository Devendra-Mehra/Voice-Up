package com.devendra.voiceup.registration.sign_in.di;


import androidx.lifecycle.MutableLiveData;

import com.devendra.voiceup.database.AppDatabase;
import com.devendra.voiceup.registration.sign_in.model.SignInModel;
import com.devendra.voiceup.registration.sign_in.view_model.SignInViewModelFactory;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Devendra Mehra on 6/12/2019.
 */

@Module
public class SignInModule {
    @Provides
    SignInViewModelFactory providesSignInViewModel(SignInModel signInModel) {
        return new SignInViewModelFactory(signInModel);
    }

    @Provides
    SignInModel providesSignInModel(AppDatabase appDatabase) {
        return new SignInModel(new MutableLiveData<>(), appDatabase);
    }
}
