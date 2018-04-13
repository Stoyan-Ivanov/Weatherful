package com.stoyan.weatherful;

import android.app.Application;
import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.stoyan.weatherful.di.AppComponent;
import com.stoyan.weatherful.di.AppModule;
import com.stoyan.weatherful.di.DaggerAppComponent;

import io.fabric.sdk.android.Fabric;
import io.paperdb.Paper;

/**
 * Created by Stoyan on 27.1.2018 г..
 */

public class WeatherfulApplication extends Application  {
    private static WeatherfulApplication sApplication;
    private static Context sContext;
    private AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        sApplication = this;
        sContext = getBaseContext();

        Paper.init(sContext);
        Fabric.with(sContext, new Crashlytics());
    }

    public static WeatherfulApplication getInstance(){
        return sApplication;
    }

    public static void showToast(String text) {
        Toast toast=Toast.makeText(sContext, text,Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 100);
        toast.show();
    }

    public AppComponent getComponent() {
        if (mAppComponent == null) {
            mAppComponent = DaggerAppComponent.builder()
                    .appModule(new AppModule(this))
                    .build();
        }
        return mAppComponent;
    }

    public static String getStringFromId(int id) {
        return sContext.getResources().getString(id);
    }

    public static Context getStaticContext() {
        return sContext;
    }
}
