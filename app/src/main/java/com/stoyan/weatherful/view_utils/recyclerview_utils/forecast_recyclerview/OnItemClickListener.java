package com.stoyan.weatherful.view_utils.recyclerview_utils.forecast_recyclerview;

import com.stoyan.weatherful.network.network_models.forecast_full_models.Data;

import java.util.ArrayList;

/**
 * Created by Stoyan on 27.1.2018 г..
 */

public interface OnItemClickListener {
    void OnItemClick(ArrayList<Data> data, int position);
}
