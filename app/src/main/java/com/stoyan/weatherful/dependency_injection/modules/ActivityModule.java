package com.stoyan.weatherful.dependency_injection.modules;

import com.stoyan.weatherful.ui.base_ui.activity.BaseActivity;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {
    private BaseActivity mActivity;

    public ActivityModule(BaseActivity activity) {
        mActivity = activity;
    }

    @Provides
    public BaseActivity provideActivity() {
        return mActivity;
    }
}
