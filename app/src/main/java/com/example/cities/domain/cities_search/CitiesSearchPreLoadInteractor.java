package com.example.cities.domain.cities_search;

import com.example.cities.model.data.CityData;

import java.util.List;

import io.reactivex.Completable;

public interface CitiesSearchPreLoadInteractor {

    /**
     * Loads and preprocess cities list.
     *
     * subscribe: computation
     * error: if failed to load or preprocess data
     *
     * @return Completable, completes when loading is completed
     */
    Completable preLoad();

    /**
     * Returns list of all cities data.
     * Cities are sorted in alphabetical order (case insensitive).
     *
     * Does not perform any calculations, just returns stored list.
     *
     * Returns empty list if Completable returned by {@link #preLoad()} has not completed.
     *
     * @return List of CityData
     */
    List<CityData> getSortedListOfCityData();

}
