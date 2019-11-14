package com.example.cities.data;

import com.example.cities.domain.cities_search.CitiesScreenRepository;
import com.example.cities.model.data.CityData;

import javax.inject.Inject;

public class CitiesScreenRepositoryImpl implements CitiesScreenRepository {

    private String currentSearchText = "";
    private CityData currentSelectedCity;

    @Inject
    CitiesScreenRepositoryImpl() {
    }

    @Override
    public void setCurrentSearchText(String currentSearchText) {
        this.currentSearchText = currentSearchText;
    }

    @Override
    public String getCurrentSearchText() {
        return currentSearchText;
    }

    @Override
    public void setCurrentSelectedCity(CityData currentSelectedCity) {
        this.currentSelectedCity = currentSelectedCity;
    }

    @Override
    public CityData getCurrentSelectedCity() {
        return currentSelectedCity;
    }
}
