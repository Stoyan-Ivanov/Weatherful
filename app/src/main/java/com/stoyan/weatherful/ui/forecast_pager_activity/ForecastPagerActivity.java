package com.stoyan.weatherful.ui.forecast_pager_activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.rd.PageIndicatorView;
import com.stoyan.weatherful.Constants;
import com.stoyan.weatherful.R;
import com.stoyan.weatherful.db.Location;
import com.stoyan.weatherful.network.models.forecast_full_models.Data;
import com.stoyan.weatherful.ui.base_ui.activity.BaseActivity;
import com.stoyan.weatherful.view_utils.CustomPagerAdapter;

import java.util.ArrayList;

import butterknife.BindView;

public class ForecastPagerActivity extends BaseActivity  {

    @BindView(R.id.toolbar_fragment_pager) Toolbar titleBar;
    @BindView(R.id.view_pager) ViewPager viewPager;
    @BindView(R.id.pageIndicatorView)
    PageIndicatorView pageIndicatorView;


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

        presenter = new ForecastPagerActivityPresenter();
        presenter.setExtras(getIntent());

        configureToolbar();
        configureViewPager();
    }

    private void configureToolbar() {
        setSupportActionBar(titleBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        titleBar.setNavigationOnClickListener(v -> finish());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getSupportActionBar().setTitle(presenter.getHeader());
        }
    }

    private void configureViewPager() {
        viewPager.setAdapter(new CustomPagerAdapter(getSupportFragmentManager(), presenter.getFragments()));
        viewPager.setCurrentItem(presenter.getDefaultPosition());
        viewPager.setOffscreenPageLimit(presenter.getOffScreenLimit());
        pageIndicatorView.setCount(presenter.getFragments().size());

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageSelected(int position) {
                pageIndicatorView.setSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {}
        });
    }
}
