package com.stoyan.weatherful.network.network_models.forecast_summary_models;

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

    public void setSummary (String summary)
    {
        this.summary = summary;
    }

    public String getIcon ()
    {
        return icon;
    }

    public void setIcon (String icon)
    {
        this.icon = icon;
    }

    public ArrayList<Data> getData ()
    {
        return data;
    }

    public void setData (ArrayList<Data> data)
    {
        this.data = data;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [summary = "+summary+", icon = "+icon+", data = "+data+"]";
    }
}
