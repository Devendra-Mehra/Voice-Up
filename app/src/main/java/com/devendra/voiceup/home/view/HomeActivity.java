package com.devendra.voiceup.home.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.devendra.voiceup.R;
import com.devendra.voiceup.home.view_model.HomeViewModel;
import com.devendra.voiceup.home.view_model.HomeViewModelFactory;
import com.devendra.voiceup.post.PostActivity;
import com.devendra.voiceup.registration.sign_in.view.SignInActivity;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {


    @Inject
    HomeViewModelFactory homeViewModelFactory;
    private HomeViewModel homeViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        homeViewModel = ViewModelProviders.of(this, homeViewModelFactory)
                .get(HomeViewModel.class);

        homeViewModel.getLogOutLiveData().observe(this, logout -> {
            if (logout) {
                startActivity(SignInActivity.requiredIntent(this));
                finish();
            }
        });

    }

    public static Intent requiredIntent(Context context) {
        return new Intent(context, HomeActivity.class);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.fab_add_post) {
            startActivity(PostActivity.requiredIntent(this));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            homeViewModel.logout();
        }
        return super.onOptionsItemSelected(item);
    }
}
