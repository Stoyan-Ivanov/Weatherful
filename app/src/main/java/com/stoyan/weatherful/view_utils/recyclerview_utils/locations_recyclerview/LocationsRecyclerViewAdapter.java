package com.stoyan.weatherful.view_utils.recyclerview_utils.locations_recyclerview;

import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stoyan.weatherful.R;
import com.stoyan.weatherful.persistence.models.Location;
import com.stoyan.weatherful.persistence.models.LocationForecastSummaryWrapper;
import com.stoyan.weatherful.persistence.models.LWWrapperDiffCallback;
import com.stoyan.weatherful.ui.location_activity.LocationActivityViewModel;

import java.util.ArrayList;


/**
 * Created by Stoyan on 27.1.2018 г..
 */

public class LocationsRecyclerViewAdapter extends RecyclerView.Adapter<LocationViewHolder> {
    private ArrayList<LocationForecastSummaryWrapper> mLocationForecastSummaryWrappers;
    private LocationActivityViewModel mViewModel;

    public LocationsRecyclerViewAdapter(LocationActivityViewModel viewModel,
                                        ArrayList<LocationForecastSummaryWrapper> locationForecastSummaryWrappers) {
        this.mViewModel = viewModel;
        this.mLocationForecastSummaryWrappers = locationForecastSummaryWrappers;
    }

    @Override
    public LocationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.viewholder_location_horizontal, parent, false);

        return new LocationViewHolder(view, this);
    }

    public void onBindViewHolder(LocationViewHolder holder, int position) {
        holder.bind(mLocationForecastSummaryWrappers.get(position));
    }

    public void updateItems(ArrayList<LocationForecastSummaryWrapper> newItems) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(
                new LWWrapperDiffCallback(mLocationForecastSummaryWrappers, newItems));
        diffResult.dispatchUpdatesTo(this);

        mLocationForecastSummaryWrappers.clear();
        mLocationForecastSummaryWrappers.addAll(newItems);
    }

    @Override
    public int getItemCount() {
        return mLocationForecastSummaryWrappers.size();
    }

    public void removeItem(int position, Location location) {
        mViewModel.deleteLocation(location);
        mLocationForecastSummaryWrappers.remove(position);
        notifyItemRemoved(position);
    }
}
