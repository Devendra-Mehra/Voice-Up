package com.devendra.voiceup.splash_screen.view;

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
    private SplashScreenViewModel splashScreenViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        splashScreenViewModel = ViewModelProviders.of(this, splashScreenViewModelFactory)
                .get(SplashScreenViewModel.class);
        setObserve();
    }


    private void setObserve() {
        splashScreenViewModel.getBooleanLiveData().observe(this, toShow -> {
            if (toShow) {
                startHomeActivity();
            } else {
                startSignInActivity();
            }
            finish();
        });
    }

    private void startHomeActivity() {
        startActivity(HomeActivity.requiredIntent(SplashScreenActivity.this));
    }

    private void startSignInActivity() {
        startActivity(SignInActivity.requiredIntent(SplashScreenActivity.this));
    }
}
