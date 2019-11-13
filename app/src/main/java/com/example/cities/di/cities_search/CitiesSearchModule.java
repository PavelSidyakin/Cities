package com.example.cities.di.cities_search;

import com.example.cities.di.CitiesSearchScreenScope;
import com.example.cities.di.MainActivityScope;
import com.example.cities.domain.cities_search.CitiesSearchInteractor;
import com.example.cities.domain.cities_search.CitiesSearchInteractorImpl;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class CitiesSearchModule {

    @Binds
    @CitiesSearchScreenScope
    abstract CitiesSearchInteractor provideCitiesSearchInteractor(CitiesSearchInteractorImpl citiesSearchInteractor);

}
