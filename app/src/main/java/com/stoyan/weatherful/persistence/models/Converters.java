package com.stoyan.weatherful.persistence.models;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;

/**
 * Created by stoyan.ivanov2 on 4/19/2018.
 */

public class Converters {

    @TypeConverter
    public static Coordinates fromStringtoCoordinates(String value) {
        return new Gson().fromJson(value, Coordinates.class);
    }

    @TypeConverter
    public static String fromCoordinates(Coordinates coordinates) {
        Gson gson = new Gson();
        String json = gson.toJson(coordinates);
        return json;
    }

    @TypeConverter
    public static Image fromStringtoImage(String value) {
        return new Gson().fromJson(value, Image.class);
    }

    @TypeConverter
    public static String fromCoordinates(Image image) {
        Gson gson = new Gson();
        String json = gson.toJson(image);
        return json;
    }
}


