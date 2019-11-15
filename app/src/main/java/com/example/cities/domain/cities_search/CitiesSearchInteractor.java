package com.example.cities.domain.cities_search;

import com.example.cities.model.CitiesSearchResult;

import io.reactivex.Single;

public interface CitiesSearchInteractor {

    /**
     * Returns cities, corresponding to provided searchText and page parameters.
     * Cities are sorted in alphabetical order (case insensitive).
     *
     * On error result does not equal to {@link com.example.cities.model.CitiesSearchResultCode#OK}
     *
     * subscribe: computation
     * error: no
     *
     * @param searchText Start letters in a city name.
     * @param pageIndex 0-based requested page index
     * @param pageItemCount Requested page item count
     *
     * @return Single with {@link CitiesSearchResult}
     */
    Single<CitiesSearchResult> requestCities(String searchText, int pageIndex, int pageItemCount);

}
