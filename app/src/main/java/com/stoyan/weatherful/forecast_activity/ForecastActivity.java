package com.stoyan.weatherful.forecast_activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.stoyan.weatherful.Constants;
import com.stoyan.weatherful.R;
import com.stoyan.weatherful.models.Location;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ForecastActivity extends AppCompatActivity {

    @BindView(R.id.ctv_header)
    TextView headerBar;

    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    public static Intent newInstance(Location location) {

       Intent intent = new Intent();
       intent.putExtra(Constants.EXTRA_LOCATION, location);

       return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_recyclerview);

        ButterKnife.bind(this);

        ForecastActivityProvider provider = new ForecastActivityProvider(getIntent());

        headerBar.setText(provider.getHeader());

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setAdapter(provider.getAdapter());
    }
}
