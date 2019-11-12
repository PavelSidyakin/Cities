package com.example.cities.presentation.cities.cities_search.impl;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.example.cities.domain.cities_search.CitiesSearchInteractor;
import com.example.cities.domain.cities_search.CitiesSearchInteractorImpl;
import com.example.cities.model.CitiesSearchResult;
import com.example.cities.presentation.cities.cities_search.CitiesSearch;
import com.example.cities.model.CityData;

public class CitiesSearchDataSource extends PageKeyedDataSource<Integer, CityData> implements RetryCallback {

    private final String searchText;
    private final CitiesSearch.Presenter citiesSearchPresenter;
    private final CitiesSearchInteractor citiesSearchInteractor = new CitiesSearchInteractorImpl();

    public CitiesSearchDataSource(String searchText, CitiesSearch.Presenter citiesSearchPresenter) {
        this.searchText = searchText;
        this.citiesSearchPresenter = citiesSearchPresenter;
    }

    @Override
    public void retry() {

    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, CityData> callback) {



    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, CityData> callback) {

    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, CityData> callback) {
    }


}
