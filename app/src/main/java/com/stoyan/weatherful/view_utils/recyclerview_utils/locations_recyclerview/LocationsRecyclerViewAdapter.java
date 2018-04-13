package com.stoyan.weatherful.view_utils.recyclerview_utils.locations_recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stoyan.weatherful.R;
import com.stoyan.weatherful.db.Location;


import java.util.ArrayList;


/**
 * Created by Stoyan on 27.1.2018 г..
 */

public class LocationsRecyclerViewAdapter extends RecyclerView.Adapter<LocationViewHolder> {
    private ArrayList<Location> mLocations = new ArrayList<>();

    public LocationsRecyclerViewAdapter(ArrayList<Location> locations) {
        this.mLocations = locations;
    }

    @Override
    public LocationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.viewholder_location, parent, false);

        return new LocationViewHolder(view, this);
    }

    public void onBindViewHolder(LocationViewHolder holder, int position) {
        final Location location = mLocations.get(position);
        holder.bind(location);
    }

    @Override
    public int getItemCount() {
        return mLocations.size();
    }

    public void removeItem(int position) {
        mLocations.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mLocations.size());
    }
}
