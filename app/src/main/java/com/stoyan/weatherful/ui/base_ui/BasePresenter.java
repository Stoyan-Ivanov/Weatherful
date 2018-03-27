package com.stoyan.weatherful.ui.base_ui;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by stoyan.ivanov2 on 3/27/2018.
 */

public class BasePresenter <V extends BaseActivityContract> implements BasePresenterContract{
    protected V view;
    protected CompositeDisposable disposables;

    public BasePresenter(V view) {
        this.view = view;
        disposables = new CompositeDisposable();
    }

    @Override
    public void onViewDestroy() {
        view = null;
        disposables.clear();
    }
}
