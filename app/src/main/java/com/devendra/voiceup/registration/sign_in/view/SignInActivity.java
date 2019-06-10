package com.devendra.voiceup.registration.sign_in.view;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.devendra.voiceup.R;
import com.devendra.voiceup.registration.sign_up.view.SignUpActivity;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tv_sign_up) {
            startActivity(SignUpActivity.requiredIntent(this));
        }
    }
}
