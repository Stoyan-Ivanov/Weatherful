package com.stoyan.weatherful.ui.forecast_activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.stoyan.weatherful.Constants;
import com.stoyan.weatherful.R;
import com.stoyan.weatherful.db.Location;
import com.stoyan.weatherful.network.models.forecast_full_models.Data;
import com.stoyan.weatherful.ui.BaseActivity;
import com.stoyan.weatherful.view_utils.recyclerview_utils.forecast_recyclerview.ForecastRecyclerviewAdapter;

import java.util.ArrayList;

import butterknife.BindView;

public class ForecastActivity extends BaseActivity implements ForecastActivityContract {

    @BindView(R.id.ctv_header)
    TextView headerBar;

    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    private ForecastActivityPresenter presenter;

    public static void getIntent(Context context, Location location) {
        Intent starter = new Intent(context, ForecastActivity.class);
        starter.putExtra(Constants.EXTRA_LOCATION, location);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_recyclerview);

        presenter = new ForecastActivityPresenter(getIntent(), this);

        headerBar.setText(presenter.getHeader());

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new ForecastRecyclerviewAdapter(presenter.getWeeklyForecast(), presenter.getLocation()));
        presenter.downloadWeeklyForecast();
    }

    @Override
    protected void onDestroy() {
        presenter.onViewDestroy();
        super.onDestroy();
    }

    @Override
    public void notifyDataSetChanged(){
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void showError(Throwable throwable){
        Log.d("SII", "showError: " + throwable.getMessage());
    }
}
