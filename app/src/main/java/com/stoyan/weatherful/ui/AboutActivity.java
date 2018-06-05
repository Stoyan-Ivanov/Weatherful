package com.stoyan.weatherful.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.stoyan.weatherful.BuildConfig;
import com.stoyan.weatherful.R;
import com.stoyan.weatherful.ui.base_ui.activity.BaseActivity;

import butterknife.BindView;

public class AboutActivity extends BaseActivity {
    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.tv_app_version) TextView mAppVersion;

    public static Intent getIntent(Context context) {
        return new Intent(context, AboutActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        
        configureToolbar();
        mAppVersion.setText(getString(R.string.app_version, BuildConfig.VERSION_NAME));
    }

    private void configureToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(R.drawable.ic_about);
        mToolbar.setNavigationOnClickListener(v -> finish());
        getSupportActionBar().setTitle(getString(R.string.about_screen_title));
    }

    @Override
    protected void inject() {
        getActivityComponent().inject(this);
    }
}
