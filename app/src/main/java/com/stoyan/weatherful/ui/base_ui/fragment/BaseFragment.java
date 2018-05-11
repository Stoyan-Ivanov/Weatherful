package com.stoyan.weatherful.ui.base_ui.fragment;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stoyan.weatherful.viewmodel.BaseViewModel;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by stoyan.ivanov on 3/26/2018.
 */

public abstract class BaseFragment<VM extends BaseViewModel> extends Fragment{
    protected VM mViewModel;
    private Unbinder unbinder;

    protected View inflateCurrentView(LayoutInflater inflater, int layoutId, ViewGroup container) {
        View view = inflater.inflate(layoutId, container, false);

        unbinder = ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }
}
