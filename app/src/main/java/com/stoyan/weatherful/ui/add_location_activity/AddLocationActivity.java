package com.stoyan.weatherful.ui.add_location_activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;

import com.stoyan.weatherful.R;
import com.stoyan.weatherful.ui.base_ui.activity.BaseActivity;
import com.stoyan.weatherful.ui.location_activity.LocationActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class AddLocationActivity extends BaseActivity<AddLocationActivityPresenter> implements AddLocationActivityContract {

    @BindView(R.id.et_city) EditText mEtCityName;
    @BindView(R.id.et_country) EditText mEtCountryName;
    @BindView(R.id.toolbar_add) Toolbar mTitleBar;

    private AddLocationActivityPresenter presenter;

    public static Intent getIntent(Context context) {
        return new Intent(context, AddLocationActivity.class);
    }

    @OnClick(R.id.btn_done_adding)
        void addLocation() {
        presenter.addNewLocation(mEtCityName.getText().toString(),
                mEtCountryName.getText().toString());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_location);

        presenter = new AddLocationActivityPresenter();
        presenter.setView(this);

        setSupportActionBar(mTitleBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mTitleBar.setNavigationOnClickListener(v -> finish());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getSupportActionBar().setTitle(getString(R.string.add_location_activity_header));
        }
    }

    @Override
    public void startNewLocationsActivity() {
        startActivity(LocationActivity.getIntent(this));
        finish();
    }
}
