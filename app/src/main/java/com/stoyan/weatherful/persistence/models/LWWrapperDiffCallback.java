package com.stoyan.weatherful.persistence.models;

import android.support.v7.util.DiffUtil;

import java.util.ArrayList;

public class LWWrapperDiffCallback extends DiffUtil.ItemCallback<LocationForecastSummaryWrapper>{
    private static LWWrapperDiffCallback mDiffCallback;

    public static LWWrapperDiffCallback getInstance() {
        if(mDiffCallback == null) {
            mDiffCallback = new LWWrapperDiffCallback();
        }
        return mDiffCallback;
    }

    @Override
    public boolean areItemsTheSame(LocationForecastSummaryWrapper oldItem, LocationForecastSummaryWrapper newItem) {
        return oldItem.getLocation().toString().equals(newItem.getLocation().toString());
    }

    @Override
    public boolean areContentsTheSame(LocationForecastSummaryWrapper oldItem, LocationForecastSummaryWrapper newItem) {
        if(oldItem.getLocation().equals(newItem.getLocation())) {
            if(oldItem.getForecastSummaryResponse().equals(newItem.getForecastSummaryResponse())) {
                return true;
            }
        }
        return false;
    }
}
