package com.example.cities.di.screen;

import com.example.cities.TheApplication;
import com.example.cities.di.PerFeature;
import com.example.cities.di.cities_search.CitiesSearchModule;

import dagger.Subcomponent;

@Subcomponent(modules = {CitiesSearchModule.class})
@PerFeature
public interface CitiesSearchScreenComponent {
    void inject(TheApplication theApplication);
}
