package com.stoyan.weatherful.ui.forecast_activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.stoyan.weatherful.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ForecastActivity extends AppCompatActivity {

    @BindView(R.id.ctv_header)
    TextView headerBar;

    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    private ForecastActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_recyclerview);

        ButterKnife.bind(this);

        presenter = new ForecastActivityPresenter(getIntent(), this);

        headerBar.setText(presenter.getHeader());

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(presenter.getAdapter());
    }
}
