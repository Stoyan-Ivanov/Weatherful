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
    private ArrayList<Data> weeklyForecast;
    private Location location;

    public ForecastRecyclerviewAdapter(ArrayList<Data> weeklyForecast, Location location) {
        this.location = location;
        this.weeklyForecast = weeklyForecast;
    }

    @Override
    public ForecastViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.viewholder_forecast, parent, false);

        return new ForecastViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ForecastViewHolder holder, int position) {
        holder.bind(weeklyForecast, position, location);
    }

    @Override
    public int getItemCount() {
        return weeklyForecast.size();
    }
}
