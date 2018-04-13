package com.stoyan.weatherful;

import android.app.Application;
import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

import com.stoyan.weatherful.di.AppComponent;
import com.stoyan.weatherful.di.AppModule;
import com.stoyan.weatherful.di.DaggerAppComponent;

/**
 * Created by Stoyan on 27.1.2018 г..
 */

public class WeatherfulApplication extends Application  {
    private static WeatherfulApplication application;
    private static Context applicationContext;
    AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        //applicationContext = application.getApplicationContext();
        application = this;
        applicationContext = getBaseContext();
    }

    public static WeatherfulApplication getInstance(){
        return application;
    }

    public static void showToast(String text) {
        Toast toast=Toast.makeText(applicationContext, text,Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 100);
        toast.show();
    }

    public AppComponent getComponent() {
        if (appComponent == null) {
            appComponent = DaggerAppComponent.builder()
                    .appModule(new AppModule(this))
                    .build();
        }
        return appComponent;
    }

    public static String getStringFromId(int id) {
        return applicationContext.getResources().getString(id);
    }

    public static Context getStaticContext() {
        return applicationContext;
    }
}
