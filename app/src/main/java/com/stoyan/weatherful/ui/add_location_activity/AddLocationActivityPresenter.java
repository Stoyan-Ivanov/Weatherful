package com.stoyan.weatherful.ui.add_location_activity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationProvider;
import android.view.Gravity;
import android.widget.Toast;

import com.stoyan.weatherful.Constants;
import com.stoyan.weatherful.locations_provider.LocationsProvider;
import com.stoyan.weatherful.models.Location;
import com.stoyan.weatherful.network.WeatherfulAPIImpl;
import com.stoyan.weatherful.ui.location_activity.LocationActivity;

import java.io.IOException;
import java.util.List;

/**
 * Created by Stoyan on 28.1.2018 г..
 */

public class AddLocationActivityPresenter {
    private AddLocationActivity addLocationActivity;

    public AddLocationActivityPresenter(AddLocationActivity activity) {
        addLocationActivity = activity;
    }

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
                        prepareLocationForSaving(new Location(cityName, countryName,
                                            a.getLatitude(), a.getLongitude()));

                        break;
                    }
                }
            } catch (IOException e) {}
        }
    }

    private void prepareLocationForSaving(Location location) {
        LocationsProvider locationsProvider = new LocationsProvider();
        if(locationsProvider.saveLocation(location)) {
            startNewActivity();
            showSuccessToast();
        }
    }

    private void showSuccessToast() {
        Toast toast=Toast.makeText(WeatherfulAPIImpl.getStaticContext() , Constants.SUCCESSFUL_ADDING,Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 100);
        toast.show();
    }

    private boolean checkIfDataIsCorrect(String cityName, String countryName) {
        if(cityName == "" || countryName == "") {
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

    private void startNewActivity() {
        Intent intent = new Intent(addLocationActivity, LocationActivity.class);
        addLocationActivity.startActivity(intent);
        addLocationActivity.finish();
    }
}
