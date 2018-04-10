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
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.crashlytics.android.Crashlytics;
import com.stoyan.weatherful.R;
import com.stoyan.weatherful.ui.add_location_activity.AddLocationActivity;
import com.stoyan.weatherful.ui.base_ui.activity.BaseActivity;
import com.stoyan.weatherful.view_utils.recyclerview_utils.locations_recyclerview.LocationsRecyclerViewAdapter;

import butterknife.BindView;
import butterknife.OnClick;
import io.fabric.sdk.android.Fabric;

public class LocationActivity extends BaseActivity<LocationActivityPresenter> implements LocationActivityContract{


    @BindView(R.id.recyclerview) RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh) SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.progressBar_loading) ProgressBar loadingBar;
    @BindView(R.id.layout_loading) ConstraintLayout layoutLoading;
    @BindView(R.id.layout_locations) RelativeLayout layoutLocations;
    @BindView(R.id.layout_missing_network) ConstraintLayout layoutMissingNetwork;
    @BindView(R.id.toolbar_locations) Toolbar toolbar;

    private static int LOADING_SCREEN_TIME = 2000;

    public static Intent getIntent(Context context) {
        return new Intent(context, LocationActivity.class);
    }

    @OnClick(R.id.fab_add)
    void fabOnClick() {
        presenter.fabOnClick();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_locations);

        presenter = new LocationActivityPresenter(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new LocationsRecyclerViewAdapter(presenter.getLocations()));

        presenter.downloadData();
        configureSplashScreen();
        configureToolbar();
        configureSwipeRefreshLayout();
    }

    private void configureSwipeRefreshLayout() {
        swipeRefreshLayout.setOnRefreshListener(() -> {
            presenter.downloadData();
            if(swipeRefreshLayout.isRefreshing()) {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void configureSplashScreen() {
        new Handler().postDelayed(() -> layoutLoading.setVisibility(View.GONE), LOADING_SCREEN_TIME);
    }

    private void configureToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.location_activity_header);
    }

    @Override
    public void notifyDataSetChanged() {
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void showError(Throwable throwable) {
        Log.d("SII", "showError: " + throwable.getMessage());
    }

    @Override
    public void startNewActivity() {
        startActivity(AddLocationActivity.getIntent(this));
    }

    @Override
    public void showNoInternetView() {
        if(layoutLocations.getVisibility() == View.VISIBLE) {
            layoutLocations.setVisibility(View.GONE);
            layoutMissingNetwork.setVisibility(View.VISIBLE);
        }
    }
}
