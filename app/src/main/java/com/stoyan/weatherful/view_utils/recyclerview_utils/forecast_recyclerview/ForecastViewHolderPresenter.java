package com.stoyan.weatherful.view_utils.recyclerview_utils.forecast_recyclerview;

import android.content.Context;

import com.stoyan.weatherful.R;
import com.stoyan.weatherful.WeatherfulApplication;
import com.stoyan.weatherful.network.models.forecast_full_models.Data;

import java.util.Calendar;
import java.util.Locale;

/**
 * Created by stoyan.ivanov on 3/26/2018.
 */

public class ForecastViewHolderPresenter {
    private Data data;
    private Context context;

    public ForecastViewHolderPresenter(Data data) {
        this.data = data;
        context = WeatherfulApplication.getStaticContext();
    }

    public String getTemperature() {
        return context.getString(R.string.temperature_field,
                data.getTemperatureLow(), data.getTemperatureHigh());
    }

    public String getRainChance() {
        float probability = Float.parseFloat(data.getPrecipProbability()) * 100;
        String displayProbability = String.format("%.2f", probability);

        return context.getString(R.string.rain_chance_field, displayProbability);
    }

    public String getDateFromTimestamp() {
        Calendar date = Calendar.getInstance();
        date.setTimeInMillis(Long.valueOf(data.getTime()) * 1000);

        return date.get(Calendar.DAY_OF_MONTH) + "."
                + date.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.ENGLISH)
                + "." + date.get(Calendar.YEAR);
    }
}
