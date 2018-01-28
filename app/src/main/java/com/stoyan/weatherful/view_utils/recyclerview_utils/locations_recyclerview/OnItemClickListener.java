package com.stoyan.weatherful.view_utils.recyclerview_utils.locations_recyclerview;

import com.stoyan.weatherful.models.Location;

/**
 * Created by Stoya on 27.1.2018 г..
 */

public interface OnItemClickListener {
    void OnItemClick(Location location);

    void OnItemLongClick(Location location);
}
