package com.stoyan.weatherful.ui.location_activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
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
import com.stoyan.weatherful.rx.RxBus;
import com.stoyan.weatherful.rx.RxUtils;
import com.stoyan.weatherful.rx.events.NoInternetAvailableEvent;
import com.stoyan.weatherful.ui.add_location_activity.AddLocationActivity;
import com.stoyan.weatherful.ui.base_ui.activity.BaseActivity;
import com.stoyan.weatherful.ui.forecast_activity.ForecastActivity;
import com.stoyan.weatherful.view_utils.recyclerview_utils.decorations.SpacesItemDecoration;
import com.stoyan.weatherful.view_utils.recyclerview_utils.locations_recyclerview.LocationsRecyclerViewAdapter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class LocationActivity extends BaseActivity<LocationActivityViewModel> {

    @BindView(R.id.recyclerview) RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh) SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.progressBar_loading) ProgressBar mProgressBar;
    @BindView(R.id.layout_loading) ConstraintLayout mLayoutLoading;
    @BindView(R.id.layout_locations) ConstraintLayout mLayoutLocations;
    @BindView(R.id.layout_missing_network) ConstraintLayout mLayoutMissingNetwork;
    @BindView(R.id.toolbar_locations) Toolbar mToolbar;

    @BindView(R.id.iv_main_location) ImageView mCurrentLocationImage;
    @BindView(R.id.tv_main_location_name) TextView mTvCurrentLocationName;
    @BindView(R.id.tv_main_location_temperature) TextView mTvCurrentLocationTemperature;
    @BindView(R.id.tv_current_location_summary) TextView mTvCurrentLocationSummary;

    @BindView(R.id.tv_missing_gps) TextView mTvMissingGpsConnection;
    @BindView(R.id.iv_touch_missing_location) ImageView mImageTouchMissingLocation;

    @Inject
    RxBus mRxBus;
    private LocationsRecyclerViewAdapter mAdapter;

    public static Intent getIntent(Context context) {
        return new Intent(context, LocationActivity.class);
    }

    @OnClick(R.id.iv_main_location)
    void onCurrentLocationClick() {
        mViewModel.getCurrentLocationWrapper().observe(this,
                wrapper -> {
                    if (wrapper != null) {
                        startActivity(ForecastActivity.getIntent(LocationActivity.this, wrapper.getLocation()));
                    } else {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(intent);
                    }
                });
    }

    @OnClick(R.id.fab_add)
    void fabOnClick() {
        startActivity(AddLocationActivity.getIntent(this));
    }

    @OnClick(R.id.tv_try_again_missing_network)
    void tryAgainFieldClicked() {
        startActivity(LocationActivity.getIntent(this));
        finish();
    }

    @Override
    protected void inject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locations);

        mViewModel = new LocationActivityViewModel();

        subscribeToEventBus();
        mViewModel.downloadData();
        mViewModel.downloadCurrentLocationData();

        mViewModel.getLocationForecastWrappers().observe(this, locationForecastSummaryWrappers -> {
            if(mRecyclerView.getAdapter() == null) {
                mRecyclerView.setLayoutManager(new LinearLayoutManager(LocationActivity.this, LinearLayoutManager.HORIZONTAL, false));
                mAdapter = (new LocationsRecyclerViewAdapter(mViewModel, locationForecastSummaryWrappers));
                mRecyclerView.setAdapter(mAdapter);
                mRecyclerView.addItemDecoration(new SpacesItemDecoration(getResources()
                        .getInteger(R.integer.viewholder_forecast_margin), SpacesItemDecoration.HORIZONTAL));
            } else {
                mAdapter.updateItems(locationForecastSummaryWrappers);
            }
        });

        configureSplashScreen();
        configureToolbar();
        configureSwipeRefreshLayout();
        loadCurrentLocation();
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//
//        new Handler().postDelayed(() -> {
//            mViewModel.downloadCurrentLocationData();
//            loadCurrentLocation();
//        }, 1000);
//
//    }

    private void subscribeToEventBus() {
        addDisposable(mRxBus.toObservable()
                .compose(RxUtils.applySchedulersObservable())
                .subscribe(event -> {
                    if (event instanceof NoInternetAvailableEvent) {
                        showNoInternetView();
                    }
                }));
    }

    private void configureSwipeRefreshLayout() {
        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            mViewModel.getLocationForecastWrappers();
            if(mSwipeRefreshLayout.isRefreshing()) {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void configureSplashScreen() {
        final int LOADING_SCREEN_TIME = 2000;
        new Handler().postDelayed(() -> mLayoutLoading.setVisibility(View.GONE), LOADING_SCREEN_TIME);
    }

    private void configureToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(R.string.location_activity_header);
    }

    public void showNoInternetView() {
        if(mLayoutLocations.getVisibility() == View.VISIBLE) {
            mLayoutLocations.setVisibility(View.GONE);
            mLayoutMissingNetwork.setVisibility(View.VISIBLE);
        }
    }

    public void loadCurrentLocation() {
        mViewModel.getCurrentLocationWrapper().observe(this, locationForecastSummaryWrapper -> {
            if (locationForecastSummaryWrapper != null) {
                if(locationForecastSummaryWrapper.getForecastSummaryResponse() != null
                        && locationForecastSummaryWrapper.getLocation() != null) {

                    loadCurrentLocationName(locationForecastSummaryWrapper.getLocation().getLocationName());
                    loadCurrentLocationTemperature((int) locationForecastSummaryWrapper.getForecastSummaryResponse().getHourly().getData().get(0).getTemperature());
                    loadCurrentLocationForecastSummary(locationForecastSummaryWrapper.getForecastSummaryResponse().getHourly().getSummary());
                    loadCurrentLocationImage(locationForecastSummaryWrapper.getLocation().getLocationImageFull());
                }
            } else {
                mTvMissingGpsConnection.setVisibility(View.VISIBLE);
                mImageTouchMissingLocation.setVisibility(View.VISIBLE);
            }
        });
    }

    private void loadCurrentLocationImage(String imageUrl) {
        Glide.with(this)
                .load(imageUrl)
                .centerCrop()
                .into(mCurrentLocationImage);
    }

    private void loadCurrentLocationTemperature(int temperature) {
        mTvCurrentLocationTemperature.setText(getString(R.string.single_temperature_field, temperature));
    }

    private void loadCurrentLocationName(String locationName) {
        mTvCurrentLocationName.setText(locationName);
    }

    private void loadCurrentLocationForecastSummary(String forecastSummary) {
        mTvCurrentLocationSummary.setText(forecastSummary);
    }
}
