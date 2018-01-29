package com.stoyan.weatherful.network.models.image_response_models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Stoyan on 27.1.2018 г..
 */

public class ImageResponse {

    @SerializedName("data")
    private Data data;

    public ImageResponse(Data data) {
        this.data = data;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}
