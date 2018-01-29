package com.stoyan.weatherful.network.models.image_response_models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Stoyan on 27.1.2018 г..
 */

public class Picture {

    @SerializedName("thumbnail")
    private String thumbnailUrl;

    public Picture(String thumbnailUrl ) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

}
