package com.example.cities.domain.cities_search;

import com.example.cities.model.CitiesSearchResultData;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface CitiesSearchInteractor {

    Completable init();

    Single<CitiesSearchResultData> requestCities(String searchText, int pageIndex, int pageItemCount);

}
