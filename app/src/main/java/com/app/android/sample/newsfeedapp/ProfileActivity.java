package com.app.android.sample.newsfeedapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.app.android.sample.newsfeedapp.Util.Constants;
import com.app.android.sample.newsfeedapp.Util.SessionManager;

public class ProfileActivity extends AppCompatActivity {
    private TextView txtName;
    private  TextView txtMail;
    private TextView txtLocation;
    SessionManager session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        session = new SessionManager();

        txtName = findViewById(R.id.txt_name);
        txtMail = findViewById(R.id.txt_mail);
        txtLocation = findViewById(R.id.txt_loc);

        txtName.setText("Name       : " + session.getPreferences(ProfileActivity.this,Constants.USERNAME));
        txtLocation.setText("Location   : "+session.getPreferences(ProfileActivity.this,Constants.LOCATION));
        txtMail.setText("Mail       : "+session.getPreferences(ProfileActivity.this,Constants.MAIL));
    }
    @Override
    public void onBackPressed() {
       Intent in = new Intent(ProfileActivity.this,NewsFeedActivity.class);
       startActivity(in);
       finish();
    }
}
