package com.devendra.voiceup.home.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.devendra.voiceup.R;
import com.devendra.voiceup.post.PostActivity;

import dagger.android.AndroidInjection;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


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

        }
        return super.onOptionsItemSelected(item);
    }
}
