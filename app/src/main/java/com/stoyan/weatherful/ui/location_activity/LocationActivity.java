package com.stoyan.weatherful.ui.location_activity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresPermission;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.stoyan.weatherful.R;
import com.stoyan.weatherful.WeatherfulApplication;
import com.stoyan.weatherful.rx.RxBus;
import com.stoyan.weatherful.rx.RxUtils;
import com.stoyan.weatherful.rx.events.NoInternetAvailableEvent;
import com.stoyan.weatherful.ui.AboutActivity;
import com.stoyan.weatherful.ui.add_location_activity.AddLocationActivity;
import com.stoyan.weatherful.ui.base_ui.activity.BaseActivity;
import com.stoyan.weatherful.ui.forecast_activity.ForecastActivity;
import com.stoyan.weatherful.utils.Constants;
import com.stoyan.weatherful.utils.StartActivityEnum;
import com.stoyan.weatherful.view_utils.recyclerview_utils.decorations.SpacesItemDecoration;
import com.stoyan.weatherful.view_utils.recyclerview_utils.locations_recyclerview.LocationsRecyclerViewAdapter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
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
    private StartActivityEnum mEnum;

    public static Intent getIntent(Context context, StartActivityEnum startActivityEnum) {
        Intent intent = new Intent(context, LocationActivity.class);
        intent.putExtra(Constants.EXTRA_START_ENUM, startActivityEnum);
        return intent;
    }

    @OnClick(R.id.iv_main_location)
    void onCurrentLocationClick() {
        mViewModel.getCurrentLocationWrapper().observe(this,

                wrapper -> {
                    if (wrapper != null) {
                        startActivity(ForecastActivity.getIntent(LocationActivity.this, wrapper.getLocation()));
                    } else {
                        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                            LocationActivityPermissionsDispatcher.getCurrentLocationWithPermissionCheck(this);
                        } else {
                            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        }
                    }
                });
    }

    @OnClick(R.id.fab_add)
    void fabOnClick() {
        startActivity(AddLocationActivity.getIntent(this));
    }

    @OnClick(R.id.tv_try_again_missing_network)
    void tryAgainFieldClicked() {
        startActivity(LocationActivity.getIntent(this, StartActivityEnum.SPLASH_SCREEN));
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
        mEnum = (StartActivityEnum) getIntent().getSerializableExtra(Constants.EXTRA_START_ENUM);

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
                mAdapter.submitList(locationForecastSummaryWrappers);
            }
        });

        configureSplashScreen();
        configureToolbar();
        configureSwipeRefreshLayout();
        loadCurrentLocation();
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkPlayServicesAvailable();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.about_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_about: startActivity(AboutActivity.getIntent(this));
        }

        return super.onOptionsItemSelected(item);
    }

    private void checkPlayServicesAvailable() {
        final GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        final int status = apiAvailability.isGooglePlayServicesAvailable(this);

        if(status != ConnectionResult.SUCCESS) {
            if(apiAvailability.isUserResolvableError(status)) {
                apiAvailability.getErrorDialog(this, status, 1).show();
            }
        }
    }

    @NeedsPermission({Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
    void getCurrentLocation() {
        mViewModel.downloadCurrentLocationData();
        loadCurrentLocation();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        LocationActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @OnShowRationale({Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
    void showRationaleForLocation(final PermissionRequest request) {
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.permission_dialog_title))
                .setMessage(getString(R.string.permission_dialog_message))
                .setPositiveButton(getString(R.string.permission_dialog_accept_button), (dialog, which) -> request.proceed())
                .setNegativeButton(getString(R.string.permission_dialog_deny_button), (dialog, which) -> request.cancel())
                .show();
    }

    @OnPermissionDenied({Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
    void onLocationPermissionDenied() {
        WeatherfulApplication.showToast(getString(R.string.toast_permission_denied));
    }

    @OnNeverAskAgain({Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
    void showNeverAskForLocation() {
        Toast.makeText(this, getString(R.string.toast_permission_never_ask), Toast.LENGTH_SHORT).show();
    }

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

        if(mEnum == null || mEnum.equals(StartActivityEnum.SPLASH_SCREEN)) {
            new Handler().postDelayed(() -> mLayoutLoading.setVisibility(View.GONE), LOADING_SCREEN_TIME);
        } else {
            mLayoutLoading.setVisibility(View.GONE);
        }
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

                    mTvMissingGpsConnection.setVisibility(View.GONE);
                    mImageTouchMissingLocation.setVisibility(View.GONE);
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
