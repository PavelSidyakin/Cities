package com.example.cities.domain;

import com.example.cities.model.data.CitiesData;

import io.reactivex.Single;

public interface CitiesRepository {

    Single<CitiesData> getCitiesData();

}
