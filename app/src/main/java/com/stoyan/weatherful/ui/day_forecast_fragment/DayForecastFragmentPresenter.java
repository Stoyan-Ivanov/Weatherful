package com.stoyan.weatherful.ui.day_forecast_fragment;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.stoyan.weatherful.Constants;
import com.stoyan.weatherful.R;
import com.stoyan.weatherful.WeatherfulApplication;
import com.stoyan.weatherful.network.models.forecast_full_models.Data;
import com.stoyan.weatherful.ui.base_ui.contract.BaseViewContract;
import com.stoyan.weatherful.ui.base_ui.presenter.BasePresenter;

import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Stoyan on 28.1.2018 г..
 */

public class DayForecastFragmentPresenter extends BasePresenter<BaseViewContract> {
    private Data data;
    private Context context;

    public DayForecastFragmentPresenter(final Bundle arguments, BaseViewContract view) {
        super(view);
        getExtras(arguments);
        context = WeatherfulApplication.getStaticContext();
    }


    public void getExtras(final Bundle arguments) {
        data = arguments.getParcelable(Constants.EXTRA_DATA);
    }

    public Drawable getImageDrawable() {
        return context.getResources().getDrawable(data.getIcon().getResourceId());
    }

    public String getDate() {
        return getDateFromTimestamp();
    }

    public String getMinTemperature() {
        return context.getString(R.string.min_temperature_field, data.getTemperatureLow());
    }

    public String getMaxTemperature() {
        return context.getString(R.string.max_temperature_field, data.getTemperatureHigh());
    }

    public String getWindSpeed() {
        return context.getString(R.string.wind_speed_field, data.getWindSpeed());
    }


    public String getRainChance() {
        float probability = Float.parseFloat(data.getPrecipProbability()) * 100;
        String displayProbability = String.format("%.2f", probability);

        return context.getString(R.string.rain_chance_field, displayProbability);
    }

    private String getDateFromTimestamp() {
        Calendar date = Calendar.getInstance();
        date.setTimeInMillis(Long.valueOf(data.getTime()) * 1000);

        return date.get(Calendar.DAY_OF_MONTH) + "."
                + date.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.ENGLISH)
                + "." + date.get(Calendar.YEAR);
    }

    @Override
    public void onViewDestroy() {

    }
}
