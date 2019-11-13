package com.example.cities.domain.cities_search;

import com.example.cities.model.CitiesSearchResult;
import com.example.cities.model.CitiesSearchResultCode;
import com.example.cities.model.CitiesSearchResultData;
import com.example.cities.model.data.CityData;
import com.example.cities.utils.rx.SchedulerProvider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class CitiesSearchInteractorImpl implements CitiesSearchInteractor {

    private final CitiesSearchPreLoadInteractor citiesSearchPreLoadInteractor;
    private final SchedulerProvider schedulerProvider;

    private String currentSearchText = "";
    private List<CityData> currentCityDataList = new ArrayList<>();

    @Inject
    CitiesSearchInteractorImpl(CitiesSearchPreLoadInteractor citiesSearchPreLoadInteractor, SchedulerProvider schedulerProvider) {
        this.citiesSearchPreLoadInteractor = citiesSearchPreLoadInteractor;
        this.schedulerProvider = schedulerProvider;
    }

    public Single<CitiesSearchResult> requestCities(String searchText, int pageIndex, int pageItemCount) {
        return Single.fromCallable(() ->  searchText.equalsIgnoreCase(currentSearchText))
                .flatMap(hasResult -> hasResult ? returnFromCachedResult(pageIndex, pageItemCount) : findAndCacheResult(searchText, pageIndex, pageItemCount))
                .map(cityDataList -> new CitiesSearchResult(CitiesSearchResultCode.OK, new CitiesSearchResultData(cityDataList)))
                .subscribeOn(schedulerProvider.computation());
    }

    private Single<List<CityData>> returnFromCachedResult(int pageIndex, int pageItemCount) {
        return Single.fromCallable(() -> currentCityDataList.subList(
                    Math.min(pageIndex * pageItemCount, currentCityDataList.size()),
                    Math.min(pageIndex * pageItemCount + pageItemCount, currentCityDataList.size()))
            )
            .subscribeOn(schedulerProvider.computation());
    }

    private Single<List<CityData>> findAndCacheResult(String searchText, int pageIndex, int pageItemCount) {
        return Single.fromCallable(() -> findCityData(searchText))
                .doOnSuccess(cityData -> currentCityDataList = cityData)
                .doOnSuccess(cityData -> currentSearchText = searchText)
                .subscribeOn(schedulerProvider.computation());
    }

    private List<CityData> findCityData(String searchText) {
        Comparator<CityData> comparator = (val, key) -> {
            if (val.getName().startsWith(key.getName())) {
                return 0;
            }

            return val.getName().compareToIgnoreCase(key.getName());

        };

        List<CityData> sortedListOfCityData = citiesSearchPreLoadInteractor.getSortedListOfCityData();

        int foundIndex = Collections.binarySearch(sortedListOfCityData,
                new CityData(searchText, null, null, 0), // We don't care about other properties
                comparator);

        // foundIndex could be in any place of found list. Check values before and after foundIndex

        int startIndexOfList = foundIndex;

        for (int i = startIndexOfList - 1; i >= 0; --i) {
            if (sortedListOfCityData.get(i).getName().startsWith(searchText)) {
                startIndexOfList = i;
            } else {
                break;
            }
        }

        int lastIndexOfList = foundIndex;
        for (int i = lastIndexOfList + 1; i < sortedListOfCityData.size(); ++i) {
            if (sortedListOfCityData.get(i).getName().startsWith(searchText)) {
                lastIndexOfList = i;
            } else {
                break;
            }
        }

        return sortedListOfCityData.subList(startIndexOfList, lastIndexOfList + 1);


    }
}
