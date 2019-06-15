package com.devendra.voiceup.home.model;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.devendra.voiceup.database.AppDatabase;
import com.devendra.voiceup.database.JoinResult;
import com.devendra.voiceup.utils.FieldType;
import com.devendra.voiceup.utils.Preferences;
import com.devendra.voiceup.utils.custom_exception.FieldException;
import com.devendra.voiceup.utils.out_come.Failure;
import com.devendra.voiceup.utils.out_come.OutCome;
import com.devendra.voiceup.utils.out_come.Progress;
import com.devendra.voiceup.utils.out_come.Success;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Devendra Mehra on 6/12/2019.
 */
public class HomeModel {

    private Preferences preferences;
    private MutableLiveData<Boolean> logoutMutableLiveData;
    private MutableLiveData<OutCome> outComeMutableLiveData;
    private AppDatabase appDatabase;


    @Inject
    public HomeModel(Preferences preferences,
                     MutableLiveData<Boolean> logoutMutableLiveData,
                     MutableLiveData<OutCome> outComeMutableLiveData,
                     AppDatabase appDatabase) {
        this.preferences = preferences;
        this.logoutMutableLiveData = logoutMutableLiveData;
        this.outComeMutableLiveData = outComeMutableLiveData;
        this.appDatabase = appDatabase;
    }

    public void logout() {
        preferences.clearPrefs();
        logoutMutableLiveData.setValue(true);
    }

    public MutableLiveData<Boolean> getLogoutMutableLiveData() {
        return logoutMutableLiveData;
    }

    public MutableLiveData<OutCome> getOutComeMutableLiveData() {
        return outComeMutableLiveData;
    }


    public void getPost() {
        outComeMutableLiveData.setValue(new Progress(true));

        appDatabase.getPostDao()
                .getDisplayablePost()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<JoinResult>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<JoinResult> joinResults) {
                         outComeMutableLiveData.setValue(new Progress(false));
                        if (joinResults.size() > 0) {
                            outComeMutableLiveData.setValue(new Success<>(joinResults));
                        } else {
                            outComeMutableLiveData.setValue(new Failure(
                                    new FieldException("No Post found", FieldType.GENERAL)));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        outComeMutableLiveData.setValue(new Progress(false));
                        outComeMutableLiveData.setValue(new Failure(
                                new FieldException(e.getMessage(), FieldType.GENERAL)));
                        Log.d("Log10", "" + e.getMessage());
                    }
                });

    }
}
