package com.stoyan.weatherful.ui.forecast_pager_activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.widget.TextView;

import com.stoyan.weatherful.Constants;
import com.stoyan.weatherful.R;
import com.stoyan.weatherful.db.Location;
import com.stoyan.weatherful.network.models.forecast_full_models.Data;
import com.stoyan.weatherful.ui.base_ui.BaseActivity;
import com.stoyan.weatherful.view_utils.CustomPagerAdapter;

import java.util.ArrayList;

import butterknife.BindView;

public class ForecastPagerActivity extends BaseActivity  {

    @BindView(R.id.ctv_header) TextView header;
    @BindView(R.id.view_pager) ViewPager viewPager;

    private ForecastPagerActivityPresenter presenter;

    public static Intent getIntent(Context context, Location location, ArrayList<Data> data, int position) {
        Intent intent = new Intent(context, ForecastPagerActivity.class);
        intent.putExtra(Constants.EXTRA_LOCATION, location);
        intent.putExtra(Constants.EXTRA_DATA, data);
        intent.putExtra(Constants.EXTRA_POSITION, position);

        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast_pager);

        presenter = new ForecastPagerActivityPresenter(getIntent());

        header.setText(presenter.getHeader());

        viewPager.setAdapter(new CustomPagerAdapter(getSupportFragmentManager(), presenter.getFragments()));
        viewPager.setCurrentItem(presenter.getDefaultPosition());
        viewPager.setOffscreenPageLimit(presenter.getOffScreenLimit());
    }

}
