package com.stoyan.weatherful.view_utils.recyclerview_utils.locations_recyclerview;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.stoyan.weatherful.R;
import com.stoyan.weatherful.db.Location;
import com.stoyan.weatherful.WeatherfulApplication;
import com.stoyan.weatherful.network.DataManager;
import com.stoyan.weatherful.ui.forecast_activity.ForecastActivity;
import com.stoyan.weatherful.view_utils.recyclerview_utils.BaseViewHolder;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by Stoyan on 27.1.2018 г..
 */

public class LocationViewHolder extends BaseViewHolder {

    @BindView(R.id.iv_location) ImageView locationPicture;
    @BindView(R.id.tv_location_name) TextView tvLocationName;
    @BindView(R.id.tv_location_summary) TextView tvForecastSummary;
    @BindView(R.id.tv_location_temperature) TextView tvTemperature;

    @Inject DataManager mDataManager;
    private LocationsRecyclerViewAdapter mAdapter;
    private final int FIRST = 0;

    public LocationViewHolder(View itemView, LocationsRecyclerViewAdapter adapter) {
        super(itemView);
        this.mAdapter = adapter;
    }

    public void bind(final Location location) {

        tvLocationName.setText(location.getLocationName());
        setLocationPicture(location.getImageUrl());
        setForecastSummary(location.getForecastSummary().getHourly().getSummary());
        setTemperature(location.getForecastSummary().getHourly().getData().get(FIRST).getTemperature());

        setOnViewHolderClickListeners(location);
    }

    private void setOnViewHolderClickListeners(final Location location) {
        itemView.setOnClickListener(view -> ForecastActivity.getIntent(context, location));

        itemView.setOnLongClickListener(view -> {
            mDataManager.deleteLocation(location);
            removeItem();
            return false;
        });
    }

    private void removeItem() {
        mAdapter.removeItem(getAdapterPosition());
    }

    public void setLocationPicture(String url) {
        if ((itemView != null) && url != null) {
            Glide.with(itemView.getContext())
                    .load(url)
                    .centerCrop()
                    .placeholder(R.drawable.cityscape)
                    .into(locationPicture);
        }
    }

    public void setForecastSummary(String summary) {
        if (summary != null) {
            tvForecastSummary.setText(summary);
        } else {
            throw new NullPointerException("Provide valid summary!");
        }
    }

    public void setTemperature(String temp) {
        if (temp != null) {
            String displayableTemp = temp + WeatherfulApplication.getStringFromId(R.string.degree_symbol);
            tvTemperature.setText(displayableTemp);
        } else {
            throw new NullPointerException("Provide valid tvTemperature");
        }

    }
}
