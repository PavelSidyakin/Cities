package com.example.cities.di.app;

import com.example.cities.TheApplication;
import com.example.cities.di.screen.about.AboutScreenBindModule;
import com.example.cities.di.screen.cities.MainActivityScreenBindModule;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Component(modules = {
        AndroidSupportInjectionModule.class,
        AppModule.class,
        AboutScreenBindModule.class,
        //CitiesSearchScreenBindModule.class,
        MainActivityScreenBindModule.class
    })
@Singleton
public interface AppComponent extends AndroidInjector<TheApplication> {
    void inject(TheApplication theApplication);



}
