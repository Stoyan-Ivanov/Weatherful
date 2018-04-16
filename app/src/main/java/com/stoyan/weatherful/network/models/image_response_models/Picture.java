package com.stoyan.weatherful.network.models.image_response_models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Stoyan on 27.1.2018 г..
 */

public class Picture {

    @SerializedName("thumbnail")
    private String thumbnailUrl;
    @SerializedName("media_fullsize")
    private String fullSizeImageUrl;

    public String getFullSizeImageUrl() {
        return fullSizeImageUrl;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

}
