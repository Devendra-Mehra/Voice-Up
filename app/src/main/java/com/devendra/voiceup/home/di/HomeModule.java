package com.devendra.voiceup.home.di;

import com.devendra.voiceup.home.model.HomeModel;
import com.devendra.voiceup.home.view_model.HomeViewModelFactory;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Devendra Mehra on 6/12/2019.
 */
@Module
public class HomeModule {

    @Provides
    HomeViewModelFactory providesHomeViewModellFactory(HomeModel homeModel) {
        return new HomeViewModelFactory(homeModel);
    }

    @Provides
    HomeModel providesHomeModel() {
        return new HomeModel();
    }
}
