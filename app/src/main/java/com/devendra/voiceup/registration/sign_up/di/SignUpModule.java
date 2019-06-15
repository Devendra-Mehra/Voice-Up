package com.devendra.voiceup.registration.sign_up.di;


import androidx.lifecycle.MutableLiveData;

import com.devendra.voiceup.database.AppDatabase;
import com.devendra.voiceup.registration.sign_up.model.SignUpModel;
import com.devendra.voiceup.registration.sign_up.view_model.SignUpViewModelFactory;
import com.devendra.voiceup.utils.Preferences;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.Disposable;

/**
 * Created by Devendra Mehra on 6/10/2019.
 */

@Module
public class SignUpModule {


    @Provides
    SignUpViewModelFactory providesSignUpViewModel(SignUpModel signUpModel) {
        return new SignUpViewModelFactory(signUpModel);
    }

    @Provides
    SignUpModel providesSignUpModel(AppDatabase appDatabase, Preferences preferences,
                                    Disposable disposable) {
        return new SignUpModel(new MutableLiveData<>(), appDatabase, preferences,disposable);
    }
}
