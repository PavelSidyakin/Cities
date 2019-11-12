package com.example.cities.domain.cities_search;

import android.util.Log;

import com.example.cities.domain.CitiesRepository;
import com.example.cities.model.CitiesSearchResultData;
import com.example.cities.model.data.CityData;
import com.example.cities.utils.rx.SchedulerProvider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;

public class CitiesSearchInteractorImpl implements CitiesSearchInteractor {

    private final CitiesRepository citiesRepository;
    private final SchedulerProvider schedulerProvider;


    private List<CityData> sortedCityList = new ArrayList<>();

    private static final String TAG = "CitiesSearchInteractor";

    @Inject
    CitiesSearchInteractorImpl(CitiesRepository citiesRepository, SchedulerProvider schedulerProvider) {
        this.citiesRepository = citiesRepository;
        this.schedulerProvider = schedulerProvider;
    }

    @Override
    public Completable init() {
        return citiesRepository.getCitiesData()
            .map(citiesData -> citiesData.getCityDataList())
            .map(cityDataList -> {
                Collections.sort(cityDataList, (o1, o2) -> o1.getName().compareToIgnoreCase(o2.getName()));
                return cityDataList;
             })
            .map(sortedList -> sortedCityList = sortedList)
            .subscribeOn(schedulerProvider.computation())
            .doOnSubscribe(disposable -> Log.i(TAG, "CitiesSearchInteractorImpl.init(): Subscribe. "))
            .doOnSuccess(result -> Log.i(TAG, "CitiesSearchInteractorImpl.init(): Success. result=" + result))
            .doOnError(throwable -> Log.w(TAG, "CitiesSearchInteractorImpl.init(): Error", throwable))
            .ignoreElement();
    }

    public Single<CitiesSearchResultData> requestCities(String searchText, int pageIndex, int pageItemCount) {
        return null;
    }
}
