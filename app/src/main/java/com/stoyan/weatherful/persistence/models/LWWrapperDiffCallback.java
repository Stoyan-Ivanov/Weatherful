package com.stoyan.weatherful.persistence.models;

import android.support.v7.util.DiffUtil;

import java.util.ArrayList;

public class LWWrapperDiffCallback extends DiffUtil.Callback{
    private ArrayList<LocationForecastSummaryWrapper> mOldList;
    private ArrayList<LocationForecastSummaryWrapper> mNewList;

    public LWWrapperDiffCallback(ArrayList<LocationForecastSummaryWrapper> oldList, ArrayList<LocationForecastSummaryWrapper> newList) {
        mOldList = oldList;
        mNewList = newList;
    }

    @Override
    public int getOldListSize() {
        return mOldList.size();
    }

    @Override
    public int getNewListSize() {
        return mNewList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return mOldList.get(oldItemPosition).equals(mNewList.get(newItemPosition));
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {

        LocationForecastSummaryWrapper oldItem = mOldList.get(oldItemPosition);
        LocationForecastSummaryWrapper newItem = mNewList.get(newItemPosition);

        if(oldItem.getLocation().equals(newItem.getLocation())) {
            if(oldItem.getForecastSummaryResponse().equals(newItem.getForecastSummaryResponse())) {
                return true;
            }
        }
        return false;
    }
}
