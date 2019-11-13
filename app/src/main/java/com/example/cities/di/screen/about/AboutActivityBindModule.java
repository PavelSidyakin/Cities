package com.example.cities.di.screen.about;

import com.example.cities.presentation.about_app.view.AboutActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class AboutActivityBindModule {

    @AboutActivityScope
    @ContributesAndroidInjector(modules = {AboutActivityModule.class})
    abstract AboutActivity bindAboutActivity();
}
