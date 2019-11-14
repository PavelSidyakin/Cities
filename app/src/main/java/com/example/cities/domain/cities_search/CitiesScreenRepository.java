package com.example.cities.domain.cities_search;

import com.example.cities.model.data.CityData;

public interface CitiesScreenRepository {

    void setCurrentSearchText(String currentSearchText);
    String getCurrentSearchText();

    void setCurrentSelectedCity(CityData currentSelectedCity);
    CityData getCurrentSelectedCity();

}
