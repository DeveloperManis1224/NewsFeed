package com.app.android.sample.newsfeedapp;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.app.android.sample.newsfeedapp.Util.Constants;
import com.app.android.sample.newsfeedapp.Util.SessionManager;

public class SplashScreen extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                SessionManager session = new SessionManager();
                if(session.getPreferences(SplashScreen.this,Constants.SESSION).equalsIgnoreCase(Constants.LOGIN))
                {
                    Intent in = new Intent(SplashScreen.this, NewsFeedActivity.class);
                    startActivity(in);
                }
                else
                {
                    Intent in = new Intent(SplashScreen.this, LoginPage.class);
                    startActivity(in);
                }
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
    @Override
    public void onBackPressed() {
        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
        homeIntent.addCategory(Intent.CATEGORY_HOME);
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homeIntent);
    }
}
