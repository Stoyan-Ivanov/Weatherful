package com.stoyan.weatherful.ui.add_location_activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;

import com.stoyan.weatherful.R;
import com.stoyan.weatherful.ui.base_ui.activity.BaseActivity;
import com.stoyan.weatherful.ui.location_activity.LocationActivity;
import com.stoyan.weatherful.utils.StartActivityEnum;

import butterknife.BindView;
import butterknife.OnClick;

public class AddLocationActivity extends BaseActivity<AddLocationViewModel> {

    @BindView(R.id.et_city) EditText mEtCityName;
    @BindView(R.id.et_country) EditText mEtCountryName;
    @BindView(R.id.toolbar_add) Toolbar mTitleBar;

    public static Intent getIntent(Context context) {
        return new Intent(context, AddLocationActivity.class);
    }

    @OnClick(R.id.btn_done_adding)
        void addLocation() {
            mViewModel.addNewLocation(mEtCityName.getText().toString(), mEtCountryName.getText().toString());
    }

    @Override
    protected void inject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_location);

        mViewModel = ViewModelProviders.of(this).get(AddLocationViewModel.class);
        subscribeToNavigationChange();

        configureToolbar();
    }

    private void configureToolbar() {
        setSupportActionBar(mTitleBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mTitleBar.setNavigationOnClickListener(v -> finish());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getSupportActionBar().setTitle(getString(R.string.add_location_activity_header));
        }
    }

    private void subscribeToNavigationChange() {
        mViewModel.getSaveLocationEvent().observe(this, o -> {
            startActivity(LocationActivity.getIntent(this, StartActivityEnum.NORMAL));
            finish();});
    }
}
