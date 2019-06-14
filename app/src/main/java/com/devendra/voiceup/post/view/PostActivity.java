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
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;

import com.afollestad.materialdialogs.MaterialDialog;
import com.devendra.voiceup.R;
import com.devendra.voiceup.post.view_model.PostViewModel;
import com.devendra.voiceup.post.view_model.PostViewModelFactory;
import com.devendra.voiceup.utils.Constants;
import com.devendra.voiceup.utils.ViewState;
import com.devendra.voiceup.utils.custom_exception.ImageException;
import com.devendra.voiceup.utils.out_come.Failure;
import com.devendra.voiceup.utils.out_come.OutCome;
import com.devendra.voiceup.utils.out_come.Success;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

import static com.devendra.voiceup.utils.Constants.PHOTO;
import static com.devendra.voiceup.utils.Constants.VIDEO;

public class PostActivity extends AppCompatActivity implements View.OnClickListener {

    @Inject
    PostViewModelFactory postViewModelFactory;
    @Inject
    MaterialDialog.Builder materialDialog;
    private PostViewModel postViewModel;
    private AppCompatImageView appCompatImageView;
    private TextView tvReAddMedia;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        appCompatImageView = findViewById(R.id.aciv_file);
        tvReAddMedia = findViewById(R.id.tv_re_addd_media);
        postViewModel = ViewModelProviders.of(this, postViewModelFactory)
                .get(PostViewModel.class);

        observeEvents();

    }

    private void observeEvents() {
        postViewModel.openFileBooleanMutableLiveData().observe(this, openFilePicker -> {
            if (openFilePicker) {
                if (isPermissionAvailable()) {
                    askForFileType();
                } else {
                    askPermission();
                }
            }
        });

        postViewModel.getOutComeMutableLiveData().observe(this, outCome -> {
            if (outCome instanceof Success) {
                changeViewState(ViewState.SUCCESS, outCome);
            } else if (outCome instanceof Failure) {
                changeViewState(ViewState.ERROR, outCome);
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
                String path = getRealPathFromURI(this, data.getData());
                postViewModel.copyFileToAnotherDirectory(path);
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {

        if (requestCode == Constants.PERMISSIONS_WRITE_EXTERNAL_STORAGE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                askForFileType();
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
        } else if (id == R.id.btn_create_post) {
            String fileName = appCompatImageView.getTag().toString();
        }
    }

    public boolean isPermissionAvailable() {
        return ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
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

    private void changeViewState(ViewState viewStatus, OutCome outCome) {

        if (viewStatus == ViewState.SUCCESS) {
            Success<String> success = (Success<String>) outCome;
            appCompatImageView.setScaleType(ImageView.ScaleType.FIT_XY);
            appCompatImageView.setPadding(0, 0, 0, 0);
            appCompatImageView.setImageURI(Uri.parse(success.getData()));
            appCompatImageView.setTag(success.getData());
            tvReAddMedia.setVisibility(View.VISIBLE);
        } else {
            Failure failure = (Failure) outCome;
            if (failure.getThrowable() instanceof ImageException) {
                Toast.makeText(this, failure.getThrowable().getMessage()
                        , Toast.LENGTH_LONG).show();
            }
        }
    }

    private void askForFileType() {
        new MaterialDialog.Builder(PostActivity.this)
                .title(getResources().getString(R.string.select_your_file_type))
                .items(getResources().getStringArray(R.array.itemsArray))
                .itemsCallback((dialog, view, which, text) -> {
                    if (which == PHOTO) {
                        openGalleryForImage(PHOTO);
                    }
                    if (which == VIDEO) {
                        openGalleryForImage(VIDEO);
                    }
                })
                .show();

    }

    private void openGalleryForImage(int type) {
        Intent galleryIntent = null;
        if (type == Constants.PHOTO) {
            galleryIntent = new Intent(Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        }
        if (type == VIDEO) {
            galleryIntent = new Intent(Intent.ACTION_PICK,
                    MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
        }
        startActivityForResult(galleryIntent, type);
    }
}