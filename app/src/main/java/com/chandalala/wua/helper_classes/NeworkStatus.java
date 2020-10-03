package com.chandalala.wua.helper_classes;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Toast;

import static android.content.Context.CONNECTIVITY_SERVICE;

public class NeworkStatus {

    private static ConnectivityManager connectivityManager;
    private static NetworkInfo networkInfo;
    private static AppCompatActivity activity;
    private Context context;
    private NetworkReciever networkReciever;

    public NeworkStatus(Context context) {
        this.context=context;
        activity= (AppCompatActivity) context;
        connectivityManager=(ConnectivityManager) activity.getSystemService(CONNECTIVITY_SERVICE);
        networkInfo=connectivityManager.getActiveNetworkInfo();
        networkReciever=new NetworkReciever();
        IntentFilter filter=new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
    }

    public boolean isConnected() {
        return networkInfo != null && networkInfo.isConnected();
    }

    public static class NetworkReciever extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            networkInfo=connectivityManager.getActiveNetworkInfo();

            if (networkInfo!=null)
                {
                    boolean isWiFiAvailable=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnected();

                    boolean isMobileAvailable=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnected();

                    if (isWiFiAvailable)
                        {
                            Toast.makeText(activity,"Wifi Reconnected", Toast.LENGTH_SHORT).show();
                        }
                        else if (isMobileAvailable)
                            {
                                Toast.makeText(activity,"Data reconnected", Toast.LENGTH_SHORT).show();

                            }
                            else
                                {
                                    Toast.makeText(activity,"Network signal lost", Toast.LENGTH_SHORT).show();
                                }
                }


        }
    }
}
