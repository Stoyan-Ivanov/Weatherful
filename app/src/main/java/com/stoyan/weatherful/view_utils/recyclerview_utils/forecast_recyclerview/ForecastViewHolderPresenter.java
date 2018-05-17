package com.stoyan.weatherful.view_utils.recyclerview_utils.forecast_recyclerview;

import android.content.Context;

import com.stoyan.weatherful.R;
import com.stoyan.weatherful.WeatherfulApplication;
import com.stoyan.weatherful.network.models.forecast_full_models.Data;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by stoyan.ivanov on 3/26/2018.
 */

public class ForecastViewHolderPresenter {
    private Data mData;
    private Context mContext;

    public ForecastViewHolderPresenter(Data data) {
        this.mData = data;
        mContext = WeatherfulApplication.getStaticContext();
    }

    public String getTemperature() {
        float temperatureHigh = Float.parseFloat(mData.getTemperatureHigh());
        float temperatureLow = Float.parseFloat(mData.getTemperatureLow());
        return mContext.getString(R.string.temperature_field, (int)temperatureHigh, (int)temperatureLow);
    }

    public String getRainChance() {
        float probability = Float.parseFloat(mData.getPrecipProbability()) * 100;

        return mContext.getString(R.string.rain_chance_field, (int) probability);
    }

    public String getDayOfTheWeekFromTimestamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        Date dateFormat = new java.util.Date(Long.valueOf(mData.getTime()) * 1000);
        return sdf.format(dateFormat);
    }
}