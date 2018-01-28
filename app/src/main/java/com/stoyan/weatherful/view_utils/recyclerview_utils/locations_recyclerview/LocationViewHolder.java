package com.stoyan.weatherful.view_utils.recyclerview_utils.locations_recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.stoyan.weatherful.R;
import com.stoyan.weatherful.models.Location;
import com.stoyan.weatherful.network.WeatherfulAPIImpl;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Stoyan on 27.1.2018 г..
 */

public class LocationViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.iv_location)
    ImageView locationPicture;

    @BindView(R.id.tv_location_name)
    TextView tvLocationName;

    @BindView(R.id.tv_location_summary)
    TextView tvForecastSummary;

    @BindView(R.id.tv_location_temperature)
    TextView tvTemperature;


    public LocationViewHolder(View itemView) {
        super(itemView);
    }

    public void bind(final Location location, final OnItemClickListener onItemClickListener) {

        ButterKnife.bind(this, itemView);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.OnItemClick(location);
            }
        });

        tvLocationName.setText(location.getLocationName());

        WeatherfulAPIImpl weatherfulAPI = new WeatherfulAPIImpl();
        weatherfulAPI.getLocationImageUrl(this, location);
        weatherfulAPI.getForecastSummary(this, location);

    }

    public void setLocationPicture(String url) {

        if((itemView != null) && url != null) {

            Glide.with(locationPicture.getContext())
                    .load(url)
                    .centerCrop()
                    .into(locationPicture);
        } else {
            throw new NullPointerException("Provide valid image url");
        }
    }

    public void setForecastSummary(String summary) {
        if(summary != null) {
            tvForecastSummary.setText(summary);
        } else {
            throw new NullPointerException("Provide valid summary!");
        }
    }

    public void setTemperature(String temp) {
        if(temp != null) {
            tvTemperature.setText(temp);
        } else {
            throw new NullPointerException("Provide valid tvTemperature");
        }

    }
}
