package com.example.cities.di.screen.main_activity;

import com.example.cities.di.screen.main_activity.cities_search.CitiesSearchFragmentScope;
import com.example.cities.di.cities_search.CitiesSearchModule;
import com.example.cities.di.screen.main_activity.cities_search.CitiesSearchFragmentModule;
import com.example.cities.presentation.main_activity.cities_search.view.CitiesSearchFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MainActivityModule {

    @CitiesSearchFragmentScope
    @ContributesAndroidInjector(modules = { CitiesSearchFragmentModule.class, CitiesSearchModule.class})
    abstract CitiesSearchFragment bindCitiesSearchFragment();


}
