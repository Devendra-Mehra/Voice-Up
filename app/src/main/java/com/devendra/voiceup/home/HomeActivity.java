package com.devendra.voiceup.home;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.devendra.voiceup.R;
import com.devendra.voiceup.utils.Preferences;

import javax.inject.Inject;

public class HomeActivity extends AppCompatActivity {

    @Inject
    Preferences preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Log.d("lOG10", "" + preferences.isLoggedIn());
        preferences.setLoggedIn(true);
        Log.d("lOG10", "" + preferences.isLoggedIn());
    }


}
