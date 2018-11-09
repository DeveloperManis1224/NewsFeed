package com.app.android.sample.newsfeedapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.app.android.sample.newsfeedapp.Util.Constants;
import com.bumptech.glide.Glide;

public class ZoomImage extends AppCompatActivity {

    private ImageView imgView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zoom_image);
        imgView = findViewById(R.id.img_zoom);

        String imgName = getIntent().getStringExtra("img");

        Glide.with(this).load(Constants.BASE_IMG_URL+imgName).into(imgView);

    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
