package com.devendra.voiceup.app.di;

/**
 * Created by Devendra Mehra on 5/6/2019.
 */

import com.devendra.voiceup.registration.sign_up.di.SignUpModule;
import com.devendra.voiceup.registration.sign_up.view.SignUpActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = SignUpModule.class)
    abstract SignUpActivity contributeSignUpActivityInjector();

}
