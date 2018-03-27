package com.stoyan.weatherful.ui.base_ui;

import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

/**
 * Created by stoyan.ivanov on 3/19/2018.
 */

public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity {
    protected P presenter;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);

        ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        if(presenter != null) {
            presenter.onViewDestroy();
        }
        super.onDestroy();
    }
}
