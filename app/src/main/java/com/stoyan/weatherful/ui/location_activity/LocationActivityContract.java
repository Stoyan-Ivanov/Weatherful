package com.stoyan.weatherful.ui.location_activity;

import com.stoyan.weatherful.ui.base_ui.BaseActivityContract;

/**
 * Created by Stoyan on 27.1.2018 г..
 */

public interface LocationActivityContract extends BaseActivityContract {
    void notifyDataSetChanged();
    void showError(Throwable throwable);
    void startNewActivity();
}
