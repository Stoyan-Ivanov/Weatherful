package com.stoyan.weatherful.network.models.forecast_summary_models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Stoyan on 27.1.2018 г..
 */

public class Hourly {
    @SerializedName("summary")
    private String summary;
    @SerializedName("icon")
    private String icon;
    @SerializedName("data")
    private ArrayList<Data> data;

    public String getSummary ()
    {
        return summary;
    }

    public String getIcon ()
    {
        return icon;
    }

    public ArrayList<Data> getData ()
    {
        return data;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [summary = "+summary+", icon = "+icon+", data = "+data+"]";
    }
}
