package com.example.cities.di.screen.cities;

import com.example.cities.di.screen.cities.cities_search.CitiesSearchScreenModule;
import com.example.cities.presentation.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MainActivityScreenModule {

    @ContributesAndroidInjector(modules = {CitiesSearchScreenModule.class})
    abstract MainActivity bindMainActivity();


}
