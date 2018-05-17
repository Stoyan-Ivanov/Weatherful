package com.stoyan.weatherful.viewmodel;

import android.arch.lifecycle.ViewModel;

import com.stoyan.weatherful.WeatherfulApplication;
import com.stoyan.weatherful.dependency_injection.components.AppComponent;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BaseViewModel extends ViewModel {
    private CompositeDisposable mDisposables;

    public BaseViewModel() {
        inject();
        mDisposables = new CompositeDisposable();
    }

    protected abstract void inject();

    protected void addDisposable(Disposable disposable) {
        mDisposables.add(disposable);
    }

    protected AppComponent.ViewModelComponent getViewModelComponent() {
        return WeatherfulApplication.getInstance()
                .getComponent()
                .viewModelComponent();
    }

    @Override
    protected void onCleared() {
        if(mDisposables != null) {
            mDisposables.dispose();
        }
        super.onCleared();
    }
}
