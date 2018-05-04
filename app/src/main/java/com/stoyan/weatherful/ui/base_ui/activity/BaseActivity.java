package com.stoyan.weatherful.ui.base_ui.activity;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;

import com.stoyan.weatherful.R;
import com.stoyan.weatherful.ui.base_ui.presenter.BasePresenter;

import butterknife.ButterKnife;

/**
 * Created by stoyan.ivanov on 3/19/2018.
 */

public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity {
    protected P mViewModel;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);

        ButterKnife.bind(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.statusBarBlue));
        }
    }

    @Override
    protected void onDestroy() {
        if(mViewModel != null) {
            mViewModel.onViewDestroy();
        }
        super.onDestroy();
    }
}
