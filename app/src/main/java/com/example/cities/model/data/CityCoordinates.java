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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CityCoordinates that = (CityCoordinates) o;

        if (Double.compare(that.lon, lon) != 0) return false;
        return Double.compare(that.lat, lat) == 0;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(lon);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(lat);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "CityCoordinates{" +
                "lon=" + lon +
                ", lat=" + lat +
                '}';
    }
}
