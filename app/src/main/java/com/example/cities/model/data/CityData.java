package com.example.cities.model.data;

import com.google.gson.annotations.SerializedName;

public class CityData {

    @SerializedName("name")
    private String name;

    @SerializedName("country")
    private String country;

    @SerializedName("coord")
    private CityCoordinates cityCoordinates;

    @SerializedName("_id")
    private int id;

    public CityData(String name, String country, CityCoordinates cityCoordinates, int id) {
        this.name = name;
        this.country = country;
        this.cityCoordinates = cityCoordinates;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public CityCoordinates getCityCoordinates() {
        return cityCoordinates;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "CityData{" +
                "name='" + name + '\'' +
                ", country='" + country + '\'' +
                ", cityCoordinates=" + cityCoordinates +
                ", id=" + id +
                '}';
    }
}
