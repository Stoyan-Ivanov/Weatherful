package com.stoyan.weatherful.network.models.forecast_summary_models;

import java.util.ArrayList;

/**
 * Created by Stoyan on 27.1.2018 г..
 */

public class Hourly {

    private String summary;

    private String icon;

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
