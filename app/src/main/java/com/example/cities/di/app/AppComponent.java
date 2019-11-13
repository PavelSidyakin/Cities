package com.example.cities.di.app;

import com.example.cities.TheApplication;
import com.example.cities.di.screen.about.AboutScreenBuildersModule;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;

@Component(modules = {AndroidSupportInjectionModule.class, AppModule.class, AboutScreenBuildersModule.class})
@Singleton
public interface AppComponent {
    void inject(TheApplication theApplication);



}
