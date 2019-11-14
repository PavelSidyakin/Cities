package com.example.cities.domain.cities_search;

import com.example.cities.model.data.CityData;

import javax.inject.Inject;

public class CitiesScreenInteractorImpl implements CitiesScreenInteractor {

    private final CitiesScreenRepository citiesScreenRepository;

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
    }

    @Override
    public CityData getCurrentSelectedCity() {
        return citiesScreenRepository.getCurrentSelectedCity();
    }

}
