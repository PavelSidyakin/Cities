package com.example.cities.model;

import com.example.cities.model.data.CityData;

import java.util.List;

public class CitiesSearchResultData {
    private final List<CityData> cityDataList;

    public CitiesSearchResultData(List<CityData> cityDataList) {
        this.cityDataList = cityDataList;
    }

    public List<CityData> getCityDataList() {
        return cityDataList;
    }

    @Override
    public String toString() {
        return "CitiesSearchResultData{" +
                "cityDataList=" + cityDataList +
                '}';
    }
}
