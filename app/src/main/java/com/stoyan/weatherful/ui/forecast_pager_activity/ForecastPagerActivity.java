package com.stoyan.weatherful.ui.forecast_pager_activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.rd.PageIndicatorView;
import com.stoyan.weatherful.Constants;
import com.stoyan.weatherful.R;
import com.stoyan.weatherful.network.models.forecast_full_models.Data;
import com.stoyan.weatherful.persistence.models.Location;
import com.stoyan.weatherful.ui.base_ui.activity.BaseActivity;
import com.stoyan.weatherful.view_utils.CustomPagerAdapter;

import java.util.ArrayList;

import butterknife.BindView;

public class ForecastPagerActivity extends BaseActivity<ForecastPagerActivityViewModel>  {

    @BindView(R.id.toolbar_fragment_pager) Toolbar mToolbar;
    @BindView(R.id.view_pager) ViewPager mViewPager;
    @BindView(R.id.pageIndicatorView) PageIndicatorView mPageIndicatorView;

    private ForecastPagerActivityViewModel mViewModel;

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

        mViewModel = new ForecastPagerActivityViewModel();
        mViewModel.setExtras(getIntent());

        configureToolbar();
        configureViewPager();
    }

    private void configureToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setNavigationOnClickListener(v -> finish());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mViewModel.getHeader().observe(this, title -> getSupportActionBar().setTitle(title));
        }
    }

    private void configureViewPager() {
        mViewModel.getData().observe(this, data -> {
            ArrayList<Fragment> fragments =  mViewModel.getFragments(data);
            int defaultPosition = mViewModel.getDefaultPosition();

            mViewPager.setAdapter(new CustomPagerAdapter(getSupportFragmentManager(), fragments));
            mViewPager.setCurrentItem(defaultPosition);
            mViewPager.setOffscreenPageLimit(mViewModel.getOffScreenLimit());

            mPageIndicatorView.setCount(fragments.size());
            mPageIndicatorView.setSelected(defaultPosition);
        });



        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageSelected(int position) {
                mPageIndicatorView.setSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {}
        });
    }
}
