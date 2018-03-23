package com.stoyan.weatherful.view_utils.recyclerview_utils.locations_recyclerview;

import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stoyan.weatherful.R;
import com.stoyan.weatherful.db.Location;
import com.stoyan.weatherful.ui.location_activity.LocationActivity;


import java.util.ArrayList;


/**
 * Created by Stoyan on 27.1.2018 г..
 */

public class LocationsRecyclerViewAdapter extends RecyclerView.Adapter<LocationViewHolder> {
    private ArrayList<Location> locations = new ArrayList<>();

    private ArrayList<String> imageUrls = new ArrayList<>();

    public LocationsRecyclerViewAdapter(LocationActivity locationActivity, ArrayList<Location> locations) {}
    private LayoutInflater inflater;

    public LocationsRecyclerViewAdapter(ArrayList<Location> locations) {
        this.locations = locations;
    }

    @Override
    public LocationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.location_viewholder, parent, false);

        return new LocationViewHolder(view, this);
    }

    public void onBindViewHolder(LocationViewHolder holder, int position) {
        final Location location = locations.get(position);
        holder.bind(location);
    }

    @Override
    public int getItemCount() {
        return locations.size();
    }

     ArrayList<Location> getLocations() {
        return locations;
    }
}
