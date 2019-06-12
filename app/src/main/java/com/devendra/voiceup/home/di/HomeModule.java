package com.devendra.voiceup.home.di;

import androidx.lifecycle.MutableLiveData;

import com.devendra.voiceup.home.model.HomeModel;
import com.devendra.voiceup.home.view_model.HomeViewModelFactory;
import com.devendra.voiceup.utils.Preferences;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Devendra Mehra on 6/12/2019.
 */
@Module
public class HomeModule {

    @Provides
    HomeViewModelFactory providesHomeViewModelFactory(HomeModel homeModel) {
        return new HomeViewModelFactory(homeModel);
    }

    @Provides
    HomeModel providesHomeModel(Preferences preferences) {
        return new HomeModel(preferences, new MutableLiveData<>());
    }
}
