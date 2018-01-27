package com.stoyan.weatherful.network;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.stoyan.weatherful.models.Location;
import com.stoyan.weatherful.network.network_models.image_response_models.ImageResponse;
import com.stoyan.weatherful.network.network_models.image_response_models.Picture;
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

public class WeatherfulAPIImpl extends Application implements WeatherfulAPIImplContract{
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
    public void getLocationImageUrl(final LocationViewHolder viewHolder, Location location) {
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

    public static Context getStaticContext() {
        return applicationContext;
    }
}
