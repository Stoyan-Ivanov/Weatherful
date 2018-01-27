package com.stoyan.weatherful.view_utils.recyclerview_utils.locations_recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.stoyan.weatherful.R;
import com.stoyan.weatherful.models.Location;

import org.w3c.dom.Text;

import butterknife.BindView;

/**
 * Created by Stoyan on 27.1.2018 г..
 */

public class LocationViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.tv_location_name)
    TextView locationName;

    @BindView(R.id.tv_location_summary)
    TextView forecastSummary;

    @BindView(R.id.tv_location_temperature)
    TextView temperature;


    public LocationViewHolder(View itemView) {
        super(itemView);
    }

    public void bind(final Location location, final OnItemClickListener onItemClickListener) {

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.OnItemClick(location);
            }
        });

        locationName.setText(location.getLocationName());

    }
}
