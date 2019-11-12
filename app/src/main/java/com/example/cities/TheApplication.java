package com.example.cities;

import android.app.Application;

import com.example.cities.di.app.AppComponent;
import com.example.cities.di.app.DaggerAppComponent;
import com.example.cities.domain.ApplicationProvider;
import com.example.cities.domain.initialization.impl.InitializationHelper;

import javax.inject.Inject;

public class TheApplication extends Application {

    private static AppComponent appComponent;

    @Inject
    ApplicationProvider applicationProvider;

    @Inject
    InitializationHelper initializationHelper;


    @Override
    public void onCreate() {
        super.onCreate();

        appComponent = DaggerAppComponent.builder().build();

        appComponent.inject(this);

        applicationProvider.init(this);

        initializationHelper.launchInitialization();

    }

    public static AppComponent getAppComponent() {
        return appComponent;
    }
}
