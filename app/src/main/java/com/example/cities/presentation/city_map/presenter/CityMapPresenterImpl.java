package com.example.cities.presentation.city_map.presenter;

import com.example.cities.domain.cities_search.CitiesScreenInteractor;
import com.example.cities.model.data.CityData;
import com.example.cities.presentation.city_map.CityMap;

import javax.inject.Inject;

public class CityMapPresenterImpl implements CityMap.Presenter {

    private final CityMap.View view;
    private final CitiesScreenInteractor citiesScreenInteractor;

    @Inject
    CityMapPresenterImpl(CityMap.View view, CitiesScreenInteractor citiesScreenInteractor){
        this.view = view;

        this.citiesScreenInteractor = citiesScreenInteractor;
    }

    @Override
    public void onViewReady() {
        CityData cityData = citiesScreenInteractor.getCurrentSelectedCity();

        view.navigateToCity(cityData.getCityCoordinates());

        view.addCityMarker(cityData.getCityCoordinates(), cityData.getName());
    }

    @Override
    public void onDestroyView() {

    }
}
