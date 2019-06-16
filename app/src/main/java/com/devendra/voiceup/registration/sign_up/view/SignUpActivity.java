package com.devendra.voiceup.registration.sign_up.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.devendra.voiceup.R;
import com.devendra.voiceup.databinding.ActivitySignUpBinding;
import com.devendra.voiceup.home.view.HomeActivity;
import com.devendra.voiceup.registration.sign_up.view_model.SignUpViewModel;
import com.devendra.voiceup.registration.sign_up.view_model.SignUpViewModelFactory;
import com.devendra.voiceup.utils.FieldType;
import com.devendra.voiceup.utils.ViewState;
import com.devendra.voiceup.utils.custom_exception.FieldException;
import com.devendra.voiceup.utils.out_come.Failure;
import com.devendra.voiceup.utils.out_come.OutCome;
import com.devendra.voiceup.utils.out_come.Progress;
import com.devendra.voiceup.utils.out_come.Success;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    @Inject
    SignUpViewModelFactory signUpViewModelFactory;
    private SignUpViewModel signUpViewModel;
    private ActivitySignUpBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up);

        signUpViewModel = ViewModelProviders.of(this, signUpViewModelFactory)
                .get(SignUpViewModel.class);
        setObserve();

    }

    private void setObserve() {

        signUpViewModel.getOutComeMutableLiveData().observe(this, outCome -> {
            if (outCome instanceof Progress) {
                changeViewState(ViewState.LOADING, outCome);
            } else if (outCome instanceof Success) {
                changeViewState(ViewState.SUCCESS, outCome);
            } else {
                changeViewState(ViewState.ERROR, outCome);
            }
        });
    }

    private void changeViewState(ViewState viewStatus, OutCome outCome) {
        switch (viewStatus) {
            case SUCCESS:
                success(outCome);
                break;
            case LOADING:
                progress(outCome);
                break;
            default:
                failure(outCome);
        }
    }

    public static Intent requiredIntent(Context context) {
        return new Intent(context, SignUpActivity.class);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.fab_sign_up) {
            extractData();
        } else if (id == R.id.aciv_back) {
            finish();
        }
    }

    private void extractData() {
        binding.etUserName.setError(null);
        binding.etEmail.setError(null);
        binding.etPassword.setError(null);
        signUpViewModel.validate(binding.etUserName.getText().toString().trim(),
                binding.etEmail.getText().toString().trim(),
                binding.etPassword.getText().toString().trim());

    }

    private void success(OutCome outCome) {
        Success<String> success = (Success) outCome;
        Toast.makeText(this, success.getData(),
                Toast.LENGTH_SHORT).show();
        startActivity(HomeActivity.requiredIntent(this));
        finish();
    }

    private void progress(OutCome outCome) {
        Progress progress = (Progress) outCome;
        binding.progressBarLoading.setVisibility(progress.isLoading()
                ? View.VISIBLE : View.GONE);
    }

    private void failure(OutCome outCome) {
        Failure failure = (Failure) outCome;
        if (failure.getThrowable() instanceof FieldException) {
            FieldException fieldException = (FieldException) failure.getThrowable();
            String errorMessage = fieldException.getMessage();
            FieldType fieldType = fieldException.getFieldType();
            switch (fieldType) {
                case EMAIL:
                    binding.etEmail.setError(errorMessage);
                    break;
                case USERNAME:
                    binding.etUserName.setError(errorMessage);
                    break;
                case PASSWORD:
                    binding.etPassword.setError(errorMessage);
                    break;
                case GENERAL:
                    Toast.makeText(this, errorMessage,
                            Toast.LENGTH_LONG).show();
                    break;
            }
        } else {
            Toast.makeText(this, failure.getThrowable().getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }
}
