package com.example.cities.di.app;

import com.example.cities.TheApplication;
import com.example.cities.di.screen.about.AboutActivityBindModule;
import com.example.cities.di.screen.cities.MainActivityBindModule;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Component(modules = {
        AndroidSupportInjectionModule.class,
        AppModule.class,
        AboutActivityBindModule.class,
        MainActivityBindModule.class
    })
@Singleton
public interface AppComponent extends AndroidInjector<TheApplication> {
    void inject(TheApplication theApplication);



}
