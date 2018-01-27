package com.stoyan.weatherful.network.network_models.forecast_summary_models;

/**
 * Created by Stoyan on 27.1.2018 г..
 */

public class Data {
    private String summary;

    private String windGust;

    private String icon;

    private String pressure;

    private String visibility;

    private String cloudCover;

    private String apparentTemperature;

    private String precipType;

    private String precipIntensity;

    private String temperature;

    private String dewPoint;

    private String ozone;

    private String time;

    private String windSpeed;

    private String humidity;

    private String windBearing;

    private String uvIndex;

    private String precipProbability;

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getWindGust() {
        return windGust;
    }

    public void setWindGust(String windGust) {
        this.windGust = windGust;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

}
