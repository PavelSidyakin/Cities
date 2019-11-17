package com.example.cities.di.app;

import com.example.cities.data.CitiesRepositoryImpl;
import com.example.cities.di.DiMocks;
import com.example.cities.domain.cities_search.CitiesRepository;

import javax.inject.Provider;

import dagger.Module;

@Module
public class AppStubModuleForTests extends AppStubModule {

    @Override
    public CitiesRepository provideCitiesRepository(Provider<CitiesRepositoryImpl> citiesRepositoryProvider) {
        if (DiMocks.INSTANCE.citiesRepository != null) {
            return DiMocks.INSTANCE.citiesRepository;
        }

        return super.provideCitiesRepository(citiesRepositoryProvider);
    }
}