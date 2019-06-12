package com.devendra.voiceup.post;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.devendra.voiceup.R;
import com.devendra.voiceup.registration.sign_in.view.SignInActivity;

public class PostActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
    }


    public static Intent requiredIntent(Context context) {
        return new Intent(context, PostActivity.class);
    }
}
