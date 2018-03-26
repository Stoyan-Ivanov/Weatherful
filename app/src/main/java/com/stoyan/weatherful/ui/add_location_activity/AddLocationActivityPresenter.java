package com.stoyan.weatherful.ui.add_location_activity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;

import com.stoyan.weatherful.R;
import com.stoyan.weatherful.db.LocationsProvider;
import com.stoyan.weatherful.db.Location;
import com.stoyan.weatherful.network.WeatherfulApplication;
import com.stoyan.weatherful.ui.BasePresenterContract;
import com.stoyan.weatherful.ui.location_activity.LocationActivity;

import java.io.IOException;
import java.util.List;

/**
 * Created by Stoyan on 28.1.2018 г..
 */

public class AddLocationActivityPresenter implements BasePresenterContract {
    private AddLocationActivityContract view;

    public AddLocationActivityPresenter(AddLocationActivityContract view) {
        this.view = view;
    }

    public void addNewLocation(String cityName, String countryName) {
        if(checkIfDataIsCorrect(cityName, countryName)) {
            getCoordinatesOfLocation(cityName, countryName);
        }
    }

    private void getCoordinatesOfLocation(final String cityName, final String countryName) {
        if(Geocoder.isPresent()){
            try {
                Geocoder gc = new Geocoder(WeatherfulApplication.getStaticContext());
                List<Address> addresses= gc.getFromLocationName(cityName + ", "
                                                                + countryName, 5);

                for(Address a : addresses){
                    if(a.hasLatitude() && a.hasLongitude()){
                        prepareLocationForSaving(new Location(cityName, countryName,
                                            a.getLatitude(), a.getLongitude()));

                        break;
                    }
                }
            } catch (IOException e) {}
        }
    }

    private void prepareLocationForSaving(Location location) {
        if(LocationsProvider.getInstance().saveLocation(location)) {
            WeatherfulApplication.showToast(WeatherfulApplication.getStringFromId(R.string.successful_adding));
            view.startNewLocationsActivity();
        }

    }

    private boolean checkIfDataIsCorrect(String cityName, String countryName) {
        if(cityName.equals("") || countryName.equals("")) {
            WeatherfulApplication.showToast(WeatherfulApplication.getStringFromId(R.string.invalid_input));
            return false;
        }
        return true;
    }

    @Override
    public void onViewDestroy() {
        view = null;
    }
}
