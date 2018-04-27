package com.stoyan.weatherful.dependency_injection.modules;

import com.stoyan.weatherful.ui.base_ui.presenter.BasePresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Copyright (c) 2018 Adastra.ONE a.s., all rights reserved.
 */
@Module
public class PresenterModule {

    private BasePresenter presenter;

    public PresenterModule(BasePresenter presenter) {
        this.presenter = presenter;
    }

    @Provides
    public BasePresenter providePresenter() {
        return presenter;
    }
}
