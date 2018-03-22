package com.stoyan.weatherful.view_utils.recyclerview_utils.locations_recyclerview;

import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stoyan.weatherful.R;
import com.stoyan.weatherful.db.Location;
import com.stoyan.weatherful.network.WeatherfulApplication;
import com.stoyan.weatherful.ui.location_activity.LocationActivity;
import com.stoyan.weatherful.ui.location_activity.LocationsViewModel;

import java.util.ArrayList;


/**
 * Created by Stoyan on 27.1.2018 г..
 */

public class LocationsRecyclerViewAdapter extends RecyclerView.Adapter<LocationViewHolder> {
    private ArrayList<Location> locations = new ArrayList<>();
    private ArrayList<String> imageUrls = new ArrayList<>();

    public LocationsRecyclerViewAdapter(LocationActivity locationActivity, LocationsViewModel locationsViewModel, ArrayList<Location> locations) {
        this.locations = locations;

        Observer<ArrayList<String>> imageUrlObserver = new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(@Nullable ArrayList<String> strings) {
               imageUrls = strings;
               notifyDataSetChanged();
            }};

        locationsViewModel.getImageUrls().observe(locationActivity, imageUrlObserver);
    }

    @Override
    public LocationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.location_viewholder, parent, false);

        return new LocationViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(final LocationViewHolder holder, final int position) {
        holder.bind(locations.get(position));

        Log.d("SII", "onBindViewHolder: " + imageUrls.size());
        holder.setLocationPicture(imageUrls.get(position));

        //presenter.getForecastSummary(holder, locations.get(position));
    }

    @Override
    public int getItemCount() {
        return locations.size();
    }

     ArrayList<Location> getLocations() {
        return locations;
    }
}
