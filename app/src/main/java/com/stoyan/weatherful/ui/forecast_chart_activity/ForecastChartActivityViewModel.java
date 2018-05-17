package com.stoyan.weatherful.ui.forecast_chart_activity;

import android.os.Bundle;

import com.github.mikephil.charting.data.Entry;
import com.stoyan.weatherful.network.models.forecast_full_models.Data;
import com.stoyan.weatherful.viewmodel.BaseViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class ForecastChartActivityViewModel extends BaseViewModel {

    @Override
    protected void inject() {
        getViewModelComponent().inject(this);
    }

    public ArrayList<Entry> getXCoordinateVals(ArrayList<Data> data) {
        ArrayList<Entry> xVals = new ArrayList<>();
        for(int i = 1; i <= data.size(); i++) {
//            xVals.add(new Entry(i, ));
        }
        return xVals;
    }

//    public ArrayList<Entry> getXCoordinateLabels(ArrayList<Data> data) {
//        ArrayList<Entry> xLabels = new ArrayList<>();
//        for(Data singleDataElement: data) {
//            xLabels.add(getDayOfTheWeekFromUnixTime(singleDataElement.getTime()));
//        }
//    }

//    private Entry getDayOfTheWeekFromUnixTime(String time) {
//        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
//        Date dateFormat = new java.util.Date(Long.valueOf(mData.getTime()) * 1000);
//        return sdf.format(dateFormat);
//    }
}
