package com.stoyan.weatherful.view_utils.recyclerview_utils.locations_recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.stoyan.weatherful.models.Location;

/**
 * Created by Stoyan on 27.1.2018 г..
 */

public class LocationViewHolder extends RecyclerView.ViewHolder {
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
    }
}
