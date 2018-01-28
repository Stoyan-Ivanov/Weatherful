package com.stoyan.weatherful.view_utils.recyclerview_utils.forecast_recyclerview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.stoyan.weatherful.R;
import com.stoyan.weatherful.network.WeatherfulAPIImpl;
import com.stoyan.weatherful.network.network_models.forecast_full_models.Data;

import org.w3c.dom.Text;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Stoyan on 27.1.2018 г..
 */

public class ForecastViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.iv_weather_icon)
    ImageView weatherImage;

    @BindView(R.id.tv_temperature)
    TextView temperature;

    @BindView(R.id.tv_chances_of_rain)
    TextView rainChance;

    @BindView(R.id.tv_date)
    TextView dateHolder;


    public ForecastViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(Data data) {
        setDate(data);
        setWeatherImage(data);
        setTemperature(data);
        setRainChance(data);
    }

    private void setDate(Data data) {
        Calendar date = Calendar.getInstance();
        date.setTimeInMillis(Long.valueOf(data.getTime()) *1000);

        String displayDate = date.get(Calendar.DAY_OF_MONTH) + "." + date.get(Calendar.MONTH)
                                + "." + date.get(Calendar.YEAR);

        dateHolder.setText(displayDate);
    }

    private void setWeatherImage(Data data) {
        String iconName = data.getIcon();

        iconName = iconName.replaceAll("-","_").toLowerCase();

        Context context = WeatherfulAPIImpl.getStaticContext();
        int resID = context.getResources().getIdentifier(iconName , "drawable", context.getPackageName());
        Drawable drawable = context.getResources().getDrawable(resID );

        weatherImage.setImageDrawable(drawable);
    }

    private void setTemperature(Data data) {
        String temp = "Temperature: " + data.getTemperatureLow()+ "\u2103" +
                " - " + data.getTemperatureHigh() + "\u2103";
        temperature.setText(temp);
    }

    private void setRainChance(Data data) {
        double probability = Double.parseDouble(data.getPrecipProbability()) * 100;
        String displayRainChance = "Chance of raining: " + probability + "%";
        rainChance.setText(displayRainChance);
    }
}
