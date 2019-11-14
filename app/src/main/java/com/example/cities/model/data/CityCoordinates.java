package com.example.cities.model.data;

import com.google.gson.annotations.SerializedName;

public class CityCoordinates {

    @SerializedName("lon")
    private double lon;

    @SerializedName("lat")
    private double lat;

    public double getLng() {
        return lon;
    }

    public double getLat() {
        return lat;
    }

    @Override
    public String toString() {
        return "CityCoordinates{" +
                "lon=" + lon +
                ", lat=" + lat +
                '}';
    }
}
