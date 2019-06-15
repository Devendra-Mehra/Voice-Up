package com.devendra.voiceup.home.view_model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.devendra.voiceup.home.model.HomeModel;
import com.devendra.voiceup.utils.out_come.OutCome;

/**
 * Created by Devendra Mehra on 6/12/2019.
 */
public class HomeViewModel extends ViewModel {

    private HomeModel homeModel;
    private LiveData<Boolean> logOutLiveData;
    private LiveData<OutCome> outComeLiveData;


    public HomeViewModel(HomeModel homeModel) {
        this.homeModel = homeModel;
        logOutLiveData = homeModel.getLogoutMutableLiveData();
        outComeLiveData = homeModel.getOutComeMutableLiveData();
        homeModel.getPost();
    }

    public LiveData<Boolean> getLogOutLiveData() {
        return logOutLiveData;
    }


    public void logout() {
        homeModel.logout();
    }

    public LiveData<OutCome> getOutComeLiveData() {
        return outComeLiveData;
    }
}
