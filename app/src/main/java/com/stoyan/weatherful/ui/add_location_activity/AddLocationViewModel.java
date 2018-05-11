package com.stoyan.weatherful.ui.add_location_activity;

import android.location.Address;
import android.location.Geocoder;

import com.stoyan.weatherful.DataManager;
import com.stoyan.weatherful.WeatherfulApplication;
import com.stoyan.weatherful.livedata_utils.SingleLiveEvent;
import com.stoyan.weatherful.persistence.models.Location;
import com.stoyan.weatherful.viewmodel.BaseViewModel;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by stoyan.ivanov2 on 5/2/2018.
 */

public class AddLocationViewModel extends BaseViewModel {
    @Inject
    DataManager mDataManager;

    SingleLiveEvent saveLocationEvent;

    public AddLocationViewModel() {
        saveLocationEvent = new SingleLiveEvent();
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
                        + countryName, 1);

                for(Address a : addresses){
                    if(a.hasLatitude() && a.hasLongitude()){
                        Location newLocation = new Location(cityName, countryName,
                                a.getLatitude(), a.getLongitude());
                                mDataManager.saveLocation(newLocation);

                        break;
                    }
                }
            } catch (IOException e) {}
        }
    }

    public void onDoneButtonClicked() {
        saveLocationEvent.call();
    }

    private boolean checkIfDataIsCorrect(String cityName, String countryName) {
        final String EMPTY_STRING = "";

        if(cityName.equals(EMPTY_STRING) || countryName.equals(EMPTY_STRING)) {
           // WeatherfulApplication.showToast(mContext.getString(R.string.invalid_input));
            return false;
        }
        return true;
    }

    @Override
    protected void inject() {
        getViewModelComponent().inject(this);
    }
}
