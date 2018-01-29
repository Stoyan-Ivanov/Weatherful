package com.stoyan.weatherful.network;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.stoyan.weatherful.db.Location;
import com.stoyan.weatherful.network.models.forecast_full_models.ForecastFullResponse;
import com.stoyan.weatherful.network.models.forecast_summary_models.ForecastSummaryResponse;
import com.stoyan.weatherful.network.models.image_response_models.ImageResponse;
import com.stoyan.weatherful.network.models.image_response_models.Picture;
import com.stoyan.weatherful.view_utils.recyclerview_utils.forecast_recyclerview.ForecastRecyclerviewAdapter;
import com.stoyan.weatherful.view_utils.recyclerview_utils.locations_recyclerview.LocationViewHolder;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
