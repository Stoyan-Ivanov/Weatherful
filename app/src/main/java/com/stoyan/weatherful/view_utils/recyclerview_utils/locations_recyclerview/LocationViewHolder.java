package com.stoyan.weatherful.view_utils.recyclerview_utils.locations_recyclerview;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.stoyan.weatherful.R;
import com.stoyan.weatherful.db.Location;
import com.stoyan.weatherful.ui.forecast_activity.ForecastActivity;
import com.stoyan.weatherful.view_utils.recyclerview_utils.BaseViewHolder;

import butterknife.BindView;

/**
 * Created by Stoyan on 27.1.2018 г..
 */

public class LocationViewHolder extends BaseViewHolder {

    @BindView(R.id.iv_location) ImageView locationPicture;
    @BindView(R.id.tv_location_name) TextView tvLocationName;
    @BindView(R.id.tv_location_temperature) TextView tvTemperature;

    private LocationsRecyclerViewAdapter mAdapter;
    private Context mContext;
    private final int FIRST = 0;

    public LocationViewHolder(View itemView, LocationsRecyclerViewAdapter adapter) {
        super(itemView);
        this.mAdapter = adapter;
        this.mContext = itemView.getContext();
    }

    public void bind(final Location location) {

        tvLocationName.setText(location.getLocationName());
        setLocationPicture(location.getThumbnailUrl());
        setTemperature(location);

        setOnViewHolderClickListeners(location);
    }

    private void setOnViewHolderClickListeners(final Location location) {
        itemView.setOnClickListener(view -> mContext.startActivity(ForecastActivity.getIntent(mContext, location)));

        itemView.setOnLongClickListener(view -> {
            mAdapter.removeItem(getAdapterPosition(), location);
            return false;
        });
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

    public void setTemperature(Location location) {
        float temperature = location.getForecastSummary().getHourly().getData().get(FIRST).getTemperature();
        tvTemperature.setText(mContext.getString(R.string.single_temperature_field, (int) temperature));
    }
}
