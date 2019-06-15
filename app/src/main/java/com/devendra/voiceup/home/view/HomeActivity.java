package com.devendra.voiceup.home.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.devendra.voiceup.R;
import com.devendra.voiceup.home.view_model.HomeViewModel;
import com.devendra.voiceup.home.view_model.HomeViewModelFactory;
import com.devendra.voiceup.post.view.PostActivity;
import com.devendra.voiceup.registration.sign_in.view.SignInActivity;
import com.devendra.voiceup.utils.ViewState;
import com.devendra.voiceup.utils.custom_exception.FieldException;
import com.devendra.voiceup.utils.out_come.Failure;
import com.devendra.voiceup.utils.out_come.OutCome;
import com.devendra.voiceup.utils.out_come.Progress;
import com.devendra.voiceup.utils.out_come.Success;

import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {


    @Inject
    HomeViewModelFactory homeViewModelFactory;
    private HomeViewModel homeViewModel;

    @Inject
    HomeAdapter homeAdapter;

    private RecyclerView recyclerViewPost;
    private TextView textViewNoPost;
    private ProgressBar progressBarLoading;
    private AppCompatImageView appCompatImageViewNoPost;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();
        homeViewModel = ViewModelProviders.of(this, homeViewModelFactory)
                .get(HomeViewModel.class);

        homeViewModel.getLogOutLiveData().observe(this, logout -> {
            if (logout) {
                startActivity(SignInActivity.requiredIntent(this));
                finish();
            }
        });

        homeViewModel.getOutComeLiveData().observe(this, outCome -> {
            if (outCome instanceof Progress) {
                changeViewState(ViewState.LOADING, outCome);
            } else if (outCome instanceof Success) {
                changeViewState(ViewState.SUCCESS, outCome);
            } else {
                changeViewState(ViewState.ERROR, outCome);
            }
        });

    }

    private void initView() {
        recyclerViewPost = findViewById(R.id.recycler_view_post);
        textViewNoPost = findViewById(R.id.tv_no_post);
        appCompatImageViewNoPost = findViewById(R.id.aciv_no_post);
        progressBarLoading = findViewById(R.id.progress_bar_loading);
        recyclerViewPost.setAdapter(homeAdapter);
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

    private void changeViewState(ViewState viewStatus, OutCome outCome) {
        switch (viewStatus) {
            case SUCCESS:

                Success<List<DisplayablePost>> success = (Success<List<DisplayablePost>>) outCome;
                homeAdapter.addPosts(success.getData());
                recyclerViewPost.setVisibility(View.VISIBLE);
                textViewNoPost.setVisibility(View.GONE);
                appCompatImageViewNoPost.setVisibility(View.GONE);
                break;
            case LOADING:
                Progress progress = (Progress) outCome;
                progressBarLoading.setVisibility(progress.isLoading()
                        ? View.VISIBLE : View.GONE);
                break;
            default:
                Failure failure = (Failure) outCome;
                if (failure.getThrowable() instanceof FieldException) {
                   /* FieldException fieldException = (FieldException) failure.getThrowable();
                    String errorMessage = fieldException.getMessage();
                    FieldType fieldType = fieldException.getFieldType();*/
                    recyclerViewPost.setVisibility(View.GONE);
                    textViewNoPost.setVisibility(View.VISIBLE);
                    appCompatImageViewNoPost.setVisibility(View.VISIBLE);

                }


        }
    }
}
