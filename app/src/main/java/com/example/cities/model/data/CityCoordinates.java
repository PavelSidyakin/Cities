package com.example.cities.model.data;

import com.google.gson.annotations.SerializedName;

public class CityCoordinates {

    @SerializedName("lon")
    private double lon;

    @SerializedName("lat")
    private double lat;

    @Override
    public String toString() {
        return "CityCoordinates{" +
                "lon=" + lon +
                ", lat=" + lat +
                '}';
    }
}
