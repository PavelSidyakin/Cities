package com.example.cities.domain.cities_search;

import com.example.cities.model.CitiesSearchResult;

import io.reactivex.Single;

public interface CitiesSearchInteractor {

    Single<CitiesSearchResult> requestCities(String searchText, int pageIndex, int pageItemCount);

}
