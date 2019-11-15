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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CitiesSearchResultData that = (CitiesSearchResultData) o;

        return cityDataList != null ? cityDataList.equals(that.cityDataList) : that.cityDataList == null;
    }

    @Override
    public int hashCode() {
        return cityDataList != null ? cityDataList.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "CitiesSearchResultData{" +
                "cityDataList=" + cityDataList +
                '}';
    }
}
