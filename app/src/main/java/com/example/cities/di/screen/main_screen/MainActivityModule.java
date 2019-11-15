package com.example.cities.di.screen.main_screen;

import com.example.cities.di.cities_search.CitiesSearchModule;
import com.example.cities.di.screen.cities_search.CitiesSearchFragmentModule;
import com.example.cities.di.screen.cities_search.CitiesSearchFragmentScope;
import com.example.cities.di.screen.city_info.CityInfoFragmentModule;
import com.example.cities.di.screen.city_info.CityInfoFragmentScope;
import com.example.cities.di.screen.city_map.CityMapFragmentModule;
import com.example.cities.di.screen.city_map.CityMapFragmentScope;
import com.example.cities.presentation.cities_search.view.CitiesSearchFragment;
import com.example.cities.presentation.city_info.view.CityInfoFragment;
import com.example.cities.presentation.city_map.view.CityMapFragment;
import com.example.cities.presentation.main_screen.MainScreen;
import com.example.cities.presentation.main_screen.presenter.MainScreenPresenterImpl;
import com.example.cities.presentation.main_screen.view.MainActivity;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
abstract class MainActivityModule {

    @CitiesSearchFragmentScope
    @ContributesAndroidInjector(modules = { CitiesSearchFragmentModule.class, CitiesSearchModule.class })
    abstract CitiesSearchFragment bindCitiesSearchFragment();

    @CityMapFragmentScope
    @ContributesAndroidInjector(modules = { CityMapFragmentModule.class })
    abstract CityMapFragment bindCiyMapFragment();

    @CityInfoFragmentScope
    @ContributesAndroidInjector(modules = {CityInfoFragmentModule.class })
    abstract CityInfoFragment bindCiyInfoFragment();

    @Binds
    @MainActivityScope
    abstract MainScreen.Presenter provideMainScreenPresenter(MainScreenPresenterImpl mainScreenPresenter);

    @Binds
    @MainActivityScope
    abstract MainScreen.View provideMainScreenView(MainActivity mainActivity);

}
