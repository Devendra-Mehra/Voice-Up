package com.devendra.voiceup.registration.sign_up.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.devendra.voiceup.R;
import com.devendra.voiceup.registration.sign_up.view_model.SignUpViewModel;
import com.devendra.voiceup.registration.sign_up.view_model.SignUpViewModelFactory;
import com.devendra.voiceup.uitls.custom_exception.EmptyFieldException;
import com.devendra.voiceup.uitls.out_come.Failure;
import com.devendra.voiceup.uitls.out_come.OutCome;
import com.devendra.voiceup.uitls.out_come.Progress;
import com.devendra.voiceup.uitls.out_come.Success;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etEmail, etUserName, etPassword;
    private ProgressBar progressBarLoading;

    @Inject
    SignUpViewModelFactory signUpViewModelFactory;

    SignUpViewModel signUpViewModel;

    private enum ViewStatus {LOADING, SUCCESS, ERROR}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        initViews();
        signUpViewModel = ViewModelProviders.of(this, signUpViewModelFactory)
                .get(SignUpViewModel.class);


        signUpViewModel.getOutComeMutableLiveData().observe(this, outCome -> {
            if (outCome instanceof Progress) {
                changeViewState(ViewStatus.LOADING, outCome);
            } else if (outCome instanceof Success) {
                changeViewState(ViewStatus.SUCCESS, outCome);
            } else {
                changeViewState(ViewStatus.ERROR, outCome);
            }
        });

    }

    private void initViews() {
        etEmail = findViewById(R.id.et_email);
        etUserName = findViewById(R.id.et_user_name);
        etPassword = findViewById(R.id.et_password);
        progressBarLoading = findViewById(R.id.progress_bar_loading);
    }

    private void changeViewState(ViewStatus viewStatus, OutCome outCome) {
        switch (viewStatus) {
            case SUCCESS:
                Success success = (Success) outCome;
                Toast.makeText(this, success.getSuccess(),
                        Toast.LENGTH_SHORT).show();
                break;
            case LOADING:
                Progress progress = (Progress) outCome;
                progressBarLoading.setVisibility(progress.isLoading()
                        ? View.VISIBLE : View.GONE);
                break;
            default:
                Failure failure = (Failure) outCome;
                if (failure.getException() instanceof EmptyFieldException) {
                    Log.d("Log10", "" + failure.getException().getMessage());
                }


        }
    }

    public static Intent requiredIntent(Context context) {
        return new Intent(context, SignUpActivity.class);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.fab_sign_up) {
            String userName = etUserName.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            signUpViewModel.validate(userName, email, password);
        }
    }

}
