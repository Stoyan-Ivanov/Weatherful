package com.stoyan.weatherful.view_utils.recyclerview_utils.locations_recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stoyan.weatherful.R;
import com.stoyan.weatherful.persistence.models.Location;
import com.stoyan.weatherful.persistence.models.LocationForecastSummaryWrapper;
import com.stoyan.weatherful.ui.location_activity.LocationActivityPresenter;

import java.util.ArrayList;


/**
 * Created by Stoyan on 27.1.2018 г..
 */

public class LocationsRecyclerViewAdapter extends RecyclerView.Adapter<LocationViewHolder> {
    private ArrayList<LocationForecastSummaryWrapper> mLocationForecastSummaryWrappers = new ArrayList<>();
    private LocationActivityPresenter mPresenter;

    public LocationsRecyclerViewAdapter(LocationActivityPresenter presenter) {
        this.mPresenter = presenter;
        this.mLocationForecastSummaryWrappers = presenter.getLocationForecastWrappers();
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

    @Override
    public int getItemCount() {
        return mLocationForecastSummaryWrappers.size();
    }

    public void removeItem(int position, Location location) {
        mPresenter.deleteLocation(location);
        mLocationForecastSummaryWrappers.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mLocationForecastSummaryWrappers.size());
    }
}
