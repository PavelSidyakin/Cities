package com.example.cities.di.screen.cities.cities_search;

import com.example.cities.presentation.MainActivity;
import com.example.cities.presentation.cities.cities_search.CitiesSearchFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class CitiesSearchScreenBindModule {

    @ContributesAndroidInjector(modules = {CitiesSearchScreenModule.class})
    abstract CitiesSearchFragment bindCitiesSearchFragment();


}
