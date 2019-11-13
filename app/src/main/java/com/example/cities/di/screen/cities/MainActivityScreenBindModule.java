package com.example.cities.di.screen.cities;

import com.example.cities.di.MainActivityScope;
import com.example.cities.presentation.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MainActivityScreenBindModule {

    @MainActivityScope
    @ContributesAndroidInjector(modules = {MainActivityScreenModule.class})
    abstract MainActivity bindMainActivity();

}
