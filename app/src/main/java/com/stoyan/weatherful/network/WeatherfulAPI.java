package com.stoyan.weatherful.network;

import com.stoyan.weatherful.network.models.forecast_full_models.ForecastFullResponse;
import com.stoyan.weatherful.network.models.forecast_summary_models.ForecastSummaryResponse;
import com.stoyan.weatherful.network.models.image_response_models.ImageResponse;


import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Stoyan on 27.1.2018 г..
 */

public interface WeatherfulAPI {

    @GET("{latitude},{longitude}?exclude=currently,minutely,daily,alerts&&units=si")
    Single<ForecastSummaryResponse> getForecastSummaryResponse(@Path("latitude") double latitude,
                                                               @Path("longitude") double longitude);

    @GET("{latitude},{longitude}?exclude=currently,minutely,hourly,alerts&&units=si")
    Single<ForecastFullResponse> getFullForecastResponse(@Path("latitude") double latitude,
                                                       @Path("longitude") double longitude);
}
