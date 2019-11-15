package com.example.cities.domain.cities_search;

import com.example.cities.model.data.CityData;
import com.example.cities.utils.XLog;
import com.example.cities.utils.rx.SchedulerProvider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;

public class CitiesSearchPreLoadInteractorImpl implements CitiesSearchPreLoadInteractor {

    private final CitiesRepository citiesRepository;
    private final SchedulerProvider schedulerProvider;

    @Inject
    CitiesSearchPreLoadInteractorImpl(CitiesRepository citiesRepository, SchedulerProvider schedulerProvider) {
        this.citiesRepository = citiesRepository;
        this.schedulerProvider = schedulerProvider;
    }

    private List<CityData> sortedCityList = new ArrayList<>();

    private static final String TAG = "CitiesSearchIPreLoad";

    @Override
    public Completable preLoad() {
        return citiesRepository.getCitiesData()
            .map(citiesData -> citiesData.getCityDataList())
            .map(cityDataList -> {
                Collections.sort(cityDataList, (o1, o2) -> o1.getName().compareToIgnoreCase(o2.getName()));
                return cityDataList;
            })
            .map(sortedList -> sortedCityList = sortedList)
            .subscribeOn(schedulerProvider.computation())
            .doOnSubscribe(disposable -> XLog.i(TAG, "CitiesSearchInteractorImpl.init(): Subscribe. "))
            .doOnSuccess(result -> XLog.i(TAG, "CitiesSearchInteractorImpl.init(): Success. result=" + result))
            .doOnError(throwable -> XLog.w(TAG, "CitiesSearchInteractorImpl.init(): Error", throwable))
            .ignoreElement();
    }

    @Override
    public List<CityData> getSortedListOfCityData() {
        return sortedCityList;
    }
}
