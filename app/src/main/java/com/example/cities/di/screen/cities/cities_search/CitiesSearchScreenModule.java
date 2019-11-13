package com.example.cities.di.screen.cities.cities_search;

import com.example.cities.di.PerFeature;
import com.example.cities.presentation.cities.cities_search.CitiesSearch;
import com.example.cities.presentation.cities.cities_search.CitiesSearchFragment;
import com.example.cities.presentation.cities.cities_search.impl.CitiesSearchPresenterImpl;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class CitiesSearchScreenModule {

    @Binds
    @PerFeature
    abstract CitiesSearch.Presenter provideCitiesSearchPresenter(CitiesSearchPresenterImpl presenter);

    @Binds
    @PerFeature
    abstract CitiesSearch.View provideCitiesSearchView(CitiesSearchFragment citiesSearchFragment);

}
