package com.stoyan.weatherful.network.models.forecast_full_models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Stoyan on 28.1.2018 г..
 */

public class Daily {
    @SerializedName("icon")
    private String icon;
    @SerializedName("data")
    private Data[] data;

    public String getIcon ()
    {
        return icon;
    }

    public Data[] getData ()
    {
        return data;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [summary =  icon = "+icon+", data = "+data+"]";
    }
}
