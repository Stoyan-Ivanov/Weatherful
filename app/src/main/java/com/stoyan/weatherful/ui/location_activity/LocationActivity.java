package com.stoyan.weatherful.ui.location_activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.stoyan.weatherful.R;
import com.stoyan.weatherful.ui.base_ui.BaseActivity;
import com.stoyan.weatherful.ui.add_location_activity.AddLocationActivity;
import com.stoyan.weatherful.view_utils.recyclerview_utils.locations_recyclerview.LocationsRecyclerViewAdapter;

import butterknife.BindView;
import io.fabric.sdk.android.Fabric;

public class LocationActivity extends BaseActivity implements LocationActivityContract{

    @BindView(R.id.ctv_header) TextView headerBar;
    @BindView(R.id.recyclerview) RecyclerView recyclerView;
    @BindView(R.id.fab_add) FloatingActionButton fabAddLocation;

    private LocationActivityPresenter presenter;

    public static Intent getIntent(Context context) {
        return new Intent(context, LocationActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_base_recyclerview);

        presenter = new LocationActivityPresenter(this);

        headerBar.setText(R.string.location_activity_header);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new LocationsRecyclerViewAdapter(presenter.getLocations()));

        fabAddLocation.setVisibility(View.VISIBLE);
        fabAddLocation.setOnClickListener(v -> presenter.fabOnclick());

        presenter.downloadData();
    }

    @Override
    protected void onDestroy() {
        presenter.onViewDestroy();
        super.onDestroy();
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
}
