package com.stoyan.weatherful.network.models.image_response_models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Stoyan on 27.1.2018 г..
 */

public class Result {
        @SerializedName("items")
        private ArrayList<Picture> pictures;

        public Result(ArrayList<Picture> pictures) {
            this.pictures = pictures;
        }

        public ArrayList<Picture> getPictures() {
            return pictures;
        }

    public void setPictures(ArrayList<Picture> pictures) {
        this.pictures = pictures;
    }
}
