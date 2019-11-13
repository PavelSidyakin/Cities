package com.example.cities.di.screen.cities;

import com.example.cities.di.screen.cities.cities_search.CitiesSearchFragmentScope;
import com.example.cities.di.cities_search.CitiesSearchModule;
import com.example.cities.di.screen.cities.cities_search.CitiesSearchFragmentModule;
import com.example.cities.presentation.cities.cities_search.view.CitiesSearchFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MainActivityModule {

    @CitiesSearchFragmentScope
    @ContributesAndroidInjector(modules = { CitiesSearchFragmentModule.class, CitiesSearchModule.class})
    abstract CitiesSearchFragment bindCitiesSearchFragment();


}
