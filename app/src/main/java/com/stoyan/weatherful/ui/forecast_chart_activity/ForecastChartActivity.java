package com.stoyan.weatherful.ui.forecast_chart_activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.stoyan.weatherful.Constants;
import com.stoyan.weatherful.R;
import com.stoyan.weatherful.network.models.forecast_full_models.Data;
import com.stoyan.weatherful.ui.base_ui.activity.BaseActivity;
import com.stoyan.weatherful.ui.forecast_activity.ForecastActivity;

import java.util.ArrayList;

import butterknife.BindView;

public class ForecastChartActivity extends BaseActivity {
    @BindView(R.id.line_chart) LineChart mLineChart;

    public static Intent getIntent(Context context, ArrayList<Data> data) {
        Intent intent = new Intent(context, ForecastActivity.class);

        intent.putParcelableArrayListExtra(Constants.EXTRA_DATA, data);
        return intent;
    }

    @Override
    protected void inject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast_chart);
        configureLineChart();
    }

    private void configureLineChart() {
        Legend legend = mLineChart.getLegend();
        legend.setForm(Legend.LegendForm.LINE);
    }


}
