package com.example.cities.presentation.city_info.presenter;

import com.example.cities.domain.cities_search.CitiesScreenInteractor;
import com.example.cities.model.data.CityData;
import com.example.cities.presentation.city_info.CityInfo;

import javax.inject.Inject;

public class CityInfoPresenterImpl implements CityInfo.Presenter {

    private final CityInfo.View view;
    private final CitiesScreenInteractor citiesScreenInteractor;

    @Inject
    CityInfoPresenterImpl(CityInfo.View view, CitiesScreenInteractor citiesScreenInteractor) {
        this.view = view;
        this.citiesScreenInteractor = citiesScreenInteractor;
    }

    @Override
    public void onViewReady() {
        CityData cityData = citiesScreenInteractor.getCurrentSelectedCity();

        if (cityData != null) {
            view.setCityName(cityData.getName());
            view.setCountryName(cityData.getCountry());
            view.setCoordinates(cityData.getCityCoordinates());
        }
    }
}
