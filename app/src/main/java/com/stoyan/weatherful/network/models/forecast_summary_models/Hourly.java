package com.stoyan.weatherful.network.models.forecast_summary_models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Hourly)) return false;
        Hourly hourly = (Hourly) o;
        return Objects.equals(getSummary(), hourly.getSummary()) &&
                Objects.equals(getIcon(), hourly.getIcon()) &&
                Objects.equals(getData(), hourly.getData());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getSummary(), getIcon(), getData());
    }
}
