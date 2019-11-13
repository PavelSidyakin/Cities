package com.example.cities.di.screen.cities;

import com.example.cities.di.CitiesSearchScreenScope;
import com.example.cities.di.MainActivityScope;
import com.example.cities.di.cities_search.CitiesSearchModule;
import com.example.cities.di.screen.cities.cities_search.CitiesSearchScreenModule;
import com.example.cities.presentation.cities.cities_search.CitiesSearch;
import com.example.cities.presentation.cities.cities_search.presenter.CitiesSearchPresenterImpl;
import com.example.cities.presentation.cities.cities_search.view.CitiesSearchFragment;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MainActivityScreenModule {

    @CitiesSearchScreenScope
    @ContributesAndroidInjector(modules = { CitiesSearchScreenModule.class, CitiesSearchModule.class})
    abstract CitiesSearchFragment bindCitiesSearchFragment();


}
