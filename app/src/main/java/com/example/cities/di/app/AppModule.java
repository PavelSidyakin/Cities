package com.example.cities.di.app;

import com.example.cities.data.ApplicationProviderImpl;
import com.example.cities.domain.ApplicationProvider;
import com.example.cities.utils.rx.SchedulerProvider;
import com.example.cities.utils.rx.SchedulerProviderImpl;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class AppModule {

    @Binds
    @Singleton
    abstract ApplicationProvider provideApplicationProvider(ApplicationProviderImpl applicationProvider);

    @Binds
    @Singleton
    abstract SchedulerProvider provideSchedulerProvider(SchedulerProviderImpl schedulerProvider);

}
