package com.stoyan.weatherful.viewmodel;

import android.arch.lifecycle.ViewModel;

import com.stoyan.weatherful.WeatherfulApplication;
import com.stoyan.weatherful.dependency_injection.components.AppComponent;

public abstract class BaseViewModel extends ViewModel {

    public BaseViewModel() {
        inject();
    }

    protected abstract void inject();

    protected AppComponent.ViewModelComponent getViewModelComponent() {
        return WeatherfulApplication.getInstance()
                .getComponent()
                .viewModelComponent();
    }
}
