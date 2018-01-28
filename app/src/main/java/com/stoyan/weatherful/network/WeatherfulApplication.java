package com.stoyan.weatherful.network;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.stoyan.weatherful.models.Location;
import com.stoyan.weatherful.network.network_models.forecast_full_models.ForecastFullResponse;
import com.stoyan.weatherful.network.network_models.forecast_summary_models.ForecastSummaryResponse;
import com.stoyan.weatherful.network.network_models.image_response_models.ImageResponse;
import com.stoyan.weatherful.network.network_models.image_response_models.Picture;
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

public class WeatherfulApplication extends Application implements WeatherfulAPIImplContract{
    private static Context applicationContext;
    private static Retrofit retrofit;
    private static WeatherfulAPI api;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = getBaseContext();

    }

    private void createRetrofitInstance(String Url) {
        retrofit = new Retrofit.Builder()
                .baseUrl(Url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(WeatherfulAPI.class);
    }

    @Override
    public void getLocationImageUrl(final LocationViewHolder viewHolder, final Location location) {
        createRetrofitInstance(NetworkConstants.IMAGES_API_URL);

        String searchedLocation = location.getLocationName() + "," + location.getCountry();

        Call<ImageResponse> call = api.getLocationImage(searchedLocation);

        call.enqueue(new Callback<ImageResponse>() {
            @Override
            public void onResponse(Call<ImageResponse> call, Response<ImageResponse> response) {
                if(response.isSuccessful()) {
                    ImageResponse imageResponse = response.body();
                    ArrayList<Picture> pictures = imageResponse.getData().getResult().getPictures();
                    viewHolder.setLocationPicture("https:" + pictures.get(0).getThumbnailUrl());
                }
            }

            @Override
            public void onFailure(Call<ImageResponse> call, Throwable t) {
                Log.d("SII", "onFailure: getImage()");
            }
        });
    }

    @Override
    public void getForecastSummary(final LocationViewHolder viewHolder, final Location location) {
        createRetrofitInstance(NetworkConstants.FORECAST_API_URL);

        Call<ForecastSummaryResponse> call = api.getForecastSummaryResponse(location.getLatitude(),
                                                                            location.getLongitude());

        call.enqueue(new Callback<ForecastSummaryResponse>() {
            @Override
            public void onResponse(Call<ForecastSummaryResponse> call, Response<ForecastSummaryResponse> response) {
                if(response.isSuccessful()) {
                    ForecastSummaryResponse summaryResponse = response.body();

                    viewHolder.setForecastSummary(summaryResponse.getHourly().getSummary());

                    viewHolder.setTemperature(summaryResponse.getHourly()
                            .getData().get(0).getTemperature() + "\u2103");
                }
            }

            @Override
            public void onFailure(Call<ForecastSummaryResponse> call, Throwable t) {
                Log.d("SII", "onFailure: getForecastSummary()");
            }
        });

    }

    @Override
    public void getWeeklyForecast(final Location location, final ForecastRecyclerviewAdapter adapter) {
        createRetrofitInstance(NetworkConstants.FORECAST_API_URL);

        Call<ForecastFullResponse> call = api.getFullForecastResponse(location.getLatitude(),
                location.getLongitude());

        call.enqueue(new Callback<ForecastFullResponse>() {
            @Override
            public void onResponse(Call<ForecastFullResponse> call, Response<ForecastFullResponse> response) {
                if(response.isSuccessful()) {
                    ForecastFullResponse fullResponse = response.body();
                    Log.d("sii", "onResponse: " + fullResponse);
                    adapter.setNewData(fullResponse.getDaily().getData());
                }
            }

            @Override
            public void onFailure(Call<ForecastFullResponse> call, Throwable t) {
                Log.d("SII", "onFailure: getFullForecast()");
            }
        });

    }

    public static void showToast(String text) {
        Toast toast=Toast.makeText(applicationContext, text,Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 100);
        toast.show();
    }

    public static Context getStaticContext() {
        return applicationContext;
    }
}
