package com.example.cities.di.app;

import com.example.cities.TheApplication;
import com.example.cities.di.screen.about.AboutActivityBindModule;
import com.example.cities.di.screen.main_screen.MainActivityBindModule;

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

    @Component.Builder
    interface Builder {
        AppComponent.Builder appStubModule(AppStubModule appStubModule);

        AppComponent build();
    }
}
