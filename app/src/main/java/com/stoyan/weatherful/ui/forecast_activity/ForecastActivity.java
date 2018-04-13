package com.stoyan.weatherful.ui.forecast_activity;

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
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.stoyan.weatherful.Constants;
import com.stoyan.weatherful.R;
import com.stoyan.weatherful.db.Location;
import com.stoyan.weatherful.ui.base_ui.activity.BaseActivity;
import com.stoyan.weatherful.view_utils.recyclerview_utils.decorations.SpacesItemDecoration;
import com.stoyan.weatherful.view_utils.recyclerview_utils.forecast_recyclerview.ForecastRecyclerviewAdapter;

import butterknife.BindView;

public class ForecastActivity extends BaseActivity<ForecastActivityPresenter> implements ForecastActivityContract {

    @BindView(R.id.rv_forecast) RecyclerView mRecyclerView;
    @BindView(R.id.iv_weekly_forecast_location) ImageView mLocationPicture;
    @BindView(R.id.layout_missing_network) ConstraintLayout mLayoutMissingInternet;
    @BindView(R.id.layout_weekly_forecast) RelativeLayout mLayoutWeeklyForecast;
    @BindView(R.id.toolbar_collapsed) Toolbar mToolbar;

    public static void getIntent(Context context, Location location) {
        Intent starter = new Intent(context, ForecastActivity.class);
        starter.putExtra(Constants.EXTRA_LOCATION, location);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_forecast);

        presenter = new ForecastActivityPresenter();
        presenter.setView(this);
        presenter.setExtras(getIntent());

        configureToolbar();
        configureRecyclerView();
        loadLocationImage();
    }

    private void configureToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setNavigationOnClickListener(v -> finish());
        getSupportActionBar().setTitle(presenter.getHeader());
    }

    private void loadLocationImage() {
        Glide.with(this)
                .load(presenter.getImageUrl())
                .centerCrop()
                .placeholder(R.drawable.cityscape)
                .into(mLocationPicture);
    }

    private void configureRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mRecyclerView.setAdapter(new ForecastRecyclerviewAdapter(presenter.getmWeeklyForecast(), presenter.getmLocation()));
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(getResources().getInteger(R.integer.viewholder_forecast_margin), SpacesItemDecoration.HORIZONTAL));

        presenter.downloadWeeklyForecast();
    }

    @Override
    public void notifyDataSetChanged(){
        mRecyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void showError(Throwable throwable){
        Log.d("SII", "showError: " + throwable.getMessage());
    }

    @Override
    public void showNoInternetView() {
        if(mLayoutWeeklyForecast.getVisibility() == View.VISIBLE) {
            mLayoutWeeklyForecast.setVisibility(View.GONE);
            mLayoutMissingInternet.setVisibility(View.VISIBLE);
        }
    }
}
