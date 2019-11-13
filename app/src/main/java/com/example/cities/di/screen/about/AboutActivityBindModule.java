package com.example.cities.di.screen.about;

import com.example.cities.presentation.about_app.AboutActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class AboutActivityBindModule {

    @AboutActivityScope
    @ContributesAndroidInjector(modules = {AboutScreenModule.class})
    abstract AboutActivity bindAboutActivity();
}
