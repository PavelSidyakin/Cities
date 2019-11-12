package com.example.cities.di.app;

import com.example.cities.TheApplication;
import com.example.cities.di.screen.CitiesSearchScreenComponent;
import com.example.cities.di.screen.CityMapScreenComponent;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {AppModule.class})
@Singleton
public interface AppComponent {

    CitiesSearchScreenComponent getCitiesSearchScreenComponent();
    CityMapScreenComponent getCityMapScreenComponent();

    void inject(TheApplication theApplication);

    interface Builder {
        AppComponent build();
    }

}
