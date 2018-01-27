package com.stoyan.weatherful.view_utils.recyclerview_utils.locations_recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stoyan.weatherful.R;
import com.stoyan.weatherful.models.Location;

import java.util.ArrayList;

/**
 * Created by Stoyan on 27.1.2018 г..
 */

public class LocationsRecyclerviewAdapter extends RecyclerView.Adapter<LocationViewHolder> {
    private ArrayList<Location> locations = new ArrayList<>();
    private OnItemClickListener onItemClickListener;
    private LayoutInflater inflater;

    public LocationsRecyclerviewAdapter(ArrayList<Location> locations, OnItemClickListener onItemClickListener) {
        this.locations = locations;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public LocationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.from(parent.getContext())
                .inflate(R.layout.location_viewholder, parent, false);

        return new LocationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LocationViewHolder holder, int position) {
        holder.bind(locations.get(position), onItemClickListener);
    }

    @Override
    public int getItemCount() {
        return locations.size();
    }
}
