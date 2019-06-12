package com.devendra.voiceup.splash_screen.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.devendra.voiceup.R;
import com.devendra.voiceup.post.PostActivity;
import com.devendra.voiceup.registration.sign_in.view.SignInActivity;
import com.devendra.voiceup.registration.sign_in.view_model.SignInViewModel;
import com.devendra.voiceup.registration.sign_in.view_model.SignInViewModelFactory;
import com.devendra.voiceup.splash_screen.view_model.SplashScreenViewModel;
import com.devendra.voiceup.splash_screen.view_model.SplashScreenViewModelFactory;
import com.devendra.voiceup.utils.Preferences;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class SplashScreenActivity extends AppCompatActivity {

    @Inject
    SplashScreenViewModelFactory splashScreenViewModelFactory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Context context = this;
        SplashScreenViewModel splashScreenViewModel = ViewModelProviders.of(this, splashScreenViewModelFactory)
                .get(SplashScreenViewModel.class);
        splashScreenViewModel.getBooleanLiveData().observe(this, toShow -> {
            if (toShow) {
                startActivity(PostActivity.requiredIntent(context));
            } else {
                startActivity(SignInActivity.requiredIntent(context));
            }
            finish();
        });


    }
}
