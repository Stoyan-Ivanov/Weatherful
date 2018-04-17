package com.stoyan.weatherful.network;

import com.stoyan.weatherful.network.models.image_response_models.ImageResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Stoyan on 29.1.2018 г..
 */

public interface QwantAPI {
    @GET("images?count=1&&offset=1")
    Observable<ImageResponse> getLocationImage(@Query("q") String searchedLocation);
}
