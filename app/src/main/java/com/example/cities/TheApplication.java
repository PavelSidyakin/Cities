package com.example.cities;

import android.app.Application;

import com.example.cities.di.app.AppComponent;
import com.example.cities.di.app.DaggerAppComponent;
import com.example.cities.domain.ApplicationProvider;
import com.example.cities.domain.initialization.impl.InitializationHelper;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasAndroidInjector;

public class TheApplication extends Application implements HasAndroidInjector {

    @Inject
    DispatchingAndroidInjector<Object> dispatchingAndroidInjector;

    private static AppComponent appComponent;

    @Inject
    ApplicationProvider applicationProvider;

    @Inject
    InitializationHelper initializationHelper;

    @Override
    public void onCreate() {
        super.onCreate();

        appComponent = DaggerAppComponent.create();

        appComponent.inject(this);

        applicationProvider.init(this);

        initializationHelper.launchInitialization();
    }

    public static AppComponent getAppComponent() {
        return appComponent;
    }

    @Override
    public AndroidInjector<Object> androidInjector() {
        return dispatchingAndroidInjector;
    }
}
