package com.stoyan.weatherful.ui.location_activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.stoyan.weatherful.R;
import com.stoyan.weatherful.persistence.models.Location;
import com.stoyan.weatherful.ui.add_location_activity.AddLocationActivity;
import com.stoyan.weatherful.ui.base_ui.activity.BaseActivity;
import com.stoyan.weatherful.ui.forecast_activity.ForecastActivity;
import com.stoyan.weatherful.view_utils.recyclerview_utils.decorations.SpacesItemDecoration;
import com.stoyan.weatherful.view_utils.recyclerview_utils.locations_recyclerview.LocationsRecyclerViewAdapter;

import butterknife.BindView;
import butterknife.OnClick;

public class LocationActivity extends BaseActivity<LocationActivityPresenter> implements LocationActivityContract{


    @BindView(R.id.recyclerview) RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh) SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.progressBar_loading) ProgressBar mProgressBar;
    @BindView(R.id.layout_loading) ConstraintLayout mLayoutLoading;
    @BindView(R.id.layout_locations) ConstraintLayout mLayoutLocations;
    @BindView(R.id.layout_missing_network) ConstraintLayout mLayoutMissingNetwork;
    @BindView(R.id.toolbar_locations) Toolbar mToolbar;

    @BindView(R.id.iv_main_location) ImageView mMainLocationImage;
    @BindView(R.id.tv_main_location_name) TextView mTvMainLocationName;
    @BindView(R.id.tv_main_location_temperature) TextView mTvMainLocationTemperature;
    @BindView(R.id.tv_main_location_summary) TextView mTvMainLocationSummary;

    private static int LOADING_SCREEN_TIME = 2000;

    public static Intent getIntent(Context context) {
        return new Intent(context, LocationActivity.class);
    }

    @OnClick(R.id.iv_main_location)
    void onCurrentLocationClick() {
        presenter.onCurrentLocationClicked();
    }

    @OnClick(R.id.fab_add)
    void fabOnClick() {
        presenter.fabOnClick();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locations);

        presenter = new LocationActivityPresenter();
        presenter.setView(this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mRecyclerView.setAdapter(new LocationsRecyclerViewAdapter(presenter));
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(getResources()
                .getInteger(R.integer.viewholder_forecast_margin), SpacesItemDecoration.HORIZONTAL));

        presenter.getCurrentLocation();
        presenter.downloadData();
        configureSplashScreen();
        configureToolbar();
        configureSwipeRefreshLayout();
    }

    private void configureSwipeRefreshLayout() {
        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            presenter.downloadData();
            if(mSwipeRefreshLayout.isRefreshing()) {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void configureSplashScreen() {
        new Handler().postDelayed(() -> mLayoutLoading.setVisibility(View.GONE), LOADING_SCREEN_TIME);
    }

    private void configureToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(R.string.location_activity_header);
    }

    @Override
    public void notifyDataSetChanged() {
        mRecyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void showError(Throwable throwable) {
        Log.d("SII", "showError: Location Activity" + throwable.getMessage());
    }

    @Override
    public void startNewActivity() {
        startActivity(AddLocationActivity.getIntent(this));
    }

    @Override
    public void showNoInternetView() {
        if(mLayoutLocations.getVisibility() == View.VISIBLE) {
            mLayoutLocations.setVisibility(View.GONE);
            mLayoutMissingNetwork.setVisibility(View.VISIBLE);
        }
    }

    public void loadCurrentLocation() {
        loadCurrentLocationName(presenter.getCurrentLocationName());
        loadCurrentLocationTemperature(presenter.getCurrentLocationTemperature());
        loadCurrentLocationForecastSummary(presenter.getMainLocationForecastSummary());
        loadCurrentLocationImage(presenter.getMainLocationImageUrl());
    }

    private void loadCurrentLocationImage(String imageUrl) {
        Glide.with(this)
                .load(imageUrl)
                .centerCrop()
                .into(mMainLocationImage);
    }

    private void loadCurrentLocationTemperature(int temperature) {
        mTvMainLocationTemperature.setText(getString(R.string.single_temperature_field, temperature));
    }

    private void loadCurrentLocationName(String locationName) {
        mTvMainLocationName.setText(locationName);
    }

    private void loadCurrentLocationForecastSummary(String forecastSummary) {
        mTvMainLocationSummary.setText(forecastSummary);
    }

    @Override
    public void startNewForecastActivity(Location location) {
        startActivity(ForecastActivity.getIntent(this, location));
    }
}
