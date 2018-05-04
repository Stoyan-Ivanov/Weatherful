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
import com.stoyan.weatherful.persistence.models.Location;
import com.stoyan.weatherful.ui.base_ui.activity.BaseActivity;
import com.stoyan.weatherful.view_utils.recyclerview_utils.decorations.SpacesItemDecoration;
import com.stoyan.weatherful.view_utils.recyclerview_utils.forecast_recyclerview.ForecastRecyclerviewAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class ForecastActivity extends BaseActivity<ForecastActivityPresenter> {

    @BindView(R.id.rv_forecast) RecyclerView mRecyclerView;
    @BindView(R.id.iv_weekly_forecast_location) ImageView mLocationPicture;
    @BindView(R.id.layout_missing_network) ConstraintLayout mLayoutMissingInternet;
    @BindView(R.id.layout_weekly_forecast) ConstraintLayout mLayoutWeeklyForecast;
    @BindView(R.id.toolbar_collapsed) Toolbar mToolbar;
    @BindView(R.id.tv_location_name) TextView mTvLocationName;

    ForecastActivityViewModel mViewModel;

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_forecast);

        mViewModel = ViewModelProviders.of(this).get(ForecastActivityViewModel.class);
        mViewModel.setExtras(getIntent());
        subscribeToDataChange();

        configureToolbar();
        configureRecyclerView();
        loadLocationImage();

        mViewModel.getHeader().observe(this, header -> mTvLocationName.setText(header));
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
        mViewModel.getLocation().observe(this, location -> {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(ForecastActivity.this, LinearLayoutManager.HORIZONTAL, false));
            mRecyclerView.setAdapter(new ForecastRecyclerviewAdapter(new ArrayList<>(), location));
            mRecyclerView.addItemDecoration(new SpacesItemDecoration(getResources().getInteger(R.integer.viewholder_forecast_margin), SpacesItemDecoration.HORIZONTAL));
        });




        mViewModel.downloadWeeklyForecast();
    }

    public void subscribeToDataChange(){
        mViewModel.getDataDownloadedEvent().observe(this, o -> mRecyclerView.getAdapter().notifyDataSetChanged());
    }

    public void showError(Throwable throwable){
        Log.d("SII", "showError: " + throwable.getMessage());
    }

    public void showNoInternetView() {
        if(mLayoutWeeklyForecast.getVisibility() == View.VISIBLE) {
            mLayoutWeeklyForecast.setVisibility(View.GONE);
            mLayoutMissingInternet.setVisibility(View.VISIBLE);
        }
    }
}
