package com.app.android.sample.newsfeedapp.Util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


public class Util {
public static boolean isConnect(Context cntx){
    boolean val = true;
    ConnectivityManager conMgr =  (ConnectivityManager)cntx.getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
if (netInfo == null){
val = false;
    }else{
val = true;
    }
    return val;
}
}
