package com.example.cities.di.screen.main_activity.cities_search;

import com.example.cities.presentation.main_activity.cities_search.CitiesSearch;
import com.example.cities.presentation.main_activity.cities_search.presenter.CitiesSearchPresenterImpl;
import com.example.cities.presentation.main_activity.cities_search.view.CitiesSearchFragment;

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
