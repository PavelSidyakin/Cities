package com.example.cities.domain.cities_search;

import com.example.cities.model.data.CitiesData;

import io.reactivex.Single;

public interface CitiesRepository {

    /**
     * Returns CitiesData "as is". Not preprocessed at all.
     *
     * subscribe: io
     * error: if loading failed
     *
     * @return Single with {@link CitiesData}
     */
    Single<CitiesData> getCitiesData();

}
