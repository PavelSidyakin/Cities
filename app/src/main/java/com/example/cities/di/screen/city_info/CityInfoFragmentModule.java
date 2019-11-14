package com.example.cities.di.screen.city_info;

import com.example.cities.presentation.city_info.CityInfo;
import com.example.cities.presentation.city_info.presenter.CityInfoPresenterImpl;
import com.example.cities.presentation.city_info.view.CityInfoFragment;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class CityInfoFragmentModule {

    @Binds
    @CityInfoFragmentScope
    abstract CityInfo.Presenter provideCityInfoPresenter(CityInfoPresenterImpl presenter);

    @Binds
    @CityInfoFragmentScope
    abstract CityInfo.View provideCityInfoView(CityInfoFragment cityMapFragment);

}
