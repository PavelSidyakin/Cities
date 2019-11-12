package com.example.cities.model;

public class CityData {

    private final String name;
    private final String country;
    private final double lat;
    private final double lng;
    private final int id;

    public CityData(String name, String country, double lat, double lng, int id) {
        this.name = name;
        this.country = country;
        this.lat = lat;
        this.lng = lng;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "CityData{" +
                "name='" + name + '\'' +
                ", country='" + country + '\'' +
                ", lat=" + lat +
                ", lng=" + lng +
                ", id=" + id +
                '}';
    }
}
