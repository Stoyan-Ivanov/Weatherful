package com.stoyan.weatherful.ui.forecast_activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.esotericsoftware.kryo.serializers.FieldSerializer;
import com.stoyan.weatherful.Constants;
import com.stoyan.weatherful.R;
import com.stoyan.weatherful.db.Location;
import com.stoyan.weatherful.ui.base_ui.activity.BaseActivity;
import com.stoyan.weatherful.view_utils.recyclerview_utils.forecast_recyclerview.ForecastRecyclerviewAdapter;

import butterknife.BindView;
import butterknife.OnClick;

public class ForecastActivity extends BaseActivity<ForecastActivityPresenter> implements ForecastActivityContract {

    @BindView(R.id.rv_forecast) RecyclerView recyclerView;
    @BindView(R.id.iv_collapsible_location) ImageView locationPicture;
    @BindView(R.id.collapsing_toolbar) CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.iv_arrow_back) ImageView arrowBack;
//    @BindView(R.id.swipe_refresh) SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.layout_missing_network) ConstraintLayout layoutMissingInternet;
    @BindView(R.id.layout_weekly_forecast) CoordinatorLayout layoutWeeklyforecast;

    public static void getIntent(Context context, Location location) {
        Intent starter = new Intent(context, ForecastActivity.class);
        starter.putExtra(Constants.EXTRA_LOCATION, location);
        context.startActivity(starter);
    }

    @OnClick(R.id.iv_arrow_back)
    void backButtonClicked() {
        this.finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);

        presenter = new ForecastActivityPresenter(getIntent(), this);

        configureCollapsingToolbar();
        configureRecyclerView();
        //configureSwipeRefreshLayout();
        loadLocationImage();
    }

    private void loadLocationImage() {
        Glide.with(this)
                .load(presenter.getImageUrl())
                .centerCrop()
                .placeholder(R.drawable.cityscape)
                .into(locationPicture);
    }

    private void configureCollapsingToolbar() {
        collapsingToolbarLayout.setTitle(presenter.getHeader());
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);
    }

//    private void configureSwipeRefreshLayout() {
//        swipeRefreshLayout.setOnRefreshListener(() -> {
//            presenter.downloadWeeklyForecast();
//            if(swipeRefreshLayout.isRefreshing()) {
//                swipeRefreshLayout.setRefreshing(false);
//            }
//        });
//    }

    private void configureRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
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
        if(layoutWeeklyforecast.getVisibility() == View.VISIBLE) {
            layoutWeeklyforecast.setVisibility(View.GONE);
            layoutMissingInternet.setVisibility(View.VISIBLE);
        }
    }
}
