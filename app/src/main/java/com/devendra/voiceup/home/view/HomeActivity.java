package com.devendra.voiceup.home.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.devendra.voiceup.post_detail.PostDetailActivity;
import com.devendra.voiceup.registration.sign_in.view.SignInActivity;
import com.devendra.voiceup.utils.Constants;
import com.devendra.voiceup.utils.ViewState;
import com.devendra.voiceup.utils.custom_exception.FieldException;
import com.devendra.voiceup.utils.out_come.Failure;
import com.devendra.voiceup.utils.out_come.OutCome;
import com.devendra.voiceup.utils.out_come.Progress;
import com.devendra.voiceup.utils.out_come.Success;

import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

import static com.devendra.voiceup.utils.Constants.PHOTO;
import static com.devendra.voiceup.utils.Constants.VIDEO;

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
        setObserve();
        homeAdapter.setListener((postFile, postType, dominantColor) ->
                startActivity(PostDetailActivity.requiredIntent(
                        HomeActivity.this, postType, postFile, dominantColor)));


        homeViewModel.getPost();
    }

    private void setObserve() {
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
        return new Intent(context, HomeActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.fab_add_post) {
            startActivityForResult(PostActivity.requiredIntent(this), Constants.HOME_RESULT);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.HOME_RESULT) {
            homeViewModel.getPost();
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
                Log.d("Log15", "test" + success.getData());
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
                    recyclerViewPost.setVisibility(View.GONE);
                    textViewNoPost.setVisibility(View.VISIBLE);
                    appCompatImageViewNoPost.setVisibility(View.VISIBLE);

                }
        }
    }


}
