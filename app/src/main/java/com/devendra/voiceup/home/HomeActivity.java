package com.devendra.voiceup.home;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.devendra.voiceup.R;
import com.devendra.voiceup.uitls.Preferences;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

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
