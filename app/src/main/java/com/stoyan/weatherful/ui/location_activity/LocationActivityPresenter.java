package com.stoyan.weatherful.ui.location_activity;

import com.stoyan.weatherful.DataManager;
import com.stoyan.weatherful.db.Location;
import com.stoyan.weatherful.ui.base_ui.presenter.BasePresenter;

import java.util.ArrayList;

import io.reactivex.functions.Consumer;


/**
 * Created by Stoyan on 27.1.2018 г..
 */

public class LocationActivityPresenter extends BasePresenter<LocationActivityContract> {
    private ArrayList<Location> locations;

    public LocationActivityPresenter(LocationActivityContract view) {
        super(view);
    }

    public void fabOnclick() {
        view.startNewActivity();
    }

    public void downloadData() {
        addDisposable(DataManager.getInstance().getLocationDataObservable()
                .subscribe(getLocationConsumer(), getErrorConsumer())
        );
    }

    private Consumer<ArrayList<Location>> getLocationConsumer() {
        return locations -> {
            LocationActivityPresenter.this.locations.clear();
            LocationActivityPresenter.this.locations.addAll(locations);
            view.notifyDataSetChanged();
        };
    }

    private Consumer<? super Throwable> getErrorConsumer() {
        return (Consumer<Throwable>) throwable -> view.showError(throwable);
    }

    public ArrayList<Location> getLocations() {
        locations = new ArrayList<>();
        return locations;
    }
}
