package com.stoyan.weatherful.network;

import com.stoyan.weatherful.models.Location;
import com.stoyan.weatherful.view_utils.recyclerview_utils.forecast_recyclerview.ForecastRecyclerviewAdapter;
import com.stoyan.weatherful.view_utils.recyclerview_utils.locations_recyclerview.LocationViewHolder;

/**
 * Created by Stoyan on 27.1.2018 г..
 */

public interface WeatherfulAPIImplContract {
    void getLocationImageUrl(LocationViewHolder viewHolder, Location locaton);

    void getForecastSummary(LocationViewHolder viewHolder, Location locaton);

    void getWeeklyForecast(Location location, ForecastRecyclerviewAdapter adapter);
}
