package com.example.cities.di.cities_search;

import com.example.cities.data.CitiesRepositoryImpl;
import com.example.cities.di.PerFeature;
import com.example.cities.domain.CitiesRepository;
import com.example.cities.domain.cities_search.CitiesSearchInteractor;
import com.example.cities.domain.cities_search.CitiesSearchInteractorImpl;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class CitiesSearchModule {

    @Binds
    @PerFeature
    abstract CitiesSearchInteractor provideCitiesSearchInteractor(CitiesSearchInteractorImpl citiesSearchInteractor);

    @Binds
    @PerFeature
    abstract CitiesRepository provideCitiesRepository(CitiesRepositoryImpl citiesRepository);
}
