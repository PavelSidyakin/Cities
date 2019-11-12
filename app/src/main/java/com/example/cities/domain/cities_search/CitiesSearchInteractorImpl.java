package com.example.cities.domain.cities_search;

import com.example.cities.model.CitiesSearchResultData;

import io.reactivex.Single;

public class CitiesSearchInteractorImpl implements CitiesSearchInteractor {

    @Override
    public Single<CitiesSearchResultData> requestCities(String searchText, int pageIndex, int pageItemCount) {
        return null;
    }
}
