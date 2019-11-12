package com.example.cities.di.app;

import com.example.cities.data.ApplicationProviderImpl;
import com.example.cities.data.CitiesRepositoryImpl;
import com.example.cities.di.PerFeature;
import com.example.cities.domain.ApplicationProvider;
import com.example.cities.domain.CitiesRepository;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class AppModule {

    @Binds
    @Singleton
    abstract ApplicationProvider provideApplicationProvider(ApplicationProviderImpl applicationProvider);

}
