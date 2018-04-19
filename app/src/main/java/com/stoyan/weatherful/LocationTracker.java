package com.stoyan.weatherful;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by stoyan.ivanov2 on 4/17/2018.
 */

public class LocationTracker implements LocationListener{
    private LocationManager mLocationManager;
    private Context mContext;
    private com.stoyan.weatherful.db.Location currentLocation;

    public LocationTracker() {
        mContext = WeatherfulApplication.getStaticContext();
        mLocationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
    }

    public com.stoyan.weatherful.db.Location getCurrentLocation() {
        boolean isGPSEnabled;
        boolean isNetworkEnabled;

        isGPSEnabled = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNetworkEnabled = mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if(!isGPSEnabled || !isNetworkEnabled) {

        } else {
            if(isNetworkEnabled) {
                checkIfPermissionsAreGranted(LocationManager.NETWORK_PROVIDER);
                return  currentLocation;
            }

            if (isGPSEnabled) {
                checkIfPermissionsAreGranted(LocationManager.GPS_PROVIDER);
            }
        }
        return currentLocation;
    }

    private void checkIfPermissionsAreGranted(String provider) {
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {

            WeatherfulApplication.showToast("Location service is disabled!");
            // showSettingsAlert();
            return;
        }
        mLocationManager.requestSingleUpdate(provider, this, null);
        Location location = mLocationManager.getLastKnownLocation(provider);
        Log.d("SII", location.getLatitude() + ", " + location.getLongitude());

        getLocationNameAndCountry(location.getLatitude(), location.getLongitude());
    }

    private void getLocationNameAndCountry(double latitude, double longitude) {
        if (Geocoder.isPresent()) {
            Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());
            try {
                List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
                String cityName = addresses.get(0).getLocality();
                String countryName = addresses.get(0).getCountryName();

                currentLocation = new com.stoyan.weatherful.db.Location(cityName, countryName, latitude, longitude);

            } catch (IOException mE) {
                mE.printStackTrace();
            }
        } else {
            Log.d("SII", "getLocationNameAndCountry: no geocoder available");
        }
    }

    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);

        alertDialog.setTitle("GPS settings");
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");

        alertDialog.setPositiveButton("Settings",
                (dialog, which) -> {
                    Intent intent = new Intent(
                            Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    mContext.startActivity(intent);
                });

        alertDialog.setNegativeButton("Cancel",
                (dialog, which) -> dialog.cancel());

        alertDialog.show();
    }

    @Override
    public void onLocationChanged(Location location) {}

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {}

    @Override
    public void onProviderEnabled(String provider) {}

    @Override
    public void onProviderDisabled(String provider) {}
}
