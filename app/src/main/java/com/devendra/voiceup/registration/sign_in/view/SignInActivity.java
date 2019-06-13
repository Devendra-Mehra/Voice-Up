package com.devendra.voiceup.registration.sign_in.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.devendra.voiceup.R;
import com.devendra.voiceup.home.view.HomeActivity;
import com.devendra.voiceup.registration.sign_in.view_model.SignInViewModel;
import com.devendra.voiceup.registration.sign_in.view_model.SignInViewModelFactory;
import com.devendra.voiceup.registration.sign_up.view.SignUpActivity;
import com.devendra.voiceup.utils.FieldType;
import com.devendra.voiceup.utils.ViewState;
import com.devendra.voiceup.utils.custom_exception.FieldException;
import com.devendra.voiceup.utils.out_come.Failure;
import com.devendra.voiceup.utils.out_come.OutCome;
import com.devendra.voiceup.utils.out_come.Progress;
import com.devendra.voiceup.utils.out_come.Success;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {


    private EditText etEmailAddress, etPassword;
    private ProgressBar progressBarLoading;


    @Inject
    SignInViewModelFactory signInViewModelFactory;

    SignInViewModel signInViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        initView();

        signInViewModel = ViewModelProviders.of(this, signInViewModelFactory)
                .get(SignInViewModel.class);

        signInViewModel.getOutComeMutableLiveData().observe(this, outCome -> {
            if (outCome instanceof Progress) {
                changeViewState(ViewState.LOADING, outCome);
            } else if (outCome instanceof Success) {
                changeViewState(ViewState.SUCCESS, outCome);
            } else {
                changeViewState(ViewState.ERROR, outCome);
            }
        });


    }

    private void initView() {
        etPassword = findViewById(R.id.et_password);
        etEmailAddress = findViewById(R.id.et_email);
        progressBarLoading = findViewById(R.id.progress_bar_loading);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tv_sign_up) {
            startActivity(SignUpActivity.requiredIntent(this));
        } else if (id == R.id.fab_login) {
            etEmailAddress.setError(null);
            etPassword.setError(null);
            String emailAddress = etEmailAddress.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            signInViewModel.validate(emailAddress, password);
        }
    }


    private void changeViewState(ViewState viewStatus, OutCome outCome) {
        switch (viewStatus) {
            case SUCCESS:
                Success<String> success = (Success) outCome;
                Toast.makeText(this, success.getData(),
                        Toast.LENGTH_LONG).show();
                startActivity(HomeActivity.requiredIntent(this));
                finish();
                break;
            case LOADING:
                Progress progress = (Progress) outCome;
                progressBarLoading.setVisibility(progress.isLoading()
                        ? View.VISIBLE : View.GONE);
                break;
            default:
                Failure failure = (Failure) outCome;
                if (failure.getThrowable() instanceof FieldException) {
                    FieldException fieldException = (FieldException) failure.getThrowable();
                    String errorMessage = fieldException.getMessage();
                    FieldType fieldType = fieldException.getFieldType();
                    switch (fieldType) {
                        case EMAIL:
                            etEmailAddress.setError(errorMessage);
                            break;
                        case PASSWORD:
                            etPassword.setError(errorMessage);
                            break;
                        case GENERAL:
                            Toast.makeText(this, errorMessage,
                                    Toast.LENGTH_LONG).show();
                            break;
                    }
                }
        }
    }

    public static Intent requiredIntent(Context context) {
        return new Intent(context, SignInActivity.class);
    }

}
