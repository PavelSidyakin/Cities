package com.example.cities.di.screen.about;

import com.example.cities.di.AboutScreenScope;
import com.example.cities.presentation.about_app.AboutActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class AboutScreenBindModule {

    @AboutScreenScope
    @ContributesAndroidInjector(modules = {AboutScreenModule.class})
    abstract AboutActivity bindAboutActivity();
}
