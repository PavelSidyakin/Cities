package com.example.cities.domain.cities_search;

import com.example.cities.model.data.CityData;

import io.reactivex.Observable;

/**
 * Used to share information between cities-related screens
 */
public interface CitiesScreenInteractor {

    /**
     * Sets current search text
     *
     * @param currentSearchText Current search text
     */
    void setCurrentSearchText(String currentSearchText);

    /**
     * Returns current search text
     *
     */
    String getCurrentSearchText();

    void setCurrentSelectedCity(CityData currentSelectedCity);

    /**
     * Returns current selected city
     *
     */
    CityData getCurrentSelectedCity();

    /**
     * Returns Observable, which emits an item every time on {@link #setCurrentSelectedCity(CityData)} is called.
     *
     * @return Observable with CityData corresponds to BehaviorSubject contract
     */
    Observable<CityData> observeCitySelection();
}
