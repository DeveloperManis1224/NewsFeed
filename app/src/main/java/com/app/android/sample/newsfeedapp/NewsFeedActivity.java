package com.app.android.sample.newsfeedapp;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
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

import java.util.ArrayList;
public class NewsFeedActivity extends AppCompatActivity {

    private SessionManager session;
    ProgressDialog progressDialog;
    private  String locationName;
    ArrayList<DataModel> name=new ArrayList<>();
    private RecyclerView lstView;
    private TextView location,welcome;
    private RecyclerView.LayoutManager mLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_feed);

        location = findViewById(R.id.txt_location);
        welcome = findViewById(R.id.txt_welcome);


        progressDialog = new ProgressDialog(NewsFeedActivity.this);
        progressDialog.setMessage("Loading...");
        session = new SessionManager();

        location.setText(session.getPreferences(NewsFeedActivity.this, Constants.LOCATION));
        welcome.setText("Welcome "+session.getPreferences(NewsFeedActivity.this, Constants.USERNAME)+",");

        lstView = findViewById(R.id.news_list);

        lstView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        lstView.setLayoutManager(mLayoutManager);

        locationName = session.getPreferences(NewsFeedActivity.this, Constants.LOCATION);


        if(Util.isConnect(NewsFeedActivity.this))
        {
            getPostdetails(session.getPreferences(NewsFeedActivity.this, Constants.LOCATION));
        }
        else
        {
            new AlertDialog.Builder(this)
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

    private void getPostdetails(String place)
    {
        progressDialog.show();
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = Constants.BASE_URL+"get_post.php?location="+place;
        Log.v("asasasasas",url);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.v("asasasasas121212",response);
                        progressDialog.dismiss();
                        JSONArray jary = null;
                        try {
                            jary = new JSONArray(response);
                            for(int i = 0; i<jary.length(); i++)
                            {
                                JSONObject jobj = jary.getJSONObject(i);
                                String location = jobj.getString("location");
                                String id = jobj.getString("id");
                                String image = jobj.getString("image");
                                name.add(new DataModel (location,image,id));
                            }

                            DataAdapter dad = new DataAdapter(name);
                            lstView.setAdapter(dad);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


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
