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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CitiesData that = (CitiesData) o;

        return cityDataList != null ? cityDataList.equals(that.cityDataList) : that.cityDataList == null;
    }

    @Override
    public int hashCode() {
        return cityDataList != null ? cityDataList.hashCode() : 0;
    }

    @NotNull
    @Override
    public String toString() {
        return "CitiesData{" +
                "cityDataList=" + cityDataList +
                '}';
    }
}
