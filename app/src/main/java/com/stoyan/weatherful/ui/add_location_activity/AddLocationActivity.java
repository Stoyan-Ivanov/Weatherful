package com.stoyan.weatherful.ui.add_location_activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.stoyan.weatherful.R;
import com.stoyan.weatherful.ui.base_ui.activity.BaseActivity;
import com.stoyan.weatherful.ui.location_activity.LocationActivity;

import butterknife.BindView;

public class AddLocationActivity extends BaseActivity implements AddLocationActivityContract {

    @BindView(R.id.et_city) EditText etCityName;
    @BindView(R.id.et_country) EditText etCountryName;
    @BindView(R.id.btn_done_adding) Button btnAddLocation;

    private AddLocationActivityPresenter presenter;

    public static Intent getIntent(Context context) {
        return new Intent(context, AddLocationActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_location);

        presenter = new AddLocationActivityPresenter(this);

        btnAddLocation.setOnClickListener(view ->
                presenter.addNewLocation(etCityName.getText().toString(),
                                        etCountryName.getText().toString())
        );
    }

    @Override
    public void startNewLocationsActivity() {
        startActivity(LocationActivity.getIntent(this));
        finish();
    }
}
