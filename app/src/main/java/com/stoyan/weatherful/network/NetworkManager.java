package com.stoyan.weatherful.network;

import android.util.Log;

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
 * Created by Stoya on 29.1.2018 г..
 */

public class NetworkManager implements NetworkManagerContract {
    private static NetworkManager instance;

    private Retrofit weatherfulRetrofit;
    private WeatherfulAPI weatherfulAPI;

    private Retrofit qwantRetrofit;
    private QwantAPI qwantAPI;

    public static NetworkManager getInstance(){
        if(instance == null){
            instance = new NetworkManager();
        }
        return instance;
    }

    private NetworkManager(){
        weatherfulRetrofit = new Retrofit.Builder()
                .baseUrl("https://api.darksky.net/forecast/e84ac66032ba27bb9911d3f27d96c11d/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        weatherfulAPI = weatherfulRetrofit.create(WeatherfulAPI.class);
        qwantRetrofit = new Retrofit.Builder()
                .baseUrl("https://api.qwant.com/api/search/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        qwantAPI = qwantRetrofit.create(QwantAPI.class);
    }

    @Override
    public void getLocationImageUrl(final LocationViewHolder viewHolder, final Location location) {
        String searchedLocation = location.getLocationName() + "," + location.getCountry();

        Call<ImageResponse> call = qwantAPI.getLocationImage(searchedLocation);

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
        Call<ForecastSummaryResponse> call = weatherfulAPI.getForecastSummaryResponse(location.getLatitude(),
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
        Call<ForecastFullResponse> call = weatherfulAPI.getFullForecastResponse(location.getLatitude(),
                location.getLongitude());

        call.enqueue(new Callback<ForecastFullResponse>() {
            @Override
            public void onResponse(Call<ForecastFullResponse> call, Response<ForecastFullResponse> response) {
                if(response.isSuccessful()) {
                    ForecastFullResponse fullResponse = response.body();
                    adapter.setNewData(fullResponse.getDaily().getData());
                }
            }

            @Override
            public void onFailure(Call<ForecastFullResponse> call, Throwable t) {
                Log.d("SII", "onFailure: getFullForecast()");
            }
        });

    }
}
