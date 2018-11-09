package com.app.android.sample.newsfeedapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ContactUs extends AppCompatActivity {
    private EditText name,phone,email;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        name = findViewById(R.id.edt_name);
        phone = findViewById(R.id.edt_phone);
        email = findViewById(R.id.edt_mail);
        progressDialog = new ProgressDialog(ContactUs.this);
        progressDialog.setMessage("Loading...");
    }

    private boolean isValid()
    {
        boolean val = true;

        if(name.getText().toString().isEmpty())
        {
            val = false;
            name.setError("Invalid");
        }
        if(phone.getText().toString().isEmpty())
        {
            val = false;
            phone.setError("Invalid");
        }
        if(email.getText().toString().isEmpty())
        {
            val = false;
            email.setError("Invalid");
        }
        return  val;
    }

    public void onSubmitClick(View v)
    {
        if(isValid())
        {
            onSubmit();
        }
    }
    private  void onSubmit()
    {
            progressDialog.show();
            RequestQueue queue = Volley.newRequestQueue(this);
            String url = Constants.BASE_URL+"contact_us.php";
            Log.v("asasasasas",url);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.v("asasasasas121212",response);
                            try {
                                JSONObject jobj = new JSONObject(response);
                                String sts = jobj.getString("status");
                                if(sts.equalsIgnoreCase("success"))
                                {
                                    Toast.makeText(ContactUs.this, "Thank you...we will contact you soon.", Toast.LENGTH_SHORT).show();
                                    Intent in = new Intent(ContactUs.this,NewsFeedActivity.class);
                                    startActivity(in);
                                    finish();
                                }
                                else
                                {
                                    Toast.makeText(ContactUs.this, "Failed...", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                           // Toast.makeText(ContactUs.this, "", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
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
                    params.put("name",""+name.getText().toString().trim());
                    params.put("phone",""+phone.getText().toString().trim());
                    params.put("mail",""+email.getText().toString().trim());
                    return params;
                }
            };
            queue.add(stringRequest);
    }
    @Override
    public void onBackPressed() {
        Intent in = new Intent(ContactUs.this,NewsFeedActivity.class);
        startActivity(in);
        finish();
    }
}
