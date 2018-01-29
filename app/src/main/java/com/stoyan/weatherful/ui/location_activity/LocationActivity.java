package com.stoyan.weatherful.ui.location_activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.stoyan.weatherful.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.fabric.sdk.android.Fabric;

public class LocationActivity extends AppCompatActivity {

    @BindView(R.id.ctv_header)
    TextView headerBar;

    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    @BindView(R.id.fab_add)
    FloatingActionButton fabAddLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_base_recyclerview);

        ButterKnife.bind(this);
        fabAddLocation.setVisibility(View.VISIBLE);

        final LocationActivityPresenter presenter = new LocationActivityPresenter(this);

        headerBar.setText(R.string.location_activity_header);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(presenter.getAdapter());

        fabAddLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.fabOnclick();
            }
        });
    }
}