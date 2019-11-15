package com.example.cities.presentation.city_map.presenter;

import com.example.cities.domain.cities_search.CitiesScreenInteractor;
import com.example.cities.model.data.CityData;
import com.example.cities.presentation.city_map.CityMap;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.CompletableSubject;

public class CityMapPresenterImpl implements CityMap.Presenter {

    private final CityMap.View view;
    private final CitiesScreenInteractor citiesScreenInteractor;

    private CompletableSubject viewReadySubject = CompletableSubject.create();

    private Disposable observeCityDisposable;

    @Inject
    CityMapPresenterImpl(CityMap.View view, CitiesScreenInteractor citiesScreenInteractor){
        this.view = view;
        this.citiesScreenInteractor = citiesScreenInteractor;

        observeCityDisposable = citiesScreenInteractor.observeCitySelection()
                .flatMap(cityData -> viewReadySubject.toSingleDefault(cityData).toObservable())
                .subscribe(cityData -> navigateAndAddCityMarker(cityData));
    }

    @Override
    public void onViewReady() {
        viewReadySubject.onComplete();
    }

    @Override
    public void onBackClicked() {
        view.showCitiesSearchScreen();
    }

    private void navigateAndAddCityMarker(CityData cityData) {
        view.navigateToCity(cityData.getCityCoordinates());
        view.addCityMarker(cityData.getCityCoordinates(), cityData.getName());
    }

    @Override
    public void onDestroyView() {

        if (observeCityDisposable != null && !observeCityDisposable.isDisposed()) {
            observeCityDisposable.dispose();
            observeCityDisposable = null;
        }

    }
}
