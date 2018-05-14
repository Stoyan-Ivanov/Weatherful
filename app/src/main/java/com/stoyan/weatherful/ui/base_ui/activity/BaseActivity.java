package com.stoyan.weatherful.ui.base_ui.activity;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;

import com.stoyan.weatherful.R;
import com.stoyan.weatherful.WeatherfulApplication;
import com.stoyan.weatherful.dependency_injection.components.AppComponent;
import com.stoyan.weatherful.viewmodel.BaseViewModel;

import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by stoyan.ivanov on 3/19/2018.
 */

public abstract class BaseActivity<VM extends BaseViewModel> extends AppCompatActivity {
    protected VM mViewModel;
    protected CompositeDisposable disposables = new CompositeDisposable();

    public BaseActivity() {
        inject();
    }

    protected abstract void inject();

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);

        ButterKnife.bind(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.statusBarBlue));
        }
    }

    protected void addDisposable(Disposable disposable) {
        disposables.add(disposable);
    }

    protected AppComponent.ActivityComponent getActivityComponent() {
        return WeatherfulApplication.getInstance()
                .getComponent()
                .activityComponent();
    }

    @Override
    protected void onDestroy() {
        disposables.dispose();
        super.onDestroy();
    }
}
