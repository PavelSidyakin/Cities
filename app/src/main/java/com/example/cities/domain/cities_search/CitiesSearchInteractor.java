package com.example.cities.domain.cities_search;

import com.example.cities.model.CitiesSearchResultData;

public interface CitiesSearchInteractor {

    CitiesSearchResultData requestCities(String searchText, int pageIndex, int pageItemCount);

}
