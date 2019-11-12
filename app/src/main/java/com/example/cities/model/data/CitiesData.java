package com.example.cities.model.data;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CitiesData {
    private final List<CityData> cityDataList;

    public CitiesData(List<CityData> cityDataList) {
        this.cityDataList = cityDataList;
    }

    public List<CityData> getCityDataList() {
        return cityDataList;
    }

    @NotNull
    @Override
    public String toString() {
        return "CitiesData{" +
                "cityDataList=" + cityDataList +
                '}';
    }
}
