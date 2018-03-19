package com.stoyan.weatherful.ui.forecast_activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.stoyan.weatherful.R;
import com.stoyan.weatherful.ui.BaseActivity;

import butterknife.BindView;

public class ForecastActivity extends BaseActivity {

    @BindView(R.id.ctv_header)
    TextView headerBar;

    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    private ForecastActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_recyclerview);

        presenter = new ForecastActivityPresenter(getIntent(), this);

        headerBar.setText(presenter.getHeader());

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(presenter.getAdapter());
    }

    @Override
    protected void onDestroy() {
        presenter.onViewDestroy();
        super.onDestroy();
    }
}
