package com.devendra.voiceup.home.di;

import androidx.lifecycle.MutableLiveData;

import com.devendra.voiceup.database.AppDatabase;
import com.devendra.voiceup.home.model.HomeModel;
import com.devendra.voiceup.home.view.HomeAdapter;
import com.devendra.voiceup.home.view_model.HomeViewModelFactory;
import com.devendra.voiceup.utils.Preferences;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Devendra Mehra on 6/12/2019.
 */
@Module
public class HomeModule {

    @Provides
    HomeViewModelFactory provideHomeViewModelFactory(HomeModel homeModel) {
        return new HomeViewModelFactory(homeModel);
    }

    @Provides
    HomeModel provideHomeModel(Preferences preferences, AppDatabase appDatabase) {
        return new HomeModel(preferences, new MutableLiveData<>(),
                new MutableLiveData<>(), appDatabase);
    }

    @Provides
    HomeAdapter provideHomeAdapter() {
        return new HomeAdapter(new ArrayList<>());
    }
}
