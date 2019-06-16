package com.devendra.voiceup.home.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.devendra.voiceup.R;
import com.devendra.voiceup.databinding.ActivityHomeBinding;
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

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    @Inject
    HomeAdapter homeAdapter;
    @Inject
    HomeViewModelFactory homeViewModelFactory;
    private HomeViewModel homeViewModel;
    private ActivityHomeBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);

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
                success(outCome);
                break;
            case LOADING:
                progress(outCome);
                break;
            default:
                failure(outCome);
        }
    }

    private void success(OutCome outCome) {
        Success<List<DisplayablePost>> success = (Success<List<DisplayablePost>>) outCome;
        homeAdapter.addPosts(success.getData());
        binding.recyclerViewPost.setVisibility(View.VISIBLE);
        binding.tvNoPost.setVisibility(View.GONE);
        binding.acivNoPost.setVisibility(View.GONE);
    }

    private void progress(OutCome outCome) {
        Progress progress = (Progress) outCome;
        binding.progressBarLoading.setVisibility(progress.isLoading()
                ? View.VISIBLE : View.GONE);
    }

    private void failure(OutCome outCome) {
        Failure failure = (Failure) outCome;
        if (failure.getThrowable() instanceof FieldException) {
            binding.recyclerViewPost.setVisibility(View.GONE);
            binding.tvNoPost.setVisibility(View.VISIBLE);
            binding.acivNoPost.setVisibility(View.VISIBLE);
        }
    }
}
