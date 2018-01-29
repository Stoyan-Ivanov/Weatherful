package com.stoyan.weatherful.ui.location_activity;

import android.content.Intent;

import com.stoyan.weatherful.Constants;
import com.stoyan.weatherful.ui.add_location_activity.AddLocationActivity;
import com.stoyan.weatherful.ui.forecast_activity.ForecastActivity;
import com.stoyan.weatherful.db.LocationsProvider;
import com.stoyan.weatherful.db.Location;
import com.stoyan.weatherful.view_utils.recyclerview_utils.locations_recyclerview.LocationsRecyclerviewAdapter;
import com.stoyan.weatherful.view_utils.recyclerview_utils.locations_recyclerview.OnItemClickListener;


/**
 * Created by Stoyan on 27.1.2018 г..
 */

public class LocationActivityPresenter implements LocationActivityContract{
    LocationActivity activity;

    public LocationActivityPresenter(LocationActivity activity) {
        this.activity = activity;
    }

    @Override
    public LocationsRecyclerviewAdapter getAdapter() {
        return new LocationsRecyclerviewAdapter(LocationsProvider.getInstance().getLocations(),
                new OnItemClickListener() {
            @Override
            public void OnItemClick(Location location) {

                Intent intent = new Intent(activity, ForecastActivity.class);
                intent.putExtra(Constants.EXTRA_LOCATION, location);
                activity.startActivity(intent);
            }

            @Override
            public void OnItemLongClick(Location location) {
                LocationsProvider.getInstance().deleteLocation(location);
            }
        });
    }

    public void fabOnclick() {
        Intent intent = new Intent(activity, AddLocationActivity.class);
        activity.startActivity(intent);
    }
}
