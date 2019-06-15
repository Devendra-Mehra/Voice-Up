package com.devendra.voiceup.registration.sign_in.model;

import android.util.Patterns;

import androidx.lifecycle.MutableLiveData;

import com.devendra.voiceup.database.AppDatabase;
import com.devendra.voiceup.database.user.User;
import com.devendra.voiceup.utils.FieldType;
import com.devendra.voiceup.utils.Preferences;
import com.devendra.voiceup.utils.custom_exception.FieldException;
import com.devendra.voiceup.utils.out_come.Failure;
import com.devendra.voiceup.utils.out_come.OutCome;
import com.devendra.voiceup.utils.out_come.Progress;
import com.devendra.voiceup.utils.out_come.Success;

import javax.inject.Inject;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Devendra Mehra on 6/12/2019.
 */
public class SignInModel {

    private MutableLiveData<OutCome> outComeMutableLiveData;
    private AppDatabase appDatabase;
    private Preferences preferences;
    private Disposable disposable;


    @Inject
    public SignInModel(MutableLiveData<OutCome> outComeMutableLiveData,
                       AppDatabase appDatabase, Preferences preferences,
                       Disposable disposable) {
        this.outComeMutableLiveData = outComeMutableLiveData;
        this.appDatabase = appDatabase;
        this.disposable = disposable;
        this.preferences = preferences;
    }

    public void validate(String emailAddress, String password) {

        outComeMutableLiveData.setValue(new Progress(true));
        if (emailAddress.isEmpty()) {
            outComeMutableLiveData.setValue(new Progress(false));
            outComeMutableLiveData.setValue(new Failure(
                    new FieldException("Email cannot be empty", FieldType.EMAIL)));

        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()) {
            outComeMutableLiveData.setValue(new Progress(false));
            outComeMutableLiveData.setValue(new Failure(
                    new FieldException("Invalid email address", FieldType.EMAIL)));

        } else if (password.isEmpty()) {
            outComeMutableLiveData.setValue(new Progress(false));
            outComeMutableLiveData.setValue(new Failure(
                    new FieldException("password cannot be empty", FieldType.PASSWORD)));
        } else {
            login(emailAddress, password);
        }

    }

    private void login(String emailAddress, String password) {
        appDatabase.getUserDao().getUserByEmail(emailAddress)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<User>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onSuccess(User user) {
                        if (user == null) {
                            outComeMutableLiveData.setValue(new Progress(false));
                            outComeMutableLiveData.setValue(new Failure(
                                    new FieldException("User does not exist", FieldType.GENERAL)));

                        } else {
                            if (user.getUserPassword().equals(password)) {
                                outComeMutableLiveData.setValue(new Progress(false));
                                outComeMutableLiveData.setValue(new Success<>("Login successfully"));
                                preferences.setLoggedIn(true);
                                preferences.setUserId(user.getUserId());
                            } else {
                                outComeMutableLiveData.setValue(new Progress(false));
                                outComeMutableLiveData.setValue(new Failure(
                                        new FieldException("Wrong password", FieldType.GENERAL)));

                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        outComeMutableLiveData.setValue(new Progress(false));
                        outComeMutableLiveData.setValue(new Failure(
                                new FieldException("Something went wrong", FieldType.GENERAL)));


                    }
                });

    }

    public void clearSubscriptions() {
        disposable.dispose();
    }

    public MutableLiveData<OutCome> getOutComeMutableLiveData() {
        return outComeMutableLiveData;
    }
}
