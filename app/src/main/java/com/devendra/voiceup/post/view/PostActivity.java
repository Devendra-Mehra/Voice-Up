package com.devendra.voiceup.post.view;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;

import com.devendra.voiceup.R;
import com.devendra.voiceup.post.view_model.PostViewModel;
import com.devendra.voiceup.post.view_model.PostViewModelFactory;
import com.devendra.voiceup.utils.Constants;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class PostActivity extends AppCompatActivity implements View.OnClickListener {

    @Inject
    PostViewModelFactory postViewModelFactory;
    private PostViewModel postViewModel;
    public final int SELECT_FILE = 1;

    AppCompatImageView appCompatImageView;
    TextView tvReAddMedia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        appCompatImageView = findViewById(R.id.aciv_file);
        tvReAddMedia = findViewById(R.id.tv_re_addd_media);
        postViewModel = ViewModelProviders.of(this, postViewModelFactory)
                .get(PostViewModel.class);

        postViewModel.openFileBooleanMutableLiveData().observe(this, check -> {
            Log.d("Log13", "value" + check);
            if (check) {
                Log.d("Log13", "isper" + isPermissionAvailable());
                if (isPermissionAvailable()) {
                    openGallery();
                } else {
                    askPermission();
                }
            }
        });


    }


    public static Intent requiredIntent(Context context) {
        return new Intent(context, PostActivity.class);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == Constants.PERMISSIONS_WRITE_EXTERNAL_STORAGE) {
                appCompatImageView.setScaleType(ImageView.ScaleType.FIT_XY);
                appCompatImageView.setPadding(0, 0, 0, 0);
                appCompatImageView.setImageURI(Uri.parse(getRealPathFromURI(this, data.getData())));
                tvReAddMedia.setVisibility(View.VISIBLE);
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {

        if (requestCode == Constants.PERMISSIONS_WRITE_EXTERNAL_STORAGE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            } else {
                askPermission();
            }
        }

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.aciv_file || id == R.id.tv_re_addd_media) {
            postViewModel.openFile();
        }
    }

    public boolean isPermissionAvailable() {
        return ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, SELECT_FILE);
    }

    public void askPermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                Constants.PERMISSIONS_WRITE_EXTERNAL_STORAGE);

    }

    public String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] stringsMedia = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, stringsMedia, null, null, null);
            assert cursor != null;
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }
}
