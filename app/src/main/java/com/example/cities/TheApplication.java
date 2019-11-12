package com.example.cities;

import android.app.Application;
import android.util.Log;

import com.example.cities.di.app.AppComponent;
import com.example.cities.di.app.DaggerAppComponent;
import com.example.cities.domain.ApplicationProvider;
import com.example.cities.domain.CitiesRepository;

import javax.inject.Inject;

public class TheApplication extends Application {

    private static AppComponent appComponent;

    @Inject
    ApplicationProvider applicationProvider;

    @Inject
    CitiesRepository citiesRepository;

    @Override
    public void onCreate() {
        super.onCreate();

        appComponent = DaggerAppComponent.builder().build();

        appComponent.inject(this);

        applicationProvider.init(this);

        appComponent.getCitiesSearchScreenComponent().inject(this);

    }

    public static AppComponent getAppComponent() {
        return appComponent;
    }
}
