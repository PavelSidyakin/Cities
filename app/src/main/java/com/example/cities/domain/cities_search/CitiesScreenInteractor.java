package com.example.cities.domain.cities_search;

import com.example.cities.model.data.CityData;

import io.reactivex.Observable;

public interface CitiesScreenInteractor {

    void setCurrentSearchText(String currentSearchText);
    String getCurrentSearchText();

    void setCurrentSelectedCity(CityData currentSelectedCity);
    CityData getCurrentSelectedCity();

    Observable<CityData> observeCitySelection();
}
