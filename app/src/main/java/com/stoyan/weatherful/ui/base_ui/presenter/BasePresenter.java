package com.stoyan.weatherful.ui.base_ui.presenter;

import com.stoyan.weatherful.ui.base_ui.contract.BaseViewContract;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by stoyan.ivanov2 on 3/27/2018.
 */

public class BasePresenter <V extends BaseViewContract> implements BasePresenterContract {
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
