package com.example.cities.domain.cities_search;

import com.example.cities.model.data.CityData;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

public class CitiesScreenInteractorImpl implements CitiesScreenInteractor {

    private final CitiesScreenRepository citiesScreenRepository;

    private final BehaviorSubject<CityData> selectCitySubject = BehaviorSubject.create();

    @Inject
    CitiesScreenInteractorImpl(CitiesScreenRepository citiesScreenRepository) {
        this.citiesScreenRepository = citiesScreenRepository;
    }

    @Override
    public void setCurrentSearchText(String currentSearchText) {
        citiesScreenRepository.setCurrentSearchText(currentSearchText);
    }

    @Override
    public String getCurrentSearchText() {
        return citiesScreenRepository.getCurrentSearchText();
    }

    @Override
    public void setCurrentSelectedCity(CityData currentSelectedCity) {
        citiesScreenRepository.setCurrentSelectedCity(currentSelectedCity);
        selectCitySubject.onNext(currentSelectedCity);
    }

    @Override
    public CityData getCurrentSelectedCity() {
        return citiesScreenRepository.getCurrentSelectedCity();
    }

    @Override
    public Observable<CityData> observeCitySelection() {
        return selectCitySubject;
    }

}
