package com.example.cities.di.app;

import com.example.cities.data.ApplicationProviderImpl;
import com.example.cities.data.CitiesRepositoryImpl;
import com.example.cities.data.CitiesScreenRepositoryImpl;
import com.example.cities.domain.ApplicationProvider;
import com.example.cities.domain.cities_search.CitiesRepository;
import com.example.cities.domain.cities_search.CitiesScreenInteractor;
import com.example.cities.domain.cities_search.CitiesScreenInteractorImpl;
import com.example.cities.domain.cities_search.CitiesScreenRepository;
import com.example.cities.domain.cities_search.CitiesSearchPreLoadInteractor;
import com.example.cities.domain.cities_search.CitiesSearchPreLoadInteractorImpl;
import com.example.cities.domain.initialization.InitializationInteractor;
import com.example.cities.domain.initialization.InitializationInteractorImpl;
import com.example.cities.domain.initialization.impl.InitializationHelper;
import com.example.cities.domain.initialization.impl.InitializationHelperImpl;
import com.example.cities.domain.initialization.impl.InitializationProgressHolder;
import com.example.cities.domain.initialization.impl.InitializationProgressHolderImpl;
import com.example.cities.utils.rx.SchedulerProvider;
import com.example.cities.utils.rx.SchedulerProviderImpl;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;

@Module
abstract class AppModule {

    @Binds
    @Singleton
    abstract ApplicationProvider provideApplicationProvider(ApplicationProviderImpl applicationProvider);

    @Binds
    @Singleton
    abstract SchedulerProvider provideSchedulerProvider(SchedulerProviderImpl schedulerProvider);

    @Binds
    @Singleton
    abstract InitializationInteractor provideInitializationInteractor(InitializationInteractorImpl initializationInteractor);

    @Binds
    @Singleton
    abstract InitializationProgressHolder provideInitializationProgressHolder(InitializationProgressHolderImpl initializationProgressHolder);

    @Binds
    @Singleton
    abstract InitializationHelper provideInitializationHelper(InitializationHelperImpl initializationHelper);

    @Binds
    @Singleton
    abstract CitiesSearchPreLoadInteractor provideCitiesSearchPreLoadInteractor(CitiesSearchPreLoadInteractorImpl citiesSearchPreLoadInteractor);

    @Binds
    @Singleton
    abstract CitiesRepository provideCitiesRepository(CitiesRepositoryImpl citiesRepository);

    @Binds
    @Singleton
    abstract CitiesScreenInteractor provideCitiesScreenInteractor(CitiesScreenInteractorImpl citiesScreenInteractor);

    @Binds
    @Singleton
    abstract CitiesScreenRepository provideCitiesScreenRepository(CitiesScreenRepositoryImpl citiesScreenRepository);

}
