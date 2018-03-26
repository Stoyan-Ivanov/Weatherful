package com.stoyan.weatherful.ui.location_activity;

/**
 * Created by Stoyan on 27.1.2018 г..
 */

public interface LocationActivityContract {
    void notifyDataSetChanged();
    void showError(Throwable throwable);
    void startNewActivity();
}
