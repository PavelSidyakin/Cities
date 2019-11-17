package com.example.cities.di.app;

import com.example.cities.data.CitiesRepositoryImpl;
import com.example.cities.domain.cities_search.CitiesRepository;

import javax.inject.Provider;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppStubModule {

    @Provides
    @Singleton
    public CitiesRepository provideCitiesRepository(Provider<CitiesRepositoryImpl> citiesRepositoryProvider) {
        return citiesRepositoryProvider.get();
    }

}
