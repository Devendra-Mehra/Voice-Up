package com.devendra.voiceup.registration.sign_in.di;


import androidx.lifecycle.MutableLiveData;

import com.devendra.voiceup.database.AppDatabase;
import com.devendra.voiceup.registration.sign_in.model.SignInModel;
import com.devendra.voiceup.registration.sign_in.view_model.SignInViewModelFactory;
import com.devendra.voiceup.utils.Preferences;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.Disposable;

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
    SignInModel providesSignInModel(AppDatabase appDatabase, Preferences preferences,
                                    Disposable disposable) {
        return new SignInModel(new MutableLiveData<>(), appDatabase, preferences, disposable);
    }
}
