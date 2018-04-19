package com.stoyan.weatherful.ui.add_location_activity;

import android.location.Address;
import android.location.Geocoder;

import com.stoyan.weatherful.R;
import com.stoyan.weatherful.WeatherfulApplication;
import com.stoyan.weatherful.db.models.Location;
import com.stoyan.weatherful.network.DataManager;
import com.stoyan.weatherful.ui.base_ui.presenter.BasePresenter;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by Stoyan on 28.1.2018 г..
 */

public class AddLocationActivityPresenter extends BasePresenter<AddLocationActivityContract> {
    @Inject DataManager mDataManager;

    @Override
    protected void inject() {
        getPresenterComponent().inject(this);
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
        if(mDataManager.saveLocation(location)) {
            WeatherfulApplication.showToast(WeatherfulApplication.getStringFromId(R.string.successful_adding));
            view.startNewLocationsActivity();
        }
    }

    private boolean checkIfDataIsCorrect(String cityName, String countryName) {
        final String EMPTY_STRING = "";

        if(cityName.equals(EMPTY_STRING) || countryName.equals(EMPTY_STRING)) {
            WeatherfulApplication.showToast(WeatherfulApplication.getStringFromId(R.string.invalid_input));
            return false;
        }
        return true;
    }
}
