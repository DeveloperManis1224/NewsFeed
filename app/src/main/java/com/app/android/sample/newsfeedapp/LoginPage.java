package com.app.android.sample.newsfeedapp;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.android.sample.newsfeedapp.Util.Constants;
import com.app.android.sample.newsfeedapp.Util.SessionManager;
import com.app.android.sample.newsfeedapp.Util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginPage extends AppCompatActivity {

    private EditText userName,passWord;
    private Button login;
    ProgressDialog progressDialog;
    private SessionManager session;
    private  String loginStatus,userLocation,username,userEmail,ID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        userName = findViewById(R.id.edt_username);
        passWord = findViewById(R.id.edt_pass);
        login = findViewById(R.id.btn_login);
        progressDialog = new ProgressDialog(LoginPage.this);
        progressDialog.setMessage("Loading...");
        session = new SessionManager();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Util.isConnect(LoginPage.this))
                {
                    if(isValid())
                    {
                        checkLogin();
                    }
                }
                else{
                    new AlertDialog.Builder(LoginPage.this)
                            .setTitle("No Internet Connection")
                            .setMessage("please turn on the mobile data or Wifi")
                            .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                }
            }
        });
    }
    private void checkLogin()
    {
        progressDialog.show();
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = Constants.BASE_URL+"user_login.php";
        Log.v("asasasasas",url);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.v("asasasasas",response);
                        try {
                            progressDialog.dismiss();
                            JSONArray jary = new JSONArray(response);
                            JSONObject jobj = jary.getJSONObject(0);
                            loginStatus = jobj.getString("Status");
                            userLocation = jobj.getString("location");
                            userEmail = jobj.getString("User_id");
                            username = jobj.getString("user_name");
                            ID = jobj.getString("id");
                            if(loginStatus.equalsIgnoreCase("Success"))
                            {
                                Toast.makeText(LoginPage.this, "Login Successfull", Toast.LENGTH_SHORT).show();
                                session.setPreferences(LoginPage.this,Constants.LOCATION,""+userLocation);
                                session.setPreferences(LoginPage.this,Constants.USERNAME,""+username);
                                session.setPreferences(LoginPage.this,Constants.SESSION,Constants.LOGIN);
                                session.setPreferences(LoginPage.this,Constants.MAIL,""+userEmail);
                                Intent in = new Intent(LoginPage.this,NewsFeedActivity.class);
                                startActivity(in);
                                finish();
                            }
                            else
                            {
                                Toast.makeText(LoginPage.this, "Login Failed...", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.v("asasasasas",e.getMessage());
                            progressDialog.dismiss();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("asasasasas",error.getMessage());
                progressDialog.dismiss();

            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params =  new HashMap<String, String>();
                params.put("username",""+userName.getText().toString().trim());
                params.put("password",""+passWord.getText().toString().trim());
                return params;
            }
        };
        queue.add(stringRequest);
    }
    private boolean isValid()
    {
        boolean val= true;

        if(userName.getText().toString().trim().isEmpty())
        {
            val = false;
            userName.setError("Invalid");
        }
        if(passWord.getText().toString().trim().isEmpty())
        {
            val = false;
            passWord.setError("Invalid");
        }
        return val;
    }
    @Override
    public void onBackPressed() {
        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
        homeIntent.addCategory(Intent.CATEGORY_HOME);
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homeIntent);
    }

    public void onRequest(View view) {


    }
}
