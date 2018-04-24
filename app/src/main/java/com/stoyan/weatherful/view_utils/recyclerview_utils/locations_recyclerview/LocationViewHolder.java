package com.stoyan.weatherful.view_utils.recyclerview_utils.locations_recyclerview;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.stoyan.weatherful.R;
import com.stoyan.weatherful.persistence.models.Location;
import com.stoyan.weatherful.persistence.models.LocationForecastSummaryWrapper;
import com.stoyan.weatherful.network.models.forecast_summary_models.ForecastSummaryResponse;
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

    public void bind(final LocationForecastSummaryWrapper locationForecastSummaryWrapper) {

        tvLocationName.setText(locationForecastSummaryWrapper.getLocation().getLocationName());
        setLocationPicture(locationForecastSummaryWrapper.getLocation().getLocationImageThumbnail());
        setTemperature(locationForecastSummaryWrapper.getForecastSummaryResponse());

        setOnViewHolderClickListeners(locationForecastSummaryWrapper.getLocation());
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

    public void setTemperature(ForecastSummaryResponse forecastSummaryResponse) {
        float temperature = forecastSummaryResponse.getHourly().getData().get(FIRST).getTemperature();
        tvTemperature.setText(mContext.getString(R.string.single_temperature_field, (int) temperature));
    }
}
