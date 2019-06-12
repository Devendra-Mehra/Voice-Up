package com.devendra.voiceup.home.view_model;

import androidx.lifecycle.ViewModel;

import com.devendra.voiceup.home.model.HomeModel;

/**
 * Created by Devendra Mehra on 6/12/2019.
 */
public class HomeViewModel extends ViewModel {

    private HomeModel homeModel;

    public HomeViewModel(HomeModel homeModel) {
        this.homeModel = homeModel;
    }
}
