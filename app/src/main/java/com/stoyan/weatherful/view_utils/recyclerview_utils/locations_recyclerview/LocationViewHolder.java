package com.stoyan.weatherful.view_utils.recyclerview_utils.locations_recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.stoyan.weatherful.R;
import com.stoyan.weatherful.db.Location;
import com.stoyan.weatherful.db.LocationsProvider;
import com.stoyan.weatherful.ui.forecast_activity.ForecastActivity;

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

    private LocationsRecyclerViewAdapter adapter;
    private Context context;

    public LocationViewHolder(View itemView, LocationsRecyclerViewAdapter adapter) {
        super(itemView);
        this.context = itemView.getContext();
        this.adapter = adapter;
    }

    public void bind(final Location location) {

        ButterKnife.bind(this, itemView);

        tvLocationName.setText(location.getLocationName());
        setLocationPicture(location.getImageUrl());
        if (location.getForecastSummary() != null) {
            setForecastSummary(location.getForecastSummary().getHourly().getSummary());
        }

        setOnViewHolderClickListeners(location);
    }

    private void setOnViewHolderClickListeners(final Location location) {
        itemView.setOnClickListener(view -> ForecastActivity.start(context, location));

        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                LocationsProvider.getInstance().deleteLocation(location);
                removeItem();
                return false;
            }
        });
    }

    private void removeItem() {
        adapter.getLocations().remove(getAdapterPosition());
        adapter.notifyItemRemoved(getAdapterPosition());
        adapter.notifyItemRangeChanged(getAdapterPosition(), adapter.getLocations().size());
    }

    public void setLocationPicture(String url) {

        if ((itemView != null) && url != null) {

            Glide.with(locationPicture.getContext())
                    .load(url)
                    .centerCrop()
                    .placeholder(R.drawable.cityscape)
                    .into(locationPicture);
        } else {
            throw new NullPointerException("Provide valid image url");
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
            tvTemperature.setText(temp);
        } else {
            throw new NullPointerException("Provide valid tvTemperature");
        }

    }
}
