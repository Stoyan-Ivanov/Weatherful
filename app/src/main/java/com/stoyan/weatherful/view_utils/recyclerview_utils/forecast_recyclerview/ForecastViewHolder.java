package com.stoyan.weatherful.view_utils.recyclerview_utils.forecast_recyclerview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.stoyan.weatherful.R;
import com.stoyan.weatherful.network.WeatherfulApplication;
import com.stoyan.weatherful.network.models.forecast_full_models.Data;

import java.util.ArrayList;
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
    TextView tvTemperature;

    @BindView(R.id.tv_chances_of_rain)
    TextView tvRainChance;

    @BindView(R.id.tv_date)
    TextView tvDateHolder;


    public ForecastViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(final ArrayList<Data> data, final int position, final OnItemClickListener onItemClickListener) {
        Data currData = data.get(position);

        setDate(currData);
        setWeatherImage(currData);
        setTemperature(currData);
        setRainChance(currData);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.OnItemClick(data, position);
            }
        });

    }

    private void setDate(final Data data) {
        tvDateHolder.setText(getDateFromTimestamp(data));
    }

    private void setWeatherImage(final Data data) {
        weatherImage.setImageDrawable(getDrawableByName(data.getIcon()));
    }

    private void setTemperature(final Data data) {
        String temp = "Temperature: " + data.getTemperatureLow()+ "\u2103" +
                " - " + data.getTemperatureHigh() + "\u2103";
        tvTemperature.setText(temp);
    }

    private void setRainChance(final Data data) {
        double probability = Double.parseDouble(data.getPrecipProbability()) * 100;
        String displayRainChance = "Chance of raining: " + probability + "%";
        tvRainChance.setText(displayRainChance);
    }

    private String getDateFromTimestamp(final Data data) {
        Calendar date = Calendar.getInstance();
        date.setTimeInMillis(Long.valueOf(data.getTime()) * 1000);

        return date.get(Calendar.DAY_OF_MONTH) + "." + date.get(Calendar.MONTH)
                + "." + date.get(Calendar.YEAR);
    }

    private Drawable getDrawableByName(String drawableName) {
        drawableName = drawableName.replaceAll("-","_").toLowerCase();

        Context context = WeatherfulApplication.getStaticContext();
        int resID = context.getResources().getIdentifier(drawableName , "drawable", context.getPackageName());

        return context.getResources().getDrawable(resID );
    }
}
