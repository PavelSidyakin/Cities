package com.example.cities.domain.cities_search;

import com.example.cities.model.data.CityData;

import java.util.List;

import io.reactivex.Completable;

public interface CitiesSearchPreLoadInteractor {

    Completable preLoad();

    List<CityData> getSortedListOfCityData();


}
