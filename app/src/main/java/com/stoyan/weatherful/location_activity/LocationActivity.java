package com.stoyan.weatherful.location_activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.stoyan.weatherful.R;

import butterknife.BindView;

public class LocationActivity extends AppCompatActivity {
    @BindView(R.id.ctv_header) TextView headerBar;
    @BindView(R.id.recyclerview) RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_recyclerview);

        
    }
}
