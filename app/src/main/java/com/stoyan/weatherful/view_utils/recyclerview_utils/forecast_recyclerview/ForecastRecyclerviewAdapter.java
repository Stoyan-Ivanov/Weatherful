package com.stoyan.weatherful.view_utils.recyclerview_utils.forecast_recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stoyan.weatherful.R;
import com.stoyan.weatherful.db.Location;
import com.stoyan.weatherful.network.models.forecast_full_models.Data;

import java.util.ArrayList;

/**
 * Created by Stoyan on 27.1.2018 г..
 */

public class ForecastRecyclerviewAdapter extends RecyclerView.Adapter<ForecastViewHolder> {
    private ArrayList<Data> mWeeklyForecast;
    private Location mLocation;

    public ForecastRecyclerviewAdapter(ArrayList<Data> weeklyForecast, Location location) {
        this.mLocation = location;
        this.mWeeklyForecast = weeklyForecast;
    }

    @Override
    public ForecastViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.viewholder_forecast_horizontal, parent, false);

        return new ForecastViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ForecastViewHolder holder, int position) {
        holder.bind(mWeeklyForecast, position, mLocation);
    }

    @Override
    public int getItemCount() {
        return mWeeklyForecast.size();
    }
}
