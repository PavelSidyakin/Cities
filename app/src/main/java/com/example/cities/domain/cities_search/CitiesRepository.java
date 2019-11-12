package com.example.cities.domain.cities_search;

import com.example.cities.model.data.CitiesData;

import io.reactivex.Single;

public interface CitiesRepository {

    Single<CitiesData> getCitiesData();

}
