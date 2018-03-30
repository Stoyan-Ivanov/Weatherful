package com.stoyan.weatherful.network;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Created by Stoyan on 27.1.2018 г..
 */

public class WeatherfulApplication extends Application  {
    private static Context applicationContext;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = getBaseContext();

    }

    public static void showToast(String text) {
        Toast toast=Toast.makeText(applicationContext, text,Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 100);
        toast.show();
    }

    public static String getStringFromId(int id) {
        return applicationContext.getResources().getString(id);
    }

    public static Context getStaticContext() {
        return applicationContext;
    }
}
