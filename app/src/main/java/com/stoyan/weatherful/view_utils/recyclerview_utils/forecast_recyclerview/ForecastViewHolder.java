package com.stoyan.weatherful.view_utils.recyclerview_utils.forecast_recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.stoyan.weatherful.R;
import com.stoyan.weatherful.network.network_models.forecast_full_models.Data;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Stoyan on 27.1.2018 г..
 */

public class ForecastViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.tv_temperature)
    TextView temperature;

    @BindView(R.id.tv_chances_of_rain)
    TextView rainChance;

    @BindView(R.id.date)
    EditText date;


    public ForecastViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(Data data) {
        setTemperature(data);
        setRainChance(data);
    }

    private void setRainChance(Data data) {
        double probability = Double.parseDouble(data.getPrecipProbability());
        probability *= 100;
        rainChance.setText(probability + "%");
    }

    private void setTemperature(Data data) {
        String temp = data.getApparentTemperatureLow() + " - " + data.getApparentTemperatureHigh();
        temperature.setText(temp);
    }
}
