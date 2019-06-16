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
import android.widget.MediaController;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.afollestad.materialdialogs.MaterialDialog;
import com.devendra.voiceup.R;
import com.devendra.voiceup.databinding.ActivityPostBinding;
import com.devendra.voiceup.post.view_model.PostViewModel;
import com.devendra.voiceup.post.view_model.PostViewModelFactory;
import com.devendra.voiceup.utils.Constants;
import com.devendra.voiceup.utils.FieldType;
import com.devendra.voiceup.utils.PostValidateSuccessResult;
import com.devendra.voiceup.utils.ViewState;
import com.devendra.voiceup.utils.custom_exception.FieldException;
import com.devendra.voiceup.utils.custom_exception.GeneralException;
import com.devendra.voiceup.utils.out_come.Failure;
import com.devendra.voiceup.utils.out_come.OutCome;
import com.devendra.voiceup.utils.out_come.Progress;
import com.devendra.voiceup.utils.out_come.Success;

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
    private ActivityPostBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_post);

        postViewModel = ViewModelProviders.of(this, postViewModelFactory)
                .get(PostViewModel.class);

        setObserve();
    }

    private void setObserve() {
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
            if (outCome instanceof PostValidateSuccessResult) {
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
        if (id == R.id.tv_re_add_media || id == R.id.cl_add_file) {
            postViewModel.openFile();

        } else if (id == R.id.btn_create_post) {
            extractData();
        }
    }

    private void extractData() {
        String imageUrl = null, videoUrl = null;
        if (binding.appCompatImageViewPost.getTag() != null)
            imageUrl = binding.appCompatImageViewPost.getTag().toString();
        if (binding.videoViewPost.getTag() != null)
            videoUrl = binding.videoViewPost.getTag().toString();
        postViewModel.validatePost(binding.etPostTitle.getText().toString().trim(),
                imageUrl, videoUrl);


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

    private void changeFileState(ViewState viewStatus, OutCome outCome) {
        if (viewStatus == ViewState.SUCCESS) {
            successFileState(outCome);
        } else if (viewStatus == ViewState.ERROR) {
            failureFileState(outCome);
        } else {
            progressFileState(outCome);
        }
    }

    private void successFileState(OutCome outCome) {
        PostValidateSuccessResult success = (PostValidateSuccessResult) outCome;
        binding.videoViewPost.setTag(null);
        binding.appCompatImageViewPost.setTag(null);
        if (((PostValidateSuccessResult) outCome).getImageType() == PHOTO) {
            binding.appCompatImageViewPost.setVisibility(View.VISIBLE);
            binding.videoViewPost.setVisibility(View.GONE);
            binding.appCompatImageViewPost.setScaleType(ImageView.ScaleType.FIT_XY);
            binding.appCompatImageViewPost.setPadding(0, 0, 0, 0);
            binding.appCompatImageViewPost.setImageURI(Uri.parse(Constants.FILE_LOCATION + success.getFileName()));
            binding.appCompatImageViewPost.setTag(success.getFileName());
        } else {
            binding.appCompatImageViewPost.setVisibility(View.GONE);
            binding.videoViewPost.setVisibility(View.VISIBLE);
            MediaController mediaController = new MediaController(PostActivity.this);
            mediaController.setAnchorView(binding.videoViewPost);
            binding.videoViewPost.setMediaController(mediaController);
            binding.videoViewPost.setVideoURI(Uri.parse(Constants.FILE_LOCATION + success.getFileName()));
            binding.videoViewPost.requestFocus();
            binding.videoViewPost.setTag(success.getFileName());

        }
        binding.tvReAddMedia.setVisibility(View.VISIBLE);
        binding.clAddFile.setOnClickListener(null);
    }

    private void progressFileState(OutCome outCome) {
        Progress progress = (Progress) outCome;
        binding.progressBarLoading.setVisibility(progress.isLoading()
                ? View.VISIBLE : View.GONE);
    }

    private void failureFileState(OutCome outCome) {
        Failure failure = (Failure) outCome;
        Toast.makeText(this, failure.getThrowable().getMessage()
                , Toast.LENGTH_LONG).show();

    }


    private void changePostViewState(ViewState viewStatus, OutCome outCome) {
        if (viewStatus == ViewState.SUCCESS) {
            successPostViewState(outCome);
        } else if (viewStatus == ViewState.LOADING) {
            progressPostViewState(outCome);
        } else {
            failurePostViewState(outCome);
        }
    }

    private void successPostViewState(OutCome outCome) {
        Success<String> success = (Success) outCome;
        Toast.makeText(this, success.getData(), Toast.LENGTH_LONG).show();
        setResult(Constants.HOME_RESULT);
        finish();
    }

    private void progressPostViewState(OutCome outCome) {
        Progress progress = (Progress) outCome;
        binding.progressBarLoading.setVisibility(progress.isLoading()
                ? View.VISIBLE : View.GONE);
    }

    private void failurePostViewState(OutCome outCome) {
        Failure failure = (Failure) outCome;
        if (failure.getThrowable() instanceof FieldException) {
            if (((FieldException) failure.getThrowable()).getFieldType() == FieldType.TITLE) {
                binding.etPostTitle.setError(failure.getThrowable().getMessage());
            }
        } else if (failure.getThrowable() instanceof GeneralException) {
            Toast.makeText(this, failure.getThrowable().getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}