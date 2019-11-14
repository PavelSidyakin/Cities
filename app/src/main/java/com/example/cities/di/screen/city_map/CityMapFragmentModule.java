package com.example.cities.di.screen.city_map;

import com.example.cities.presentation.city_map.CityMap;
import com.example.cities.presentation.city_map.presenter.CityMapPresenterImpl;
import com.example.cities.presentation.city_map.view.CityMapFragment;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class CityMapFragmentModule {

    @Binds
    @CityMapFragmentScope
    abstract CityMap.Presenter provideCityMapPresenter(CityMapPresenterImpl presenter);

    @Binds
    @CityMapFragmentScope
    abstract CityMap.View provideCityMapView(CityMapFragment cityMapFragment);

}
