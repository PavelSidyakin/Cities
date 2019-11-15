package com.example.cities.di.screen.about;

import com.example.cities.data.AboutAppInfoRepositoryImpl;
import com.example.cities.domain.about_app.AboutAppInfoInteractor;
import com.example.cities.domain.about_app.AboutAppInfoInteractorImpl;
import com.example.cities.domain.about_app.AboutAppInfoRepository;
import com.example.cities.presentation.about_app.About;
import com.example.cities.presentation.about_app.view.AboutActivity;
import com.example.cities.presentation.about_app.presenter.AboutPresenterImpl;

import dagger.Binds;
import dagger.Module;

@Module
abstract class AboutActivityModule {

    @Binds
    @AboutActivityScope
    abstract About.Presenter provideAboutPresenter(AboutPresenterImpl aboutPresenter);

    @Binds
    @AboutActivityScope
    abstract About.View provideAboutView(AboutActivity aboutActivity);

    @Binds
    @AboutActivityScope
    abstract AboutAppInfoInteractor provideAboutAppInfoInteractor(AboutAppInfoInteractorImpl aboutAppInfoInteractor);

    @Binds
    @AboutActivityScope
    abstract AboutAppInfoRepository provideAboutAppInfoRepository(AboutAppInfoRepositoryImpl aboutAppInfoRepository);

}
