package com.example.cities.presentation.cities.cities_search.presenter.recycler;

import androidx.annotation.NonNull;
import androidx.paging.DataSource;

import com.example.cities.domain.cities_search.CitiesSearchInteractor;
import com.example.cities.model.data.CityData;
import com.example.cities.presentation.cities.cities_search.CitiesSearch;
import com.example.cities.utils.rx.SchedulerProvider;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;

public class CitiesSearchDataSourceFactory extends DataSource.Factory<Integer, CityData> {

    private final String searchText;
    private final Observable<Object> retryObservable;
    private final CitiesSearch.Presenter citiesSearchPresenter;
    private final SchedulerProvider schedulerProvider;
    private final CitiesSearchInteractor citiesSearchInteractor;
    private final CompositeDisposable compositeDisposable;
    private final int initialPageSizeFactor;


    public CitiesSearchDataSourceFactory(String searchText, Observable<Object> retryObservable, CitiesSearch.Presenter citiesSearchPresenter, SchedulerProvider schedulerProvider, CitiesSearchInteractor citiesSearchInteractor, CompositeDisposable compositeDisposable, int initialPageSizeFactor) {
        this.searchText = searchText;
        this.retryObservable = retryObservable;
        this.citiesSearchPresenter = citiesSearchPresenter;
        this.schedulerProvider = schedulerProvider;
        this.citiesSearchInteractor = citiesSearchInteractor;
        this.compositeDisposable = compositeDisposable;
        this.initialPageSizeFactor = initialPageSizeFactor;
    }

    @NonNull
    @Override
    public DataSource<Integer, CityData> create() {
        return new CitiesSearchDataSource(searchText,
                retryObservable, citiesSearchPresenter,
                schedulerProvider,
                citiesSearchInteractor,
                compositeDisposable, initialPageSizeFactor);
    }

}
