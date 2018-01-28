package com.stoyan.weatherful.ui.add_location_activity;

import android.location.Address;
import android.location.Geocoder;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.stoyan.weatherful.Constants;
import com.stoyan.weatherful.models.Location;
import com.stoyan.weatherful.network.WeatherfulAPI;
import com.stoyan.weatherful.network.WeatherfulAPIImpl;

import java.io.IOException;
import java.util.List;

/**
 * Created by Stoyan on 28.1.2018 г..
 */

public class AddLocationActivityPresenter {

    public void addNewLocation(String cityName, String countryName) {
        if(checkIfDataIsCorrect(cityName, countryName)) {
            getCoordinatesOfLocation(cityName, countryName);
        }
    }

    private void getCoordinatesOfLocation(final String cityName, final String countryName) {
        Location location ;

        if(Geocoder.isPresent()){
            try {
                Geocoder gc = new Geocoder(WeatherfulAPIImpl.getStaticContext());
                List<Address> addresses= gc.getFromLocationName(cityName, 5);

                for(Address a : addresses){
                    if(a.hasLatitude() && a.hasLongitude()){
                        location = new Location(cityName, countryName,a.getLatitude(), a.getLongitude());
                        break;
                    }
                }
            } catch (IOException e) {}
        }

    }

    private boolean checkIfDataIsCorrect(String cityName, String countryName) {
        if(cityName == null || countryName == null) {
            showFailToast();
            return false;
        }
        return true;
    }

    private void showFailToast() {
        Toast toast=Toast.makeText(WeatherfulAPIImpl.getStaticContext() , Constants.INVALID_INPUT_TOAST,Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 100);
        toast.show();
    }
}
