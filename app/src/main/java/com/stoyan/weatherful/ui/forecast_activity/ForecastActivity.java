package com.stoyan.weatherful.ui.forecast_activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
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
import com.stoyan.weatherful.view_utils.recyclerview_utils.forecast_recyclerview.ForecastRecyclerviewAdapter;

import butterknife.BindView;

public class ForecastActivity extends BaseActivity<ForecastActivityPresenter> implements ForecastActivityContract {

    @BindView(R.id.rv_forecast) RecyclerView recyclerView;
    @BindView(R.id.iv_collapsible_location) ImageView locationPicture;
    @BindView(R.id.layout_missing_network) ConstraintLayout layoutMissingInternet;
    @BindView(R.id.layout_weekly_forecast) RelativeLayout layoutWeeklyForecast;
    @BindView(R.id.toolbar_collapsed) Toolbar toolbar;

    public static void getIntent(Context context, Location location) {
        Intent starter = new Intent(context, ForecastActivity.class);
        starter.putExtra(Constants.EXTRA_LOCATION, location);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.black));
        }

        setContentView(R.layout.activity_forecast);

        presenter = new ForecastActivityPresenter(getIntent(), this);

        configureToolbar();
        configureRecyclerView();
        loadLocationImage();
    }

    private void configureToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());
        getSupportActionBar().setTitle(presenter.getHeader());
    }

    private void loadLocationImage() {
        Glide.with(this)
                .load(presenter.getImageUrl())
                .centerCrop()
                .placeholder(R.drawable.cityscape)
                .into(locationPicture);
    }

    private void configureRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(new ForecastRecyclerviewAdapter(presenter.getWeeklyForecast(), presenter.getLocation()));
        presenter.downloadWeeklyForecast();
    }

    @Override
    public void notifyDataSetChanged(){
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void showError(Throwable throwable){
        Log.d("SII", "showError: " + throwable.getMessage());
    }

    @Override
    public void showNoInternetView() {
        if(layoutWeeklyForecast.getVisibility() == View.VISIBLE) {
            layoutWeeklyForecast.setVisibility(View.GONE);
            layoutMissingInternet.setVisibility(View.VISIBLE);
        }
    }
}
