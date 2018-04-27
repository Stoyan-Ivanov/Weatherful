package com.stoyan.weatherful.ui.base_ui.presenter;

import com.stoyan.weatherful.WeatherfulApplication;
import com.stoyan.weatherful.dependency_injection.components.AppComponent;
import com.stoyan.weatherful.ui.base_ui.contract.BaseViewContract;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by stoyan.ivanov2 on 3/27/2018.
 */

public abstract class BasePresenter<V extends BaseViewContract> implements BasePresenterContract {
    protected V view;
    private CompositeDisposable disposables;

    public BasePresenter() {
        disposables = new CompositeDisposable();

        inject();
    }

    protected abstract void inject();

    public void addDisposable(Disposable disposable) {
        disposables.add(disposable);
    }

    public void setView(V view) {
        this.view = view;
    }

    @Override
    public void onViewDestroy() {
        view = null;
        disposables.clear();
    }

    protected AppComponent.PresenterComponent getPresenterComponent() {
        return WeatherfulApplication.getInstance()
                .getComponent()
                .presenterComponent();
    }
}
