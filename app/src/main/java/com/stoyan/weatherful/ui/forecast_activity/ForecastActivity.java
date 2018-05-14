package com.stoyan.weatherful.ui.forecast_activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.stoyan.weatherful.Constants;
import com.stoyan.weatherful.R;
import com.stoyan.weatherful.network.models.forecast_full_models.Data;
import com.stoyan.weatherful.persistence.models.Location;
import com.stoyan.weatherful.rx.RxBus;
import com.stoyan.weatherful.rx.RxUtils;
import com.stoyan.weatherful.rx.events.NoInternetAvailableEvent;
import com.stoyan.weatherful.ui.base_ui.activity.BaseActivity;
import com.stoyan.weatherful.view_utils.recyclerview_utils.decorations.SpacesItemDecoration;
import com.stoyan.weatherful.view_utils.recyclerview_utils.forecast_recyclerview.ForecastRecyclerviewAdapter;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class ForecastActivity extends BaseActivity<ForecastActivityViewModel> {

    @BindView(R.id.rv_forecast) RecyclerView mRecyclerView;
    @BindView(R.id.iv_weekly_forecast_location) ImageView mLocationPicture;
    @BindView(R.id.layout_missing_network) ConstraintLayout mLayoutMissingInternet;
    @BindView(R.id.layout_weekly_forecast) ConstraintLayout mLayoutWeeklyForecast;
    @BindView(R.id.toolbar_collapsed) Toolbar mToolbar;
    @BindView(R.id.tv_location_name) TextView mTvLocationName;

    @Inject
    RxBus mRxBus;

    public static Intent getIntent(Context context, Location location) {
        Intent intent = new Intent(context, ForecastActivity.class);
        intent.putExtra(Constants.EXTRA_LOCATION, location);
        return intent;
    }

    @OnClick(R.id.tv_try_again_missing_network)
    void tryAgainFieldClicked() {
        startActivity(ForecastActivity.getIntent(this,
                getIntent().getParcelableExtra(Constants.EXTRA_LOCATION)));
        finish();
    }

    @Override
    protected void inject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);

        mViewModel = ViewModelProviders.of(this).get(ForecastActivityViewModel.class);
        mViewModel.setExtras(getIntent());
        subscribeToDataChange();
        subscribeToEventBus();

        configureToolbar();
        configureRecyclerView();
        loadLocationImage();

        mViewModel.getLocation().observe(this, location -> mViewModel.downloadWeeklyForecast(location));
        mViewModel.getHeader().observe(this, header -> mTvLocationName.setText(header));
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

    private void configureToolbar() {
        final String EMPTY_STRING = "";

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setNavigationOnClickListener(v -> finish());
        getSupportActionBar().setTitle(EMPTY_STRING);
    }

    private void loadLocationImage() {
        mViewModel.getImageUrl().observe(this, imageUrl ->
                Glide.with(ForecastActivity.this)
                    .load(imageUrl)
                    .centerCrop()
                    .placeholder(R.drawable.cityscape)
                    .into(mLocationPicture));
    }

    private void configureRecyclerView() {
        ArrayList<Data> weeklyForecast = new ArrayList<>();
        mViewModel.getWeeklyForecast().observe(this, data -> {
            weeklyForecast.clear();
            weeklyForecast.addAll(data);
            if(mRecyclerView.getAdapter() != null) {
                mRecyclerView.getAdapter().notifyDataSetChanged();
            }
        });

        mViewModel.getLocation().observe(this, location -> {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(ForecastActivity.this, LinearLayoutManager.HORIZONTAL, false));
            mRecyclerView.setAdapter(new ForecastRecyclerviewAdapter(weeklyForecast, location));
            mRecyclerView.addItemDecoration(new SpacesItemDecoration(getResources()
                    .getInteger(R.integer.viewholder_forecast_margin), SpacesItemDecoration.HORIZONTAL));
        });
    }

    public void subscribeToDataChange(){
        mViewModel.getDataDownloadedEvent().observe(this, o -> mRecyclerView.getAdapter().notifyDataSetChanged());
    }

    public void showNoInternetView() {
        if(mLayoutWeeklyForecast.getVisibility() == View.VISIBLE) {
            mLayoutWeeklyForecast.setVisibility(View.GONE);
            mLayoutMissingInternet.setVisibility(View.VISIBLE);
        }
    }
}
