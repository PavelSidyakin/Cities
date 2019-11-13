package com.example.cities.di.screen.about;

import com.example.cities.presentation.about_app.About;
import com.example.cities.presentation.about_app.view.AboutActivity;
import com.example.cities.presentation.about_app.presenter.AboutPresenterImpl;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class AboutActivityModule {

    @Binds
    @AboutActivityScope
    abstract About.Presenter provideAboutPresenter(AboutPresenterImpl aboutPresenter);

    @Binds
    @AboutActivityScope
    abstract About.View provideAboutView(AboutActivity aboutActivity);

}
