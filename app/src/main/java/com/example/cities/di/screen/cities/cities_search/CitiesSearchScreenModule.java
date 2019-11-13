package com.example.cities.di.screen.cities.cities_search;

import com.example.cities.di.CitiesSearchScreenScope;
import com.example.cities.presentation.cities.cities_search.CitiesSearch;
import com.example.cities.presentation.cities.cities_search.presenter.CitiesSearchPresenterImpl;
import com.example.cities.presentation.cities.cities_search.view.CitiesSearchFragment;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class CitiesSearchScreenModule {

    @Binds
    @CitiesSearchScreenScope
    abstract CitiesSearch.Presenter provideCitiesSearchPresenter(CitiesSearchPresenterImpl presenter);

    @Binds
    @CitiesSearchScreenScope
    abstract CitiesSearch.View provideCitiesSearchView(CitiesSearchFragment citiesSearchFragment);

}
