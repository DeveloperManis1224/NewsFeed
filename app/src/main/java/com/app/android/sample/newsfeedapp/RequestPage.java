package com.app.android.sample.newsfeedapp;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.android.sample.newsfeedapp.Util.Constants;

public class RequestPage extends AppCompatActivity {
    private EditText txtEmail;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_page);
        txtEmail = findViewById(R.id.edt_re_email);
        progressDialog = new ProgressDialog(RequestPage.this);
        progressDialog.setMessage("Loading...");
    }

    public void onRequestMail(View view) {
        if(!txtEmail.getText().toString().isEmpty())
        {
            onSubmit();

        }
    }
    private  void onSubmit()
    {
        progressDialog.show();
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = Constants.BASE_URL+"get_post.php?location=";
        Log.v("asasasasas",url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.v("asasasasas121212",response);
                        progressDialog.dismiss();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("asasasasas",error.getMessage());
                progressDialog.dismiss();
            }
        });
        queue.add(stringRequest);
    }
}
