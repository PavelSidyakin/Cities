package com.example.cities.di.cities_search;

import com.example.cities.di.screen.cities_search.CitiesSearchFragmentScope;
import com.example.cities.domain.cities_search.CitiesSearchInteractor;
import com.example.cities.domain.cities_search.CitiesSearchInteractorImpl;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class CitiesSearchModule {

    @Binds
    @CitiesSearchFragmentScope
    abstract CitiesSearchInteractor provideCitiesSearchInteractor(CitiesSearchInteractorImpl citiesSearchInteractor);

}
