package com.stoyan.weatherful.ui.add_location_activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.stoyan.weatherful.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddLocationActivity extends AppCompatActivity {

    @BindView(R.id.et_city)
    EditText etCityName;

    @BindView(R.id.et_country)
    EditText etCountryName;

    @BindView(R.id.btn_done_adding)
    Button btnAddLocation;

    private AddLocationActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_location);

        ButterKnife.bind(this);
        presenter = new AddLocationActivityPresenter(this);

        btnAddLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.addNewLocation(etCityName.getText().toString(),
                        etCountryName.getText().toString());
            }
        });

    }
}
