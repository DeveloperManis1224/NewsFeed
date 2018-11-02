package com.app.android.sample.newsfeedapp;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
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
    private DrawerLayout mDrawerLayout;
    private SessionManager session;
    ProgressDialog progressDialog;
    private  String locationName;
    ArrayList<DataModel> name=new ArrayList<>();
    public static RecyclerView lstView;
    private TextView location,welcome;
    private RecyclerView.LayoutManager mLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_navigation);
        location = findViewById(R.id.txt_location);
        welcome = findViewById(R.id.txt_welcome);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        progressDialog = new ProgressDialog(NewsFeedActivity.this);
        progressDialog.setMessage("Loading...");
        session = new SessionManager();
        location.setText(""+session.getPreferences(NewsFeedActivity.this, Constants.LOCATION));
        welcome.setText(""+session.getPreferences(NewsFeedActivity.this, Constants.USERNAME)+",");
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
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.as_above);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem)

                    {
                        switch (menuItem.getItemId())
                        {
                            case R.id.feedback:
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/")));
                                break;
                            case R.id.contactus:
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/")));
                                break;
                            case R.id.share:
                                Intent sendIntent = new Intent();
                                sendIntent.setAction(Intent.ACTION_SEND);
                                sendIntent.putExtra(Intent.EXTRA_TEXT, "App link: http://play.google.com");
                                sendIntent.setType("text/plain");
                                startActivity(sendIntent);
                                break;
                        }
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home){ // use android.R.id
            mDrawerLayout.openDrawer(Gravity.LEFT);
        }

        return super.onOptionsItemSelected(item);
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

    @Override
    public void onBackPressed() {
        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
        homeIntent.addCategory(Intent.CATEGORY_HOME);
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homeIntent);
    }
}
