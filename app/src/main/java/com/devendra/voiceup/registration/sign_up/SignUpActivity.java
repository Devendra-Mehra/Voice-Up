package com.devendra.voiceup.registration.sign_up;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.devendra.voiceup.R;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }


    public static Intent requiredIntent(Context context) {
        return new Intent(context, SignUpActivity.class);
    }
}
