package com.stoyan.weatherful.dependency_injection.modules;

import com.stoyan.weatherful.viewmodel.BaseViewModel;

import dagger.Module;
import dagger.Provides;

@Module
public class ViewModelModule {
    private BaseViewModel mViewModel;

    public ViewModelModule(BaseViewModel viewModel) {
        this.mViewModel = viewModel;
    }

    @Provides
    public BaseViewModel provideViewModel() {
        return mViewModel;
    }
}
