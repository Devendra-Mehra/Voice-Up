package com.devendra.voiceup.splash_screen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.devendra.voiceup.R;
import com.devendra.voiceup.utils.Preferences;

import javax.inject.Inject;

public class SplashScreenActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

    }
}
