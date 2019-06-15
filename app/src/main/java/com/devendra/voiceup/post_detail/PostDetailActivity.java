package com.devendra.voiceup.post_detail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.MediaController;

import com.devendra.voiceup.R;
import com.devendra.voiceup.databinding.ActivityPostDetailBinding;
import com.devendra.voiceup.utils.Constants;

public class PostDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityPostDetailBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_post_detail);
        String postFileName;
        int postType, dominantColor;
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            postFileName = null;
            postType = -1;
            dominantColor = -1;
        } else {
            postFileName = extras.getString(Constants.FILE_NAME);
            postType = extras.getInt(Constants.TYPE);
            dominantColor = extras.getInt(Constants.DOMINANT_COLOR);
        }

        if (dominantColor != -1) {
            binding.containerLayout.setBackgroundColor(dominantColor);
        }
        if (postType == Constants.PHOTO) {
            binding.tvSomethingWentWrong.setVisibility(View.GONE);
            binding.ivPostImage.setVisibility(View.VISIBLE);
            binding.vvPostVideo.setVisibility(View.GONE);
            binding.ivPostImage.setImageURI(Uri.parse(Constants.FILE_LOCATION + postFileName));

        } else if (postType == Constants.VIDEO) {
            binding.tvSomethingWentWrong.setVisibility(View.GONE);
            binding.ivPostImage.setVisibility(View.GONE);
            binding.vvPostVideo.setVisibility(View.VISIBLE);

            MediaController mediaController = new MediaController(this);
            mediaController.setAnchorView(binding.vvPostVideo);
            binding.vvPostVideo.setMediaController(mediaController);
            binding.vvPostVideo.setVideoURI(Uri.parse(Constants.FILE_LOCATION + postFileName));
            binding.vvPostVideo.start();
            binding.vvPostVideo.requestFocus();

        } else {

            binding.tvSomethingWentWrong.setVisibility(View.VISIBLE);
            binding.vvPostVideo.setVisibility(View.GONE);
            binding.ivPostImage.setVisibility(View.GONE);
        }

    }

    public static Intent requiredIntent(Context context, int postType, String fileName, int dominantColor) {
        return new Intent(context, PostDetailActivity.class)
                .putExtra(Constants.TYPE, postType)
                .putExtra(Constants.DOMINANT_COLOR, dominantColor)
                .putExtra(Constants.FILE_NAME, fileName);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return true;
    }
}
