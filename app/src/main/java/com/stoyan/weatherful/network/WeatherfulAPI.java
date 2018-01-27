package com.stoyan.weatherful.network;

import com.stoyan.weatherful.network.network_models.image_response_models.ImageResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Stoyan on 27.1.2018 г..
 */

public interface WeatherfulAPI {
    @GET("images?offset=1")
    Call<ImageResponse> getLocationImage(@Query("q") String searchedLocation);
}
