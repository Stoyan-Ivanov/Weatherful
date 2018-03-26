package com.stoyan.weatherful.view_utils.recyclerview_utils.forecast_recyclerview;

import com.stoyan.weatherful.R;
import com.stoyan.weatherful.network.WeatherfulApplication;
import com.stoyan.weatherful.network.models.forecast_full_models.Data;

import java.util.Calendar;
import java.util.Locale;

/**
 * Created by stoyan.ivanov on 3/26/2018.
 */

public class ForecastViewHolderPresenter {
    Data data;

    public ForecastViewHolderPresenter(Data data) {
        this.data = data;
    }

    public String getTemperature() {
        return WeatherfulApplication.getStringFromId(R.string.temperature_field)
                + data.getTemperatureLow()
                + WeatherfulApplication.getStringFromId(R.string.degree_symbol)
                + " - "
                + data.getTemperatureHigh()
                + WeatherfulApplication.getStringFromId(R.string.degree_symbol);
    }

    public String getRainChance() {
        Float probability = Float.parseFloat(data.getPrecipProbability()) * 100;

        return WeatherfulApplication.getStringFromId(R.string.rain_chance_field)
                + probability + "%";
    }

    public String getDateFromTimestamp() {
        Calendar date = Calendar.getInstance();
        date.setTimeInMillis(Long.valueOf(data.getTime()) * 1000);

        return date.get(Calendar.DAY_OF_MONTH) + "."
                + date.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.ENGLISH)
                + "." + date.get(Calendar.YEAR);
    }
}
