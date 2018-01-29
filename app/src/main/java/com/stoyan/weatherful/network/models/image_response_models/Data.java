package com.stoyan.weatherful.network.models.image_response_models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Stoyan on 27.1.2018 г..
 */

public class Data {
    @SerializedName("result")
    private Result result;

    public Data(Result result) {
        this.result = result;
    }

    public Result getResult() {
        return result;
    }

}
