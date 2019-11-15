package com.example.cities.domain.cities_search;

import com.example.cities.model.CitiesSearchResult;
import com.example.cities.model.CitiesSearchResultCode;
import com.example.cities.model.CitiesSearchResultData;
import com.example.cities.model.data.CityData;
import com.example.cities.utils.XLog;
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

    private static final String TAG = "CitiesSearchInteractor";

    @Inject
    CitiesSearchInteractorImpl(CitiesSearchPreLoadInteractor citiesSearchPreLoadInteractor, SchedulerProvider schedulerProvider) {
        this.citiesSearchPreLoadInteractor = citiesSearchPreLoadInteractor;
        this.schedulerProvider = schedulerProvider;
    }

    public Single<CitiesSearchResult> requestCities(String searchText, int pageIndex, int pageItemCount) {
        return Single.fromCallable(() ->  searchText.equalsIgnoreCase(currentSearchText))
            .flatMap(hasResult -> hasResult ? returnFromCachedResult(pageIndex, pageItemCount) : findAndCacheResult(searchText, pageIndex, pageItemCount))
            .map(cityDataList -> new CitiesSearchResult(CitiesSearchResultCode.OK, new CitiesSearchResultData(cityDataList)))
            .doOnSubscribe(disposable -> XLog.i(TAG, "CitiesSearchInteractorImpl.requestCities(): Subscribe. searchText=" + searchText + " pageIndex=" + pageIndex + " pageItemCount=" + pageItemCount))
            .doOnSuccess(result -> XLog.i(TAG, "CitiesSearchInteractorImpl.requestCities(): Success. result code " + result.getResultCode() + " size " + result.getResultData().getCityDataList().size()))
            .doOnError(throwable -> XLog.w(TAG, "CitiesSearchInteractorImpl.requestCities(): Error", throwable))
            .onErrorResumeNext(throwable -> Single.just(new CitiesSearchResult(CitiesSearchResultCode.ERROR, null)))
            .subscribeOn(schedulerProvider.computation());
    }

    private Single<List<CityData>> returnFromCachedResult(int pageIndex, int pageItemCount) {
        return Single.fromCallable(() -> getSublistFromCachedData(pageIndex, pageItemCount))
            .subscribeOn(schedulerProvider.computation());
    }

    private List<CityData> getSublistFromCachedData(int pageIndex, int pageItemCount) {
        XLog.i(TAG, "getSublistFromCachedData() pageIndex=" + pageIndex + " pageItemCount=" + pageItemCount);
        XLog.i(TAG, "getSublistFromCachedData() currentCityDataList size=" + currentCityDataList.size());

        int startIndex = Math.min(pageIndex * pageItemCount, currentCityDataList.size());
        int endIndex = Math.min(pageIndex * pageItemCount + pageItemCount, currentCityDataList.size());

        XLog.i(TAG, "getSublistFromCachedData() startIndex=" + startIndex + " endIndex=" + endIndex);

        return currentCityDataList.subList(startIndex, endIndex);
    }

    private Single<List<CityData>> findAndCacheResult(String searchText, int pageIndex, int pageItemCount) {
        return Single.fromCallable(() -> findCityData(searchText))
                .doOnSuccess(cityData -> currentCityDataList = cityData)
                .doOnSuccess(cityData -> currentSearchText = searchText)
                .subscribeOn(schedulerProvider.computation());
    }

    private List<CityData> findCityData(String searchText) {
        Comparator<CityData> comparator = (val, key) -> {
            if (startsWithIgnoreCase(val.getName(), key.getName())) {
                return 0;
            }

            return val.getName().compareToIgnoreCase(key.getName());

        };

        List<CityData> sortedListOfCityData = citiesSearchPreLoadInteractor.getSortedListOfCityData();

        XLog.i(TAG, "findCityData() sortedListOfCityData.size() = " + sortedListOfCityData.size());

        int foundIndex = Collections.binarySearch(sortedListOfCityData,
                new CityData(searchText, null, null, 0), // We don't care about other properties in CityData
                comparator);

        XLog.i(TAG, "findCityData() foundIndex=" + foundIndex);

        if (foundIndex < 0) {
            return new ArrayList<>();
        }

        // foundIndex could be in any place of found list. Check values before and after foundIndex

        int startIndexOfList = foundIndex;

        for (int i = startIndexOfList - 1; i >= 0; --i) {
            if (startsWithIgnoreCase(sortedListOfCityData.get(i).getName(), searchText)) {
                startIndexOfList = i;
            } else {
                break;
            }
        }

        XLog.i(TAG, "findCityData() startIndexOfList=" + startIndexOfList);

        int lastIndexOfList = foundIndex;
        for (int i = lastIndexOfList + 1; i < sortedListOfCityData.size(); ++i) {
            if (startsWithIgnoreCase(sortedListOfCityData.get(i).getName(), searchText)) {
                lastIndexOfList = i;
            } else {
                break;
            }
        }

        XLog.i(TAG, "findCityData() lastIndexOfList=" + lastIndexOfList);

        return sortedListOfCityData.subList(startIndexOfList, lastIndexOfList + 1);
    }

    private boolean startsWithIgnoreCase(String str, String startsWith) {
        return str.substring(0, Math.min(startsWith.length(), str.length())).equalsIgnoreCase(startsWith);
    }
}
