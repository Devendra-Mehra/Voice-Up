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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;

import com.afollestad.materialdialogs.MaterialDialog;
import com.devendra.voiceup.R;
import com.devendra.voiceup.post.view_model.PostViewModel;
import com.devendra.voiceup.post.view_model.PostViewModelFactory;
import com.devendra.voiceup.utils.Constants;
import com.devendra.voiceup.utils.FieldType;
import com.devendra.voiceup.utils.ViewState;
import com.devendra.voiceup.utils.custom_exception.FieldException;
import com.devendra.voiceup.utils.custom_exception.GeneralException;
import com.devendra.voiceup.utils.custom_exception.ImageException;
import com.devendra.voiceup.utils.out_come.Failure;
import com.devendra.voiceup.utils.out_come.OutCome;
import com.devendra.voiceup.utils.out_come.Progress;
import com.devendra.voiceup.utils.out_come.Success;
import com.devendra.voiceup.utils.out_come.SuccessPost;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

import static com.devendra.voiceup.utils.Constants.PHOTO;
import static com.devendra.voiceup.utils.Constants.VIDEO;

public class PostActivity extends AppCompatActivity implements View.OnClickListener {

    @Inject
    PostViewModelFactory postViewModelFactory;
    @Inject
    MediaController mediaController;
    private PostViewModel postViewModel;
    private AppCompatImageView acivPostPhoto;
    private TextView tvReAddMedia;
    private ProgressBar progressBar;
    private VideoView videoView;
    private ConstraintLayout constraintLayout;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        acivPostPhoto = findViewById(R.id.aciv_post_image);
        videoView = findViewById(R.id.vv_post_video);
        tvReAddMedia = findViewById(R.id.tv_re_addd_media);
        constraintLayout = findViewById(R.id.cl_file);
        progressBar = findViewById(R.id.progress_bar_loading);
        editText = findViewById(R.id.et_post_title);
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

        postViewModel.getValidateFileOutCome().observe(this, outCome -> {
            if (outCome instanceof SuccessPost) {
                changeFileState(ViewState.SUCCESS, outCome);
            } else if (outCome instanceof Failure) {
                changeFileState(ViewState.ERROR, outCome);
            } else {
                changeFileState(ViewState.LOADING, outCome);
            }
        });


        postViewModel.getValidatePostOutCome().observe(this, outCome -> {
            if (outCome instanceof Success) {
                changePostViewState(ViewState.SUCCESS, outCome);
            } else if (outCome instanceof Failure) {
                changePostViewState(ViewState.ERROR, outCome);
            } else {
                changePostViewState(ViewState.LOADING, outCome);
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
            if (requestCode == PHOTO || requestCode == VIDEO) {
                String path = getRealPathFromURI(this, data.getData());
                postViewModel.copyFileToAnotherDirectory(path, requestCode);
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
        if (id == R.id.tv_re_addd_media || id == R.id.cl_file) {
            postViewModel.openFile();

        } else if (id == R.id.btn_create_post) {
            postViewModel.validatePost(editText.getText().toString().trim(),
                    acivPostPhoto.getTag(),
                    videoView.getTag());

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

    private void changeFileState(ViewState viewStatus, OutCome outCome) {
        if (viewStatus == ViewState.SUCCESS) {
            SuccessPost success = (SuccessPost) outCome;
            videoView.setTag(null);
            acivPostPhoto.setTag(null);
            if (((SuccessPost) outCome).getImageType() == PHOTO) {
                acivPostPhoto.setVisibility(View.VISIBLE);
                videoView.setVisibility(View.GONE);
                acivPostPhoto.setScaleType(ImageView.ScaleType.FIT_XY);
                acivPostPhoto.setPadding(0, 0, 0, 0);
                acivPostPhoto.setImageURI(Uri.parse(Constants.FILE_LOCATION + success.getFileName()));
                acivPostPhoto.setTag(success.getFileName());
            } else {
                acivPostPhoto.setVisibility(View.GONE);
                videoView.setVisibility(View.VISIBLE);
                MediaController mediaController = new MediaController(PostActivity.this);
                mediaController.setAnchorView(videoView);
                videoView.setMediaController(mediaController);
                videoView.setVideoURI(Uri.parse(Constants.FILE_LOCATION + success.getFileName()));
                videoView.start();
                videoView.requestFocus();
                videoView.setTag(success.getFileName());

            }
            tvReAddMedia.setVisibility(View.VISIBLE);
            constraintLayout.setOnClickListener(null);

        } else if (viewStatus == ViewState.ERROR) {
            Failure failure = (Failure) outCome;
            if (failure.getThrowable() instanceof ImageException) {
                Toast.makeText(this, failure.getThrowable().getMessage()
                        , Toast.LENGTH_LONG).show();
            }
        } else {
            Progress progress = (Progress) outCome;
            progressBar.setVisibility(progress.isLoading()
                    ? View.VISIBLE : View.GONE);
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

    private void changePostViewState(ViewState viewStatus, OutCome outCome) {
        if (viewStatus == ViewState.SUCCESS) {
            Success<String> success = (Success) outCome;
            Toast.makeText(this, "" + success.getData(), Toast.LENGTH_LONG).show();
            finish();

        } else if (viewStatus == ViewState.ERROR) {
            Failure failure = (Failure) outCome;
            if (failure.getThrowable() instanceof FieldException) {
                if (((FieldException) failure.getThrowable()).getFieldType() == FieldType.TITLE) {
                    tvReAddMedia.setError(failure.getThrowable().getMessage());
                }
            } else if (failure.getThrowable() instanceof GeneralException) {
                Toast.makeText(this, failure.getThrowable().getMessage(), Toast.LENGTH_LONG).show();
            }
        } else {
            Progress progress = (Progress) outCome;
            progressBar.setVisibility(progress.isLoading()
                    ? View.VISIBLE : View.GONE);
        }
    }
}