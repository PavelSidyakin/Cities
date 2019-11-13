package com.example.cities.di.app;

import com.example.cities.TheApplication;
import com.example.cities.di.screen.about.AboutScreenBindModule;
import com.example.cities.di.screen.cities.MainActivityScreenModule;
import com.example.cities.di.screen.cities.cities_search.CitiesSearchScreenBindModule;
import com.example.cities.presentation.MainActivity;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;

@Component(modules = {AndroidSupportInjectionModule.class, AppModule.class,
        AboutScreenBindModule.class,
        CitiesSearchScreenBindModule.class,
        MainActivityScreenModule.class})
@Singleton
public interface AppComponent {
    void inject(TheApplication theApplication);



}
