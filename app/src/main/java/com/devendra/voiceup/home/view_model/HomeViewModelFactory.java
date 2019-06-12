package com.devendra.voiceup.home.view_model;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.devendra.voiceup.home.model.HomeModel;

/**
 * Created by Devendra Mehra on 6/12/2019.
 */
public class HomeViewModelFactory implements ViewModelProvider.Factory {
    private HomeModel homeModel;

    public HomeViewModelFactory(HomeModel homeModel) {
        this.homeModel = homeModel;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {

        return (T) new HomeViewModel(homeModel);
    }
}
