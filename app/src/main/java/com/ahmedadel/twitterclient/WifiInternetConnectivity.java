package com.ahmedadel.twitterclient;

import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Log;

/**
 * Created by ahmedadel on 24/06/16.
 *
 * WifiInternetConnectivity is a singleton class that check on all Internet connectivity resources
 */
public class WifiInternetConnectivity {

    private final String TAG = WifiInternetConnectivity.class.getSimpleName();

    private static Context context;
    private static WifiInternetConnectivity wifiInternetConnectivity;

    private WifiInternetConnectivity() {
    }

    public static WifiInternetConnectivity getInstance(Context context) {
        WifiInternetConnectivity.context = context;
        if (wifiInternetConnectivity == null) {
            wifiInternetConnectivity = new WifiInternetConnectivity();
            return wifiInternetConnectivity;
        }
        return wifiInternetConnectivity;
    }

    public boolean checkInternetAvailability() {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        // test for connection
        if (cm.getActiveNetworkInfo() != null
                && cm.getActiveNetworkInfo().isAvailable()
                && cm.getActiveNetworkInfo().isConnected()) {
            return true;
        } else {
            Log.v(TAG, "Internet Connection Not Present");
            return false;
        }
    }

}
