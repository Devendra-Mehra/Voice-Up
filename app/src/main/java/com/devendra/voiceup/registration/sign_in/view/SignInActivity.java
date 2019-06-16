package com.devendra.voiceup.registration.sign_in.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.devendra.voiceup.R;
import com.devendra.voiceup.databinding.ActivitySignInBinding;
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


    @Inject
    SignInViewModelFactory signInViewModelFactory;
    private SignInViewModel signInViewModel;
    private ActivitySignInBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_in);
        signInViewModel = ViewModelProviders.of(this, signInViewModelFactory)
                .get(SignInViewModel.class);
        setSpannable();
        setObserve();
    }

    private void setSpannable() {
        String textData = "Don't have an account click here to signUp";
        Spannable spannable = new SpannableString(textData);

        int startPosition = 22;
        int endPosition = 33;
        ClickableSpan onClickSpan = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                startActivity(SignUpActivity.requiredIntent(textView.getContext()));

            }
        };
        spannable.setSpan(onClickSpan, startPosition, endPosition,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        spannable.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this,
                R.color.black)),
                startPosition, endPosition,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        binding.tvSignUp.setText(spannable);
        binding.tvSignUp.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void setObserve() {
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

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.fab_login) {
            extractData();
        }
    }

    private void extractData() {
        binding.etEmail.setError(null);
        binding.etPassword.setError(null);
        signInViewModel.validate(binding.etEmail.getText().toString().trim()
                , binding.etPassword.getText().toString().trim());
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
        return new Intent(context, SignInActivity.class);
    }


    private void success(OutCome outCome) {
        Success<String> success = (Success) outCome;
        Toast.makeText(this, success.getData(),
                Toast.LENGTH_LONG).show();
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
