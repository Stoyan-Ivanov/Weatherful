package com.stoyan.weatherful.forecast_pager_activity;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.stoyan.weatherful.R;

import butterknife.BindView;

public class ForecastPagerActivity extends AppCompatActivity {

    @BindView(R.id.view_pager)
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast_pager);

        
    }
}
