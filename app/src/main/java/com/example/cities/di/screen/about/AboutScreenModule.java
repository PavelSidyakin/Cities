package com.example.cities.di.screen.about;

import com.example.cities.di.AboutScreenScope;
import com.example.cities.presentation.about_app.About;
import com.example.cities.presentation.about_app.AboutActivity;
import com.example.cities.presentation.about_app.impl.AboutPresenterImpl;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class AboutScreenModule {

    @Binds
    @AboutScreenScope
    abstract About.Presenter provideAboutPresenter(AboutPresenterImpl aboutPresenter);

    @Binds
    @AboutScreenScope
    abstract About.View provideAboutView(AboutActivity aboutActivity);

}
