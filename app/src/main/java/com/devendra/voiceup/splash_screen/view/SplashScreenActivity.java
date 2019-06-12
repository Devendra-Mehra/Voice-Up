package com.devendra.voiceup.splash_screen.view;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.devendra.voiceup.R;
import com.devendra.voiceup.home.view.HomeActivity;
import com.devendra.voiceup.registration.sign_in.view.SignInActivity;
import com.devendra.voiceup.splash_screen.view_model.SplashScreenViewModel;
import com.devendra.voiceup.splash_screen.view_model.SplashScreenViewModelFactory;

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
                startActivity(HomeActivity.requiredIntent(context));
            } else {
                startActivity(SignInActivity.requiredIntent(context));
            }
            finish();
        });


    }
}
