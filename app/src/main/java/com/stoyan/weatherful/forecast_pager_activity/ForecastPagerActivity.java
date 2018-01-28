package com.stoyan.weatherful.forecast_pager_activity;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.stoyan.weatherful.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ForecastPagerActivity extends AppCompatActivity {

    @BindView(R.id.ctv_header)
    TextView header;

    @BindView(R.id.view_pager)
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast_pager);

        ButterKnife.bind(this);

        ForecastPagerActivityPresenter presenter = new ForecastPagerActivityPresenter(getIntent(), this);

        header.setText(presenter.getHeader());

        viewPager.setAdapter(presenter.getPagerAdapter());
        viewPager.setCurrentItem(presenter.getDefaultPosition());
        viewPager.setOffscreenPageLimit(presenter.getOffScreenLimit());
    }
}
