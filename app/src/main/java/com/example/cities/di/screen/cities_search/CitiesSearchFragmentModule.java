package com.example.cities.di.screen.cities_search;

import com.example.cities.presentation.cities_search.CitiesSearch;
import com.example.cities.presentation.cities_search.presenter.CitiesSearchPresenterImpl;
import com.example.cities.presentation.cities_search.view.CitiesSearchFragment;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class CitiesSearchFragmentModule {

    @Binds
    @CitiesSearchFragmentScope
    abstract CitiesSearch.Presenter provideCitiesSearchPresenter(CitiesSearchPresenterImpl presenter);

    @Binds
    @CitiesSearchFragmentScope
    abstract CitiesSearch.View provideCitiesSearchView(CitiesSearchFragment citiesSearchFragment);

}
