package com.stoyan.weatherful.network.models.forecast_full_models;

/**
 * Created by Stoyan on 28.1.2018 г..
 */

public class Daily {

    private String icon;

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
