package com.example.cities.domain.cities_search;

import com.example.cities.model.CitiesSearchResultData;
import com.example.cities.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.Single;

public class CitiesSearchInteractorImpl implements CitiesSearchInteractor {

    private final CitiesSearchPreLoadInteractor citiesSearchPreLoadInteractor;
    private final SchedulerProvider schedulerProvider;

    @Inject
    CitiesSearchInteractorImpl(CitiesSearchPreLoadInteractor citiesSearchPreLoadInteractor, SchedulerProvider schedulerProvider) {
        this.citiesSearchPreLoadInteractor = citiesSearchPreLoadInteractor;
        this.schedulerProvider = schedulerProvider;
    }

    public Single<CitiesSearchResultData> requestCities(String searchText, int pageIndex, int pageItemCount) {
        return null;
    }
}
