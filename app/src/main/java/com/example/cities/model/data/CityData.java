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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CityData cityData = (CityData) o;

        if (id != cityData.id) return false;
        if (name != null ? !name.equals(cityData.name) : cityData.name != null) return false;
        if (country != null ? !country.equals(cityData.country) : cityData.country != null)
            return false;
        return cityCoordinates != null ? cityCoordinates.equals(cityData.cityCoordinates) : cityData.cityCoordinates == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (country != null ? country.hashCode() : 0);
        result = 31 * result + (cityCoordinates != null ? cityCoordinates.hashCode() : 0);
        result = 31 * result + id;
        return result;
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
