package com.example.cities.di.screen.main_activity;

import com.example.cities.presentation.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MainActivityBindModule {

    @MainActivityScope
    @ContributesAndroidInjector(modules = {MainActivityModule.class})
    abstract MainActivity bindMainActivity();

}
