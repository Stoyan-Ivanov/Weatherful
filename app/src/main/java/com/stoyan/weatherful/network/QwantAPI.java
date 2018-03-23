package com.stoyan.weatherful.network;

import com.stoyan.weatherful.network.models.image_response_models.ImageResponse;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.functions.Consumer;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Stoyan on 29.1.2018 г..
 */

public interface QwantAPI {
    @GET("images?count=1&&offset=2")
    Observable<ImageResponse> getLocationImage(@Query("q") String searchedLocation);
}
